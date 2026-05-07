package com.industrium.core.api.heat;

/**
 * Represents temperature in Celsius for the Industrium heat system.
 * 
 * Temperatures are used to determine heat transfer, phase changes,
 * and thermal damage thresholds.
 */
public class Temperature implements Comparable<Temperature> {
    
    public static final Temperature ABSOLUTE_ZERO = new Temperature(-273.15);
    public static final Temperature FREEZING = new Temperature(0.0);
    public static final Temperature BOILING = new Temperature(100.0);
    public static final Temperature MAGMA = new Temperature(1200.0);
    public static final Temperature INCINERATOR = new Temperature(2000.0);
    
    private final double celsius;
    
    public Temperature(double celsius) {
        this.celsius = celsius;
    }
    
    /**
     * Gets the temperature in Celsius.
     */
    public double getCelsius() {
        return celsius;
    }
    
    /**
     * Gets the temperature in Kelvin.
     */
    public double getKelvin() {
        return celsius + 273.15;
    }
    
    /**
     * Gets the temperature in Fahrenheit.
     */
    public double getFahrenheit() {
        return (celsius * 9.0 / 5.0) + 32.0;
    }
    
    /**
     * Checks if this temperature is below freezing.
     */
    public boolean isFreezing() {
        return celsius < FREEZING.celsius;
    }
    
    /**
     * Checks if this temperature is above boiling.
     */
    public boolean isBoiling() {
        return celsius > BOILING.celsius;
    }
    
    /**
     * Checks if this temperature can cause thermal damage.
     */
    public boolean isDangerous() {
        return celsius > 100.0;
    }
    
    @Override
    public int compareTo(Temperature other) {
        return Double.compare(celsius, other.celsius);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Math.abs(celsius - ((Temperature) obj).celsius) < 0.001;
    }
    
    @Override
    public int hashCode() {
        return Double.hashCode(celsius);
    }
    
    @Override
    public String toString() {
        return String.format("%.1f°C", celsius);
    }
}