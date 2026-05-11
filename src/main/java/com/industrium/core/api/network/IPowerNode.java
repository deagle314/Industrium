package com.industrium.core.api.network;

import com.industrium.core.api.power.VoltageTier;

/**
 * Specialized node for power energy networks.
 * 
 * Defines data contracts for voltage tier management, energy storage,
 * and power transfer calculations.
 */
public interface IPowerNode extends IIndustriumNode, IPhysicalNode {

    /**
     * Gets the voltage tier of this power node.
     * 
     * The tier determines the safe power transfer rate and
     * compatibility with other connected nodes.
     * 
     * @return The voltage tier
     */
    VoltageTier getTier();

    /**
     * Gets the current energy stored in this node.
     * 
     * @return Energy in FE (Forge Energy units)
     */
    long getEnergy();

    /**
     * Gets the maximum energy capacity of this node.
     * 
     * @return Maximum energy in FE
     */
    long getCapacity();

    /**
     * Attempts to receive energy from this node.
     * 
     * @param maxReceive Maximum energy to receive
     * @param simulate If true, simulate only
     * @return Amount of energy actually received
     */
    long receive(long maxReceive, boolean simulate);

    /**
     * Attempts to extract energy from this node.
     * 
     * @param maxExtract Maximum energy to extract
     * @param simulate If true, simulate only
     * @return Amount of energy actually extracted
     */
    long extract(long maxExtract, boolean simulate);

    /**
     * Gets the current power draw/generation in FE/t.
     * 
     * Positive values indicate power generation, negative values
     * indicate power consumption.
     * 
     * @return Power in FE per tick
     */
    default long getPowerRate() {
        return 0L;
    }
}