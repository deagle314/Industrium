package com.industrium.core.common.physics;

import com.industrium.core.api.network.SystemType;
import com.industrium.core.api.physics.IndustrialEnergyPacket;

/**
 * Manages cross-system energy conversion between Power, Heat, Rotation, and Fluid.
 */
public class EnergyConversionManager {

    // Conversion constants (calibrated for game balance)
    // 1 FE (Forge Energy) is used as the base unit.
    
    public static final double FE_TO_HEAT = 0.5;
    public static final double HEAT_TO_FE = 1.0 / FE_TO_HEAT;
    
    public static final double FE_TO_ROTATION = 1.0; 
    public static final double ROTATION_TO_FE = 1.0 / FE_TO_ROTATION;

    public static final double FLUID_TO_FE = 10.0; // Energy density of a standard fuel mB
    public static final double FE_TO_FLUID = 1.0 / FLUID_TO_FE;
    
    /**
     * Converts an energy packet from its source system to a target system.
     * 
     * @param packet The source energy packet
     * @param targetType The target system type
     * @return A new energy packet in the target system's units
     */
    public static IndustrialEnergyPacket convert(IndustrialEnergyPacket packet, SystemType targetType) {
        if (packet.sourceType() == targetType) return packet;
        
        double baseEnergy = toBaseEnergy(packet);
        return fromBaseEnergy(baseEnergy, targetType, packet.quality());
    }
    
    private static double toBaseEnergy(IndustrialEnergyPacket packet) {
        return switch (packet.sourceType()) {
            case POWER -> packet.amount();
            case HEAT -> packet.amount() * HEAT_TO_FE;
            case ROTATION -> packet.amount() * ROTATION_TO_FE;
            case FLUID -> packet.amount() * FLUID_TO_FE;
            default -> packet.amount();
        };
    }
    
    private static IndustrialEnergyPacket fromBaseEnergy(double baseEnergy, SystemType targetType, double quality) {
        double amount = switch (targetType) {
            case POWER -> baseEnergy;
            case HEAT -> baseEnergy * FE_TO_HEAT;
            case ROTATION -> baseEnergy * FE_TO_ROTATION;
            case FLUID -> baseEnergy * FE_TO_FLUID;
            default -> baseEnergy;
        };
        
        return new IndustrialEnergyPacket(targetType, amount, quality, System.currentTimeMillis());
    }
}
