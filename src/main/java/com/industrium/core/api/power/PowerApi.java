package com.industrium.core.api.power;

/**
 * Main API entry point for the Industrium Power System.
 * 
 * This provides access to the cable network, energy transfer management,
 * and power system utilities.
 */
public final class PowerApi {
    
    public static final String POWER_NETWORK = "industrium:power";
    
    private PowerApi() {
        // Utility class - no instantiation
    }
    
    /**
     * Creates a new energy storage capability for the given voltage tier.
     * 
     * @param tier The voltage tier
     * @param capacity The capacity multiplier (1 = baseline tier capacity)
     * @return New energy storage instance
     */
    public static IEnergyStorage createStorage(VoltageTier tier, int capacity) {
        return new CommonEnergyStorage(tier, tier.getCapacity() * capacity);
    }
    
    /**
     * Gets a validator for checking voltage compatibility.
     * 
     * @param source Source tier
     * @param target Target tier
     * @return Compatibility result
     */
    public static VoltageCompatibility checkCompatibility(VoltageTier source, VoltageTier target) {
        if (source == target) {
            return VoltageCompatibility.COMPATIBLE;
        }
        int tierDiff = target.ordinal() - source.ordinal();
        if (tierDiff > 0) {
            return VoltageCompatibility.DOWNGRADE;
        }
        return VoltageCompatibility.UPSURGE_RISK;
    }
    
    /**
     * Gets the energy loss multiplier for transmission.
     * 
     * @param distanceBlocks Distance in blocks
     * @return Loss multiplier (1.0 = no loss)
     */
    public static double getEnergyLoss(int distanceBlocks) {
        if (distanceBlocks <= 0) return 1.0;
        return Math.max(0.1, 1.0 - (distanceBlocks * 0.001));
    }
    
    /**
     * Result of voltage compatibility check.
     */
    public enum VoltageCompatibility {
        /** Tiers match - no conversion needed */
        COMPATIBLE,
        /** Target is higher - may cause undervoltage */
        DOWNGRADE,
        /** Target is lower - UPSURGE risk, may damage equipment */
        UPSURGE_RISK,
        /** Incompatible - can damage equipment */
        INCOMPATIBLE
    }
    
    /**
     * Simple implementation of energy storage.
     */
    private static class CommonEnergyStorage implements IEnergyStorage {
        private final VoltageTier tier;
        private final long maxEnergy;
        private long energy;
        
        CommonEnergyStorage(VoltageTier tier, long maxEnergy) {
            this.tier = tier;
            this.maxEnergy = maxEnergy;
            this.energy = 0;
        }
        
        @Override
        public long getEnergy() { return energy; }
        
        @Override
        public long getMaxEnergy() { return maxEnergy; }
        
        @Override
        public VoltageTier getVoltageTier() { return tier; }
        
        @Override
        public long receiveEnergy(long amount, boolean simulate) {
            long toReceive = Math.min(amount, maxEnergy - energy);
            if (!simulate) energy += toReceive;
            return toReceive;
        }
        
        @Override
        public long extractEnergy(long amount, boolean simulate) {
            long toExtract = Math.min(amount, energy);
            if (!simulate) energy -= toExtract;
            return toExtract;
        }
        
        @Override
        public boolean canAccept(VoltageTier other) {
            return tier.canAccept(other);
        }
        
        @Override
        public boolean canExtractTo(VoltageTier other) {
            return tier.canAccept(other);
        }
        
        @Override
        public long getTransferRate() {
            return tier.getCapacity() / 10;
        }
    }
}