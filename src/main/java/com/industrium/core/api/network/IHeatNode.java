package com.industrium.core.api.network;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

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
     * @return Temperature in Celsius
     */
    double getTemperature();

    /**
     * Gets the thermal heat capacity of this node.
     * 
     * Higher capacity means the node resists temperature changes more.
     * 
     * @return Heat capacity in HU per degree
     */
    double getHeatCapacity();

    /**
     * Gets the current heat content of this node.
     * 
     * @return Heat content in HU
     */
    default double getHeat() {
        return getTemperature() * getHeatCapacity();
    }

    /**
     * Gets the maximum temperature this node can withstand before failure.
     * 
     * @return Maximum safe temperature
     */
    default double getMaxTemperature() {
        return 1000.0;
    }

    /**
     * Gets the minimum temperature this node can withstand before failure.
     * 
     * @return Minimum safe temperature
     */
    default double getMinTemperature() {
        return -50.0;
    }

    /**
     * Gets the resistance to heat transfer.
     * 
     * Higher values mean slower heat transfer.
     * 
     * @return Heat resistance (0.0 to 1.0)
     */
    default double getHeatResistance() {
        return 0.5;
    }

    /**
     * Gets the conductivity modifier of this node.
     * 
     * Acts as a multiplier on the heat transfer rate.
     * 
     * @return Conductivity modifier (0.0 to 1.0, 1.0 = no modification)
     */
    default double getConductivityModifier() {
        return 1.0;
    }

    /**
     * Applies a heat delta to this node.
     * 
     * @param delta Heat to add (positive) or remove (negative) in HU
     */
    void applyHeatDelta(double delta);

    /**
     * Gets the maximum heat this node can store.
     * 
     * @return Maximum heat capacity in HU
     */
    default double getMaxHeat() {
        return getHeatCapacity() * getMaxTemperature();
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