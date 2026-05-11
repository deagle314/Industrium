package com.industrium.core.api.network;

import com.industrium.core.api.fluid.IFluidContainer;
import net.minecraftforge.fluids.FluidStack;

/**
 * Specialized node for fluid transport networks.
 * 
 * Defines data contracts for fluid storage, pressure management,
 * and flow rate calculations.
 */
public interface IFluidNode extends IIndustriumNode, IFluidContainer {

    /**
     * Gets the viscosity of the fluid in this node.
     * 
     * Higher viscosity results in slower flow rates.
     * 
     * @return Viscosity coefficient
     */
    double getViscosity();

    /**
     * Gets the internal diameter of the pipe or container.
     * 
     * Affects flow rate calculations and pressure drop.
     * 
     * @return Diameter in internal units
     */
    double getDiameter();

    /**
     * Gets the current pressure at this node.
     * 
     * @return Pressure level
     */
    long getPressure();

    /**
     * Sets the pressure at this node.
     * 
     * @param pressure The new pressure level
     */
    void setPressure(long pressure);

    /**
     * Gets the maximum pressure this node can handle.
     * 
     * @return Maximum safe pressure
     */
    default long getMaxPressure() {
        return 1000L;
    }

    /**
     * Checks if this node is at maximum pressure.
     * 
     * @return true if pressure is at or above maximum
     */
    default boolean isOverpressurized() {
        return getPressure() >= getMaxPressure();
    }

    /**
     * Fills this node with fluid from a source.
     * 
     * @param fluid Fluid to add
     * @param simulate If true, only simulate
     * @return Amount actually filled
     */
    @Override
    int fill(FluidStack fluid, boolean simulate);

    /**
     * Drains fluid from this node.
     * 
     * @param amount Amount to drain
     * @param simulate If true, only simulate
     * @return Fluid drained
     */
    @Override
    FluidStack drain(long amount, boolean simulate);
}