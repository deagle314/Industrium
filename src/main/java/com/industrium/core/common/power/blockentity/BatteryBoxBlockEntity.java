package com.industrium.core.common.power.blockentity;

import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import com.industrium.core.api.power.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Battery Box block entity.
 * Stores power and balances the network.
 */
public class BatteryBoxBlockEntity extends BaseMachineBlockEntity implements IEnergyStorage {
    
    private VoltageTier voltageTier;
    private long maxEnergy;
    private long energy;
    private long transferRate;
    
    public BatteryBoxBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityType.BATTERY_BOX, pos, state);
        this.voltageTier = VoltageTier.LV;
        this.maxEnergy = VoltageTier.LV.getTransferRate() * 10;
        this.energy = 0;
        this.transferRate = voltageTier.getTransferRate();
        this.status = com.industrium.core.common.system.MachineStatus.IDLE;
    }
    
    @Override
    public void tickServer() {
        super.tickServer();
        
        // Simple charge logic - if has energy, mark as running
        if (energy > 0) {
            setStatus(com.industrium.core.common.system.MachineStatus.RUNNING);
        } else {
            setStatus(com.industrium.core.common.system.MachineStatus.IDLE);
        }
    }
    
    // IEnergyStorage implementation
    
    @Override
    public long getEnergy() {
        return energy;
    }
    
    @Override
    public long getMaxEnergy() {
        return maxEnergy;
    }
    
    @Override
    public VoltageTier getVoltageTier() {
        return voltageTier;
    }
    
    @Override
    public long receiveEnergy(long amount, boolean simulate) {
        long toReceive = Math.min(amount, maxEnergy - energy);
        if (!simulate) {
            energy += toReceive;
            setStatus(com.industrium.core.common.system.MachineStatus.RUNNING);
        }
        return toReceive;
    }
    
    @Override
    public long extractEnergy(long amount, boolean simulate) {
        long toExtract = Math.min(amount, energy);
        if (!simulate) {
            energy -= toExtract;
            if (energy <= 0) {
                setStatus(com.industrium.core.common.system.MachineStatus.IDLE);
            }
        }
        return toExtract;
    }
    
    @Override
    public boolean canAccept(VoltageTier other) {
        return voltageTier.canAccept(other);
    }
    
    @Override
    public boolean canExtractTo(VoltageTier other) {
        return voltageTier.canAccept(other);
    }
    
    @Override
    public long getTransferRate() {
        return transferRate;
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong("Energy", energy);
        tag.putLong("MaxEnergy", maxEnergy);
        tag.putString("VoltageTier", voltageTier.name());
        tag.putLong("TransferRate", transferRate);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energy = tag.getLong("Energy");
        maxEnergy = tag.getLong("MaxEnergy");
        voltageTier = VoltageTier.valueOf(tag.getString("VoltageTier"));
        transferRate = tag.getLong("TransferRate");
    }
    
    @Override
    protected void saveClientData(CompoundTag tag) {
        super.saveClientData(tag);
        tag.putLong("Energy", energy);
    }
    
    @Override
    protected void loadClientData(CompoundTag tag) {
        super.loadClientData(tag);
        if (tag.contains("Energy")) {
            energy = tag.getLong("Energy");
        }
    }
}