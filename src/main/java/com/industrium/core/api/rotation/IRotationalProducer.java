package com.industrium.core.api.rotation;

/**
 * Interface for rotational power producers (motors, engines).
 * 
 * These convert other energy forms (FE, heat) into rotational motion.
 */
public interface IRotationalProducer {
    
    /**
     * Gets the current rotational power output.
     * 
     * @return Current rotational power
     */
    RotationalPower getOutput();
    
    /**
     * Gets the maximum RPM this producer supports.
     * 
     * @return Max RPM
     */
    double getMaxRpm();
    
    /**
     * Gets the maximum power output.
     * 
     * @return Max power units
     */
    double getMaxPower();
    
    /**
     * Checks if this producer is active.
     */
    boolean isActive();
}