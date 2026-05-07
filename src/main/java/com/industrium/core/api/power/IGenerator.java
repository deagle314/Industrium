package com.industrium.core.api.power;

/**
 * Interface for energy producers that generate power.
 * 
 * Generators can produce energy at a specific rate and voltage tier.
 * This interface supports both continuous generation and batch processing.
 */
public interface IGenerator {
    
    /**
     * Generates energy for this tick.
     * 
     * @return Energy generated this tick
     */
    long generate();
    
    /**
     * Gets the maximum production rate per tick.
     * 
     * @return Maximum FE/tick
     */
    long getProductionRate();
    
    /**
     * Gets the voltage tier of produced energy.
     * 
     * @return The output voltage tier
     */
    VoltageTier getOutputTier();
    
    /**
     * Checks if the generator is currently active.
     * 
     * @return True if producing
     */
    boolean isActive();
    
    /**
     * Gets the fuel type this generator uses, if any.
     * 
     * @return Fuel name, or null if not fuel-based
     */
    String getFuelType();
    
    /**
     * Gets remaining fuel in ticks.
     * 
     * @return Remaining ticks, or -1 if not applicable
     */
    default long getFuelRemaining() {
        return -1;
    }
    
    /**
     * Gets maximum fuel capacity.
     * 
     * @return Max fuel, or -1 if not applicable
     */
    default long getMaxFuel() {
        return -1;
    }
}