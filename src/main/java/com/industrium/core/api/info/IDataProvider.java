package com.industrium.core.api.info;

/**
 * Interface for devices that provide data signals.
 * Sensors, controllers, and other data sources.
 */
public interface IDataProvider {
    
    /**
     * Gets the current signal output.
     */
    ControlSignal getSignal();
    
    /**
     * Reads a numeric value by key.
     */
    default double readValue(String key) {
        return getSignal().getValue();
    }
    
    /**
     * Gets the channel/address.
     */
    default String getChannel() {
        return "default";
    }
    
    /**
     * Checks if signal is active.
     */
    default boolean hasSignal() {
        return getSignal().isActive();
    }
}