package com.industrium.core.api.power;

/**
 * Represents the different voltage tiers in the Industrium power system.
 * 
 * Each tier has a maximum energy capacity and defines what machines can operate at that voltage.
 * Higher tiers can generally handle more power but may damage lower-tier equipment.
 */
public enum VoltageTier {
    /**
     * Low Voltage - 10 FE baseline capacity.
     * Basic machines and components.
     */
    LV(10, "low"),
    
    /**
     * Medium Voltage - 100 FE capacity.
     * Intermediate industrial machinery.
     */
    MV(100, "medium"),
    
    /**
     * High Voltage - 1000 FE capacity.
     * Heavy industrial and advanced machinery.
     */
    HV(1000, "high"),
    
    /**
     * Extreme High Voltage - 10000 FE capacity.
     * Maximum power industrial systems.
     */
    EHV(10000, "extreme");
    
    private final long capacity;
    private final String name;
    
    VoltageTier(long capacity, String name) {
        this.capacity = capacity;
        this.name = name;
    }
    
    /**
     * Gets the maximum energy capacity for this voltage tier.
     * 
     * @return Maximum FE capacity
     */
    public long getCapacity() {
        return capacity;
    }
    
    /**
     * Gets the human-readable name of this tier.
     * 
     * @return Tier name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Checks if this tier can accept energy from the given tier without conversion loss.
     * 
     * @param other The other voltage tier
     * @return True if compatible
     */
    public boolean canAccept(VoltageTier other) {
        return this.equals(other);
    }
    
    /**
     * Gets the tier from its ordinal position safely.
     * 
     * @param ordinal The tier index (0-3)
     * @return The voltage tier, or LV if invalid
     */
    public static VoltageTier fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            return LV;
        }
        return values()[ordinal];
    }
}