package com.industrium.core.api.fluid;

/**
 * Represents fluid properties in the Industrium fluid system.
 * 
 * Handles gases and liquids with pressure, temperature, and throughput values.
 */
public class FluidState {
    
    public static final FluidState AIR = new FluidState(0, 20, 1000, true);
    public static final FluidState WATER = new FluidState(1000, 20, 1000, false);
    public static final FluidState STEAM = new FluidState(1000, 100, 500, true);
    public static final FluidState LAVA = new FluidState(1000, 500, 200, false);
    
    private final long quantity;  // mb (millibuckets)
    private final double temperature;  // Celsius
    private final long pressure;  // in units
    private final boolean isGas;
    
    public FluidState(long quantity, double temperature, long pressure, boolean isGas) {
        this.quantity = quantity;
        this.temperature = temperature;
        this.pressure = pressure;
        this.isGas = isGas;
    }
    
    public long getQuantity() {
        return quantity;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public long getPressure() {
        return pressure;
    }
    
    public boolean isGas() {
        return isGas;
    }
    
    public boolean isLiquid() {
        return !isGas;
    }
    
    /**
     * Checks if this fluid can exist at the given temperature without phase change.
     */
    public boolean isValidAt(double temp) {
        if (isGas) {
            return true;  // Gases can exist at various temps
        }
        return !(temp > 100 && this == WATER);  // Water becomes steam above boiling
    }
    
    /**
     * Creates a new state with modified quantity.
     */
    public FluidState withQuantity(long quantity) {
        return new FluidState(quantity, temperature, pressure, isGas);
    }
    
    /**
     * Creates a new state with modified temperature.
     */
    public FluidState withTemperature(double temperature) {
        return new FluidState(quantity, temperature, pressure, isGas);
    }
    
    /**
     * Creates a new state with modified pressure.
     */
    public FluidState withPressure(long pressure) {
        return new FluidState(quantity, temperature, pressure, isGas);
    }
}