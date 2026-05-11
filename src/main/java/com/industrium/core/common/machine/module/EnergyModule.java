package com.industrium.core.common.machine.module;

import com.industrium.core.api.power.IEnergyStorage;
import com.industrium.core.api.power.VoltageTier;
import net.minecraft.nbt.CompoundTag;

/**
 * Module for handling energy storage and transfer.
 * Implements IEnergyStorage for integration with the power system.
 */
public class EnergyModule implements MachineModule, IEnergyStorage {
    private final long capacity;
    private final VoltageTier tier;
    private long energy;

    public EnergyModule(long capacity, VoltageTier tier) {
        this.capacity = capacity;
        this.tier = tier;
        this.energy = 0;
    }

    @Override
    public void tick() {}

    @Override
    public void save(CompoundTag tag) {
        tag.putLong("Energy", energy);
    }

    @Override
    public void load(CompoundTag tag) {
        energy = tag.getLong("Energy");
    }

    @Override
    public long getEnergy() {
        return energy;
    }

    @Override
    public long getMaxEnergy() {
        return capacity;
    }

    @Override
    public VoltageTier getVoltageTier() {
        return tier;
    }

    @Override
    public long receiveEnergy(long amount, boolean simulate) {
        long received = Math.min(capacity - energy, Math.min(getTransferRate(), amount));
        if (!simulate) {
            energy += received;
        }
        return received;
    }

    @Override
    public long extractEnergy(long amount, boolean simulate) {
        long extracted = Math.min(energy, Math.min(getTransferRate(), amount));
        if (!simulate) {
            energy -= extracted;
        }
        return extracted;
    }

    @Override
    public boolean canAccept(VoltageTier tier) {
        return this.tier.canAccept(tier);
    }

    @Override
    public boolean canExtractTo(VoltageTier tier) {
        return tier.canAccept(this.tier);
    }

    @Override
    public long getTransferRate() {
        return tier.getTransferRate();
    }
    
    public void setEnergy(long energy) {
        this.energy = Math.min(energy, capacity);
    }
}
