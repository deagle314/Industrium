package com.industrium.core.api.heat;

/**
 * Interface for devices that produce heat.
 * 
 * Heaters increase temperature in connected heat containers.
 */
public interface IHeater {
    
    /**
     * Gets the current heating rate in HU/tick.
     * 
     * @return Heating rate
     */
    double getHeatingRate();
    
    /**
     * Gets the maximum temperature this heater can achieve.
     * 
     * @return Maximum temperature in Celsius
     */
    double getMaxTemperature();
    
    /**
     * Checks if the heater is currently active.
     * 
     * @return True if heating
     */
    boolean isHeating();
    
    /**
     * Gets the fuel type, if applicable.
     */
    default String getFuelType() {
        return null;
    }
}