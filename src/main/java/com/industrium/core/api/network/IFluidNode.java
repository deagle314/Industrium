package com.industrium.core.api.network;

import com.industrium.core.api.fluid.IFluidContainer;

/**
 * Specialized node for fluid networks.
 */
public interface IFluidNode extends IIndustriumNode, IFluidContainer {
    /**
     * Gets the viscosity of the fluid in this node.
     */
    double getViscosity();

    /**
     * Gets the diameter of the pipe/container.
     */
    double getDiameter();
    
    /**
     * Sets the pressure of this node.
     */
    void setPressure(long pressure);
}
