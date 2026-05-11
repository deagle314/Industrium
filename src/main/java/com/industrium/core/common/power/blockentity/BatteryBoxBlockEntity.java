package com.industrium.core.common.power.blockentity;

import com.industrium.core.api.power.IEnergyStorage;
import com.industrium.core.api.power.VoltageTier;
import com.industrium.core.common.machine.AbstractMachineBlockEntity;
import com.industrium.core.common.machine.module.EnergyModule;
import com.industrium.core.common.registry.PowerModule;
import com.industrium.core.common.system.MachineStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Battery Box block entity.
 * Stores power and balances the network.
 */
public class BatteryBoxBlockEntity extends AbstractMachineBlockEntity implements IEnergyStorage {
    
    private final EnergyModule energyModule;
    
    public BatteryBoxBlockEntity(BlockPos pos, BlockState state) {
        super(PowerModule.BATTERY_BOX_BE.get(), pos, state);
        this.energyModule = addModule(new EnergyModule(VoltageTier.LV.getTransferRate() * 10, VoltageTier.LV));
        this.status = MachineStatus.IDLE;
    }
    
    @Override
    public void tickServer() {
        super.tickServer();
        
        // Simple charge logic - if has energy, mark as running
        if (energyModule.getEnergy() > 0) {
            setStatus(MachineStatus.RUNNING);
        } else {
            setStatus(MachineStatus.IDLE);
        }
    }
    
    // IEnergyStorage implementation
    
    @Override
    public long getEnergy() {
        return energyModule.getEnergy();
    }
    
    @Override
    public long getMaxEnergy() {
        return energyModule.getMaxEnergy();
    }
    
    @Override
    public VoltageTier getVoltageTier() {
        return energyModule.getVoltageTier();
    }
    
    @Override
    public long receiveEnergy(long amount, boolean simulate) {
        long received = energyModule.receiveEnergy(amount, simulate);
        if (!simulate && received > 0) {
            setStatus(MachineStatus.RUNNING);
        }
        return received;
    }
    
    @Override
    public long extractEnergy(long amount, boolean simulate) {
        long extracted = energyModule.extractEnergy(amount, simulate);
        if (!simulate && energyModule.getEnergy() <= 0) {
            setStatus(MachineStatus.IDLE);
        }
        return extracted;
    }
    
    @Override
    public boolean canAccept(VoltageTier other) {
        return energyModule.canAccept(other);
    }
    
    @Override
    public boolean canExtractTo(VoltageTier other) {
        return energyModule.canExtractTo(other);
    }
    
    @Override
    public long getTransferRate() {
        return energyModule.getTransferRate();
    }
    
    /**
     * Gets status text for player interaction.
     */
    public String getStatusText() {
        return energyModule.getEnergy() + " / " + energyModule.getMaxEnergy() + " FE (" + energyModule.getVoltageTier().name() + ")";
    }
    
    /**
     * Gets detailed stats for tooltip.
     */
    public String[] getStats() {
        return new String[] {
            energyModule.getVoltageTier().name() + " Battery Box",
            "Energy: " + energyModule.getEnergy() + " / " + energyModule.getMaxEnergy() + " FE",
            "Transfer: " + energyModule.getTransferRate() + " FE/t",
            "Status: " + getStatus().name()
        };
    }
}
