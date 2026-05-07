package com.industrium.core.api.fluid;

import net.minecraftforge.fluids.FluidStack;

/**
 * Interface for fluid storage (tanks, pipes, processors).
 * 
 * Provides fluid handling with quantity, temperature, pressure, and throughput support.
 */
public interface IFluidContainer {
    
    /**
     * Gets the current fluid stack.
     * 
     * @return Current fluid (may be empty)
     */
    FluidStack getFluid();
    
    /**
     * Gets the fluid capacity in millibuckets.
     * 
     * @return Max capacity
     */
    long getCapacity();
    
    /**
     * Gets the current temperature in Celsius.
     * 
     * @return Temperature
     */
    double getTemperature();
    
    /**
     * Gets the current pressure.
     * 
     * @return Pressure
     */
    long getPressure();
    
    /**
     * Gets the throughput capacity (mb/tick).
     * 
     * @return Max flow rate
     */
    long getThroughput();
    
    /**
     * Fills the container with fluid.
     * 
     * @param fluid Fluid to add
     * @param simulate If true, don't actually add
     * @return Amount actually filled
     */
    int fill(FluidStack fluid, boolean simulate);
    
    /**
     * Drains fluid from the container.
     * 
     * @param amount Amount to drain
     * @param simulate If true, don't actually drain
     * @return Fluid drained
     */
    FluidStack drain(long amount, boolean simulate);
    
    /**
     * Checks if this container can hold the given fluid.
     */
    default boolean canHold(FluidStack fluid) {
        if (fluid.isEmpty()) return true;
        FluidStack current = getFluid();
        if (current.isEmpty()) return true;
        return current.isFluidEqual(fluid);
    }
    
    /**
     * Gets the fill percentage.
     */
    default float getFillPercentage() {
        if (getCapacity() <= 0) return 0;
        return (float) getFluid().getAmount() / getCapacity();
    }
}