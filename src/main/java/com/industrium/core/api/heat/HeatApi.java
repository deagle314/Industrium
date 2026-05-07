package com.industrium.core.api.heat;

import com.industrium.core.api.heat.HeatTier;

/**
 * Main API entry point for the Industrium Heat System.
 * Provides factory methods and utilities.
 */
public final class HeatApi {
    
    public static final String HEAT_NETWORK = "industrium:heat";
    
    /** Default ambient temperature in °C */
    public static final double AMBIENT_TEMPERATURE = 20.0;
    
    private HeatApi() {
        // Utility class
    }
    
    /**
     * Creates a new heat container with default properties.
     * 
     * @param capacity HU capacity
     * @return New container
     */
    public static IHeatContainer createContainer(double capacity) {
        return new CommonHeatContainer(capacity);
    }
    
    /**
     * Creates a heat container with custom thermal mass.
     * 
     * @param capacity HU capacity
     * @param conductivity heat transfer rate
     * @return New container
     */
    public static IHeatContainer createContainer(double capacity, double conductivity) {
        return new CommonHeatContainer(capacity, conductivity);
    }
    
    /**
     * Checks if temperature is hot enough to cause damage.
     */
    public static boolean isDangerous(double celsius) {
        return celsius > 100.0;
    }
    
    /**
     * Checks if temperature exceeds safe operating limits.
     */
    public static boolean isOverheated(double celsius, HeatTier tier) {
        return celsius > tier.getMax();
    }
    
    /**
     * Gets the temperature difference in Celsius.
     */
    public static double getDeltaTemp(double a, double b) {
        return Math.abs(a - b);
    }
    
    /**
     * Calculates heat transfer using simplified Newtonian law.
     */
    public static double calculateTransfer(double deltaTemp, double conductivity, double time) {
        return deltaTemp * conductivity * time;
    }
    
    /**
     * Converts power (FE) to heat (HU) with efficiency.
     */
    public static double powerToHeat(long fe, double efficiency) {
        return fe * efficiency;
    }
    
    /**
     * Converts heat (HU) to power (FE) with efficiency.
     */
    public static double heatToPower(double hu, double efficiency) {
        return hu * efficiency;
    }
    
    /**
     * Common heat container implementation.
     */
    private static class CommonHeatContainer implements IHeatContainer {
        private final double maxHeat;
        private final double conductivity;
        private double heat;
        private double temperature;
        
        CommonHeatContainer(double maxHeat) {
            this(maxHeat, 1.0);
        }
        
        CommonHeatContainer(double maxHeat, double conductivity) {
            this.maxHeat = maxHeat;
            this.conductivity = conductivity;
            this.heat = 0;
            this.temperature = AMBIENT_TEMPERATURE;
        }
        
        @Override
        public double getHeat() { return heat; }
        
        @Override
        public double getMaxHeat() { return maxHeat; }
        
        @Override
        public double getTemperature() { return temperature; }
        
        @Override
        public double getConductivity() { return conductivity; }
        
        @Override
        public double receiveHeat(double amount, boolean simulate) {
            double toReceive = Math.min(amount, maxHeat - heat);
            if (!simulate) {
                heat += toReceive;
                // Temperature rises with heat
                temperature = AMBIENT_TEMPERATURE + (heat / maxHeat) * 1000;
            }
            return toReceive;
        }
        
        @Override
        public double extractHeat(double amount, boolean simulate) {
            double toExtract = Math.min(amount, heat);
            if (!simulate) {
                heat -= toExtract;
                temperature = AMBIENT_TEMPERATURE + (heat / maxHeat) * 1000;
            }
            return toExtract;
        }
    }
}