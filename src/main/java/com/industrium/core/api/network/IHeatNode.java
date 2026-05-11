package com.industrium.core.api.network;

/**
 * Specialized node for heat transfer networks.
 * 
 * Defines data contracts for temperature management, thermal capacity,
 * and heat flow calculations.
 */
public interface IHeatNode extends IIndustriumNode, IPhysicalNode {

    /**
     * Gets the current temperature of this node.
     * 
     * @return Temperature in Celsius (or Kelvin depending on configuration)
     */
    double getTemperature();

    /**
     * Sets the temperature of this node.
     * 
     * @param temperature The new temperature
     */
    void setTemperature(double temperature);

    /**
     * Gets the thermal heat capacity of this node.
     * 
     * Higher capacity means the node resists temperature changes more.
     * 
     * @return Heat capacity in HU per degree
     */
    double getHeatCapacity();

    /**
     * Gets the maximum temperature this node can withstand before failure.
     * 
     * @return Maximum safe temperature
     */
    double getMaxTemperature();

    /**
     * Gets the minimum temperature this node can withstand before failure.
     * 
     * @return Minimum safe temperature
     */
    double getMinTemperature();

    /**
     * Applies a heat delta to this node.
     * 
     * @param delta Heat to add (positive) or remove (negative) in HU
     */
    default void applyHeatDelta(double delta) {
        double newTemp = getTemperature() + (delta / getHeatCapacity());
        newTemp = Math.max(getMinTemperature(), Math.min(getMaxTemperature(), newTemp));
        setTemperature(newTemp);
    }

    /**
     * Gets the current heat content of this node.
     * 
     * @return Heat content in HU
     */
    default double getHeatContent() {
        return getTemperature() * getHeatCapacity();
    }

    /**
     * Checks if this node is at critical temperature.
     * 
     * @return true if temperature is at or above maximum safe temperature
     */
    default boolean isOverheated() {
        return getTemperature() >= getMaxTemperature();
    }
}