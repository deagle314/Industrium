package com.industrium.core.api.info;

/**
 * Interface for machines that can be controlled remotely.
 */
public interface IControllableMachine {
    
    /**
     * Starts the machine.
     */
    void start();
    
    /**
     * Stops the machine.
     */
    void stop();
    
    /**
     * Checks if running.
     */
    boolean isRunning();
    
    /**
     * Sets the operation mode.
     */
    default void setMode(String mode) {
        // Optional
    }
    
    /**
     * Gets the current mode.
     */
    default String getMode() {
        return "idle";
    }
    
    /**
     * Sets a target value.
     */
    default void setTarget(double value) {
        // Optional
    }
    
    /**
     * Gets the target value.
     */
    default double getTarget() {
        return 0;
    }
}