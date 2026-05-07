package com.industrium.core.api.power;

/**
 * A capability representing an energy storage that can receive and output Forge Energy (FE).
 * 
 * This is the core interface for Industrium's power system, providing a unified API for
 * energy storage that is compatible with Forge's FE system while adding voltage tier validation.
 * 
 * Implementations should handle energy loss on transfer and validate voltage compatibility.
 */
public interface IEnergyStorage {
    
    /**
     * Gets the current energy level.
     * 
     * @return Current FE stored
     */
    long getEnergy();
    
    /**
     * Gets the maximum energy capacity.
     * 
     * @return Maximum FE capacity
     */
    long getMaxEnergy();
    
    /**
     * Gets the voltage tier of this storage.
     * 
     * @return The voltage tier
     */
    VoltageTier getVoltageTier();
    
    /**
     * Receives energy into this storage.
     * 
     * @param amount Maximum energy to receive
     * @param simulate If true, don't actually receive
     * @return Energy actually received
     */
    long receiveEnergy(long amount, boolean simulate);
    
    /**
     * Extracts energy from this storage.
     * 
     * @param amount Maximum energy to extract
     * @param simulate If true, don't actually extract
     * @return Energy actually extracted
     */
    long extractEnergy(long amount, boolean simulate);
    
    /**
     * Checks if this storage can accept energy from the given tier.
     * 
     * @param tier The incoming voltage tier
     * @return True if compatible
     */
    boolean canAccept(VoltageTier tier);
    
    /**
     * Checks if energy can be extracted to the given tier.
     * 
     * @param tier The target voltage tier
     * @return True if compatible
     */
    boolean canExtractTo(VoltageTier tier);
    
    /**
     * Gets the energy transfer rate per tick.
     * 
     * @return Maximum FE per tick
     */
    long getTransferRate();
    
    /**
     * Checks if this storage is full.
     * 
     * @return True if at maximum capacity
     */
    default boolean isFull() {
        return getEnergy() >= getMaxEnergy();
    }
    
    /**
     * Checks if this storage is empty.
     * 
     * @return True if empty
     */
    default boolean isEmpty() {
        return getEnergy() <= 0;
    }
    
    /**
     * Gets the percentage of capacity filled.
     * 
     * @return 0.0 to 1.0
     */
    default float getFillPercentage() {
        if (getMaxEnergy() <= 0) return 0;
        return (float) getEnergy() / getMaxEnergy();
    }
}