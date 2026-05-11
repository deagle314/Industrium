package com.industrium.core.api.network;

/**
 * Specialized node for heat networks.
 */
public interface IHeatNode extends IIndustriumNode {
    /**
     * Gets the thermal inertia of this node.
     */
    double getHeatCapacity();

    /**
     * Gets the material conductivity modifier.
     */
    double getConductivityModifier();

    /**
     * Gets the flow resistance of this node.
     */
    double getHeatResistance();

    /**
     * Gets the current temperature of this node.
     */
    double getTemperature();

    /**
     * Adds or removes heat from this node.
     * @param delta Heat in HU
     */
    void applyHeatDelta(double delta);
}
