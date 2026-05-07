package com.industrium.core.api.fluid;

/**
 * Interface for fluid processors (boilers, converters).
 * 
 * These modify fluid state (temperature, pressure, conversion).
 */
public interface IFluidProcessor {
    
    /**
     * Gets the input fluid state.
     */
    FluidState getInputState();
    
    /**
     * Gets the output fluid state.
     */
    FluidState getOutputState();
    
    /**
     * Processing rate in mb/tick.
     */
    long getProcessRate();
    
    /**
     * Temperature threshold for processing.
     */
    double getRequiredTemperature();
    
    /**
     * Checks if the processor is active.
     */
    boolean isProcessing();
}