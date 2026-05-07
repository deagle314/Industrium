package com.industrium.core.api.rotation;

/**
 * Interface for rotational power consumers (machines, pumps).
 * 
 * These convert rotational motion into work.
 */
public interface IRotationalConsumer {
    
    /**
     * Gets the required rotational power for operation.
     * 
     * @return Required power
     */
    RotationalPower getRequired();
    
    /**
     * Inputs rotational power.
     * 
     * @param power Input power
     */
    void inputPower(RotationalPower power);
    
    /**
     * Checks if sufficient power is available.
     */
    boolean hasSufficientPower();
    
    /**
     * Gets efficiency (0.0 to 1.0).
     */
    default double getEfficiency() {
        return 0.9;
    }
}