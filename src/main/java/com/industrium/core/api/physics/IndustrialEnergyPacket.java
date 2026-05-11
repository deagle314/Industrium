package com.industrium.core.api.physics;

import com.industrium.core.api.network.SystemType;

/**
 * Abstraction for energy packets transferred between systems.
 */
public record IndustrialEnergyPacket(
    SystemType sourceType,
    double amount,
    double quality, // Temperature for Heat, Pressure for Fluid, RPM for Rotation, Voltage for Power
    long timestamp
) {}
