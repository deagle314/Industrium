package com.industrium.core.api.heat;

/**
 * Interface for cooling devices.
 * Removes heat from connected systems.
 */
public interface ICooler {
    
    /**
     * Gets the current cooling rate in HU/tick.
     */
    double getCoolingRate();
    
    /**
     * Gets the minimum temperature this cooler can achieve.
     */
    double getMinTemperature();
    
    /**
     * Checks if the cooler is active.
     */
    boolean isCooling();
    
    /**
     * Gets the cooling fluid type consumed (water, oil, etc).
     */
    default String getCoolantType() {
        return "water";
    }
    
    /**
     * Gets fluid consumption rate per tick.
     */
    default double getFluidConsumption() {
        return 0.0;
    }
}