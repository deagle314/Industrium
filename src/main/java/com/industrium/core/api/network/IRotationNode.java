package com.industrium.core.api.network;

/**
 * Specialized node for rotation networks.
 */
public interface IRotationNode extends IIndustriumNode {
    /**
     * Gets current RPM of the node.
     */
    double getRPM();

    /**
     * Sets current RPM of the node.
     */
    void setRPM(double rpm);

    /**
     * Gets the current net torque applied to this node in N*m.
     */
    double getTorque();

    /**
     * Sets the current net torque applied to this node.
     */
    void setTorque(double torque);

    /**
     * Gets the inertia of this node in kg*m^2.
     */
    double getInertia();

    /**
     * Gets the friction coefficient of this node.
     */
    double getFriction();

    /**
     * Gets the maximum RPM this node can withstand before failure.
     */
    double getMaxRPM();

    /**
     * Gets the maximum torque this node can withstand before failure.
     */
    double getMaxTorque();

    /**
     * Gets the efficiency of torque transfer through this node (0.0 to 1.0).
     */
    default double getEfficiency() {
        return 1.0;
    }
}
