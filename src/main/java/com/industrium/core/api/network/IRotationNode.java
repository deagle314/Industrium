package com.industrium.core.api.network;

/**
 * Specialized node for mechanical rotation networks.
 * 
 * Defines data contracts for rotational motion with RPM and torque,
 * as well as mechanical properties like inertia and friction.
 */
public interface IRotationNode extends IIndustriumNode, IPhysicalNode {

    /**
     * Gets the current rotational speed in RPM.
     * 
     * @return Rotations per minute
     */
    double getRPM();

    /**
     * Sets the current rotational speed in RPM.
     * 
     * @param rpm Rotations per minute
     */
    void setRPM(double rpm);

    /**
     * Gets the current net torque applied to this node.
     * 
     * @return Torque in N*m (Newtons per meter)
     */
    double getTorque();

    /**
     * Gets the friction coefficient of this node.
     * 
     * Affects how quickly rotational energy is dissipated.
     * 
     * @return Friction coefficient (0.0 to 1.0)
     */
    default double getFriction() {
        return 0.1;
    }

    /**
     * Gets the maximum safe RPM before mechanical failure.
     * 
     * @return Maximum safe RPM
     */
    default double getMaxRPM() {
        return 1000.0;
    }

    /**
     * Gets the maximum safe torque before mechanical failure.
     * 
     * @return Maximum safe torque in N*m
     */
    default double getMaxTorque() {
        return 1000.0;
    }

    /**
     * Gets the efficiency of torque transfer through this node.
     * 
     * @return Efficiency from 0.0 (no transfer) to 1.0 (perfect transfer)
     */
    default double getEfficiency() {
        return 1.0;
    }

    /**
     * Calculates the mechanical power being transmitted.
     * 
     * Power = (RPM * Torque) / 60
     * 
     * @return Power in watts
     */
    default double getMechanicalPower() {
        return (getRPM() * Math.abs(getTorque())) / 60.0;
    }

    /**
     * Checks if this node is over-stressed.
     * 
     * @return true if RPM or torque exceeds maximum safe values
     */
    default boolean isOverstressed() {
        return Math.abs(getRPM()) > getMaxRPM() || Math.abs(getTorque()) > getMaxTorque();
    }
}