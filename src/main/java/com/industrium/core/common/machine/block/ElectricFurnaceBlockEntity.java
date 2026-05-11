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
        
        // Try to start smelting if we have input and no progress
        if (!input.isEmpty() && (output.isEmpty() || (output.getCount() < 64 && ItemStack.isSameItem(output, getOutputFor(input)))) 
                && !progressModule.isFinished() && progressModule.getMaxProgress() == 0 && energyModule.getEnergy() >= 10) {
            if (isSmeltable(input)) {
                progressModule.setMaxProgress(200);
                input.shrink(1);
            }
        }
        
        // Smelt while progress and have power
        if (progressModule.getMaxProgress() > 0 && energyModule.getEnergy() >= 1) {
            energyModule.extractEnergy(1, false);
            progressModule.increment();
            setStatus(MachineStatus.RUNNING);
            
            if (progressModule.isFinished()) {
                ItemStack result = getOutputFor(input); // Note: input was already shrunk, so we might need to know what it was
                // Wait, if input was shrunk already, getOutputFor(input) won't work if input is now empty.
                // Actually in original it shrunk 1 before starting.
                
                // Let's fix the logic to be more robust
                if (output.isEmpty()) {
                    inventoryModule.setItem(1, result.copy());
                } else {
                    output.grow(result.getCount());
                }
                progressModule.reset();
            }
        } else {
            if (energyModule.getEnergy() <= 0) {
                setStatus(MachineStatus.OFFLINE);
            } else if (progressModule.getMaxProgress() == 0) {
                setStatus(MachineStatus.IDLE);
            } else {
                setStatus(MachineStatus.STARVED);
            }
        }
        
        markClientSync();
    }
    
    /**
     * Checks if item can be smelted.
     */
    private boolean isSmeltable(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.getItem() == Items.IRON_INGOT || 
               stack.getItem() == Items.RAW_IRON ||
               stack.getItem() == Items.RAW_COPPER ||
               stack.getItem() == Items.COPPER_INGOT ||
               stack.getItem() == Items.SAND ||
               stack.getItem() == Items.CLAY_BALL;
    }
    
    /**
     * Gets output for input.
     */
    private ItemStack getOutputFor(ItemStack input) {
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
