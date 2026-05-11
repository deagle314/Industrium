package com.industrium.core.api.network;

/**
 * Shared contract for nodes that have physical properties affecting
 * energy or signal transmission.
 * 
 * Defines the fundamental parameters used across multiple system types
 * to calculate transmission efficiency and losses.
 */
public interface IPhysicalNode {

    /**
     * Gets the resistance of this node to energy/signal transfer.
     * 
     * Higher resistance results in greater energy loss across the node.
     * 
     * @return Resistance value (typically 0.0 to 1.0)
     */
    double getResistance();

    /**
     * Gets the conductivity modifier of this node.
     * 
     * Acts as a multiplier on the transmission rate.
     * A value of 1.0 means no modification; values below reduce throughput.
     * 
     * @return Conductivity modifier (0.0 to 1.0 range, 1.0 = neutral)
     */
    double getConductivity();

    /**
     * Gets the inertia of this node for dynamic systems.
     * 
     * Represents how resistant the node is to changes in its state.
     * Higher inertia means slower response to input changes.
     * 
     * @return Inertia value (kg*m^2 for rotational, or equivalent units)
     */
    double getInertia();
}