package com.industrium.core.common.machine.block;

import com.industrium.core.api.power.IEnergyConsumer;
import com.industrium.core.api.power.VoltageTier;
import com.industrium.core.common.machine.AbstractMachineBlockEntity;
import com.industrium.core.common.machine.module.EnergyModule;
import com.industrium.core.common.machine.module.InventoryModule;
import com.industrium.core.common.machine.module.ProgressModule;
import com.industrium.core.common.registry.MachineModule;
import com.industrium.core.common.system.MachineStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Electric Furnace - powered smelting machine.
 * Consumes LV power to smelt items.
 */
public class ElectricFurnaceBlockEntity extends AbstractMachineBlockEntity implements IEnergyConsumer {
    
    private final EnergyModule energyModule;
    private final InventoryModule inventoryModule;
    private final ProgressModule progressModule;
    
    public ElectricFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(MachineModule.ELECTRIC_FURNACE_BE.get(), pos, state);
        this.energyModule = addModule(new EnergyModule(VoltageTier.LV.getTransferRate() * 10, VoltageTier.LV));
        this.inventoryModule = addModule(new InventoryModule(2));
        this.progressModule = addModule(new ProgressModule());
        this.status = MachineStatus.IDLE;
    }
    
    @Override
    public void tickServer() {
        super.tickServer();
        
        ItemStack input = inventoryModule.getItem(0);
        ItemStack output = inventoryModule.getItem(1);
        
        if (progressModule.getMaxProgress() > 0) {
            // Already smelting
            ItemStack recipeOutput = getOutputFor(input);
            if (recipeOutput.isEmpty() || (!output.isEmpty() && (!ItemStack.isSameItem(output, recipeOutput) || output.getCount() + recipeOutput.getCount() > output.getMaxStackSize()))) {
                // Recipe changed or output full, reset progress
                progressModule.reset();
                setStatus(MachineStatus.IDLE);
            } else if (energyModule.getEnergy() >= 1) {
                // Have energy, progress smelting
                energyModule.extractEnergy(1, false);
                progressModule.increment();
                setStatus(MachineStatus.RUNNING);
                
                if (progressModule.isFinished()) {
                    input.shrink(1);
                    if (output.isEmpty()) {
                        inventoryModule.setItem(1, recipeOutput.copy());
                    } else {
                        output.grow(recipeOutput.getCount());
                    }
                    progressModule.reset();
                    if (inventoryModule.getItem(0).isEmpty()) {
                        setStatus(MachineStatus.IDLE);
                    }
                }
            } else {
                // No energy
                setStatus(MachineStatus.STARVED);
            }
        } else {
            // Not smelting, try to start
            if (!input.isEmpty()) {
                ItemStack recipeOutput = getOutputFor(input);
                if (!recipeOutput.isEmpty()) {
                    if (output.isEmpty() || (ItemStack.isSameItem(output, recipeOutput) && output.getCount() + recipeOutput.getCount() <= output.getMaxStackSize())) {
                        if (energyModule.getEnergy() >= 1) {
                            progressModule.setMaxProgress(200);
                            setStatus(MachineStatus.RUNNING);
                        } else {
                            setStatus(MachineStatus.STARVED);
                        }
                    }
                }
            } else {
                setStatus(energyModule.getEnergy() > 0 ? MachineStatus.IDLE : MachineStatus.OFFLINE);
            }
        }
        
        markClientSync();
    }
    
    /**
     * Gets output for input.
     */
    private ItemStack getOutputFor(ItemStack input) {
        if (input.isEmpty()) return ItemStack.EMPTY;
        
        // Simplified for this refactor, ideally use RecipeManager
        if (input.getItem() == Items.IRON_INGOT || input.getItem() == Items.RAW_IRON) {
            return new ItemStack(Items.IRON_INGOT);
        }
        if (input.getItem() == Items.RAW_COPPER || input.getItem() == Items.COPPER_INGOT) {
            return new ItemStack(Items.COPPER_INGOT);
        }
        if (input.getItem() == Items.SAND) {
            return new ItemStack(Items.GLASS);
        }
        if (input.getItem() == Items.CLAY_BALL) {
            return new ItemStack(Items.BRICK);
        }
        return ItemStack.EMPTY;
    }
    
    // IEnergyConsumer implementation
    
    @Override
    public VoltageTier getRequiredTier() {
        return energyModule.getVoltageTier();
    }
    
    @Override
    public long getConsumptionRate() {
        return progressModule.getMaxProgress() > 0 ? 1 : 0;
    }
    
    @Override
    public boolean canOperate(VoltageTier tier) {
        return energyModule.canAccept(tier);
    }
    
    @Override
    public boolean isOperating() {
        return progressModule.getMaxProgress() > 0;
    }
    
    @Override
    public long getPowerDemand() {
        return isOperating() ? 1 : 0;
    }
    
    @Override
    public long getMaxPowerReceive() {
        return energyModule.getTransferRate();
    }
    
    @Override
    public void onPowerReceived(long amount) {
        energyModule.receiveEnergy(amount, false);
    }
    
    /**
     * Gets status text for interaction.
     */
    public String getStatusText() {
        return energyModule.getEnergy() + " / " + energyModule.getMaxEnergy() + " FE";
    }
}
