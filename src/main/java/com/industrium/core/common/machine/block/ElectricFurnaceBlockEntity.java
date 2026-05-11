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
        this.energyModule = addModule("energy", new EnergyModule(VoltageTier.LV.getTransferRate() * 10, VoltageTier.LV));
        this.inventoryModule = addModule("inventory", new InventoryModule(2));
        this.progressModule = addModule("progress", new ProgressModule());
        this.status = MachineStatus.IDLE;
    }
    
    @Override
    public void tickServer() {
        super.tickServer();
        
        ItemStack input = inventoryModule.getItem(0);
        ItemStack output = inventoryModule.getItem(1);
        
        boolean canSmelt = !input.isEmpty() && isSmeltable(input);
        
        if (canSmelt) {
            ItemStack result = getOutputFor(input);
            boolean canOutput = output.isEmpty() || (ItemStack.isSameItem(output, result) && output.getCount() < output.getMaxStackSize());
            
            if (canOutput) {
                if (progressModule.getMaxProgress() == 0) {
                    progressModule.setMaxProgress(100);
                }
                
                if (energyModule.getEnergy() >= 1) {
                    energyModule.extractEnergy(1, false);
                    progressModule.increment();
                    setStatus(MachineStatus.RUNNING);
                    
                    if (progressModule.isFinished()) {
                        input.shrink(1);
                        if (output.isEmpty()) {
                            inventoryModule.setItem(1, result.copy());
                        } else {
                            output.grow(result.getCount());
                        }
                        progressModule.reset();
                    }
                } else {
                    setStatus(MachineStatus.STARVED);
                }
            } else {
                setStatus(MachineStatus.JAMMED);
            }
        } else {
            if (progressModule.getProgress() > 0) {
                progressModule.reset();
            }
            setStatus(MachineStatus.IDLE);
        }
    }
    
    /**
     * Checks if item can be smelted.
     */
    private boolean isSmeltable(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return !getOutputFor(stack).isEmpty();
    }
    
    /**
     * Gets output for input.
     */
    private ItemStack getOutputFor(ItemStack input) {
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
        return 1;
    }
    
    @Override
    public boolean canOperate(VoltageTier tier) {
        return energyModule.canAccept(tier);
    }
    
    @Override
    public boolean isOperating() {
        return getStatus() == MachineStatus.RUNNING;
    }
    
    @Override
    public long getPowerDemand() {
        return (progressModule.getMaxProgress() > 0 && !progressModule.isFinished()) ? 1 : 0;
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
