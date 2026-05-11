package com.industrium.core.api.network;

/**
 * Classification enum for all Industrium simulation systems.
 * Used for system identification, validation, and filtering.
 */
public enum SystemType {
    /** Power/Energy transmission system (FE-based) */
    POWER("power", 0),
    
    /** Heat transfer and thermal management system */
    HEAT("heat", 1),
    
    /** Fluid/gas transport and processing system */
    FLUID("fluid", 2),
    
    /** Mechanical rotation transmission system (RPM/torque) */
    ROTATION("rotation", 3),
    
    /** Item logistics and transport system */
    LOGISTICS("logistics", 4),
    
    /** Control signal and data transmission system */
    CONTROL("control", 5);

    private final String name;
    private final int id;

    SystemType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Gets the human-readable system name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the numeric system identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets a SystemType by its numeric ID.
     * @param id The numeric ID
     * @return The corresponding SystemType, or null if invalid
     */
    public static SystemType fromId(int id) {
        for (SystemType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }
}