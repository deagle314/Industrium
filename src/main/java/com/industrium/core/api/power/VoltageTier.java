package com.industrium.core.api.power;

/**
 * Represents the different voltage tiers in the Industrium power system.
 * 
 * Each tier represents the safe transfer class in FE/t (Energy per tick).
 * Higher tiers can transmit more power but may damage lower-tier equipment.
 * 
 * LV: 10 FE/t - starter machines, lamps, small motors
 * MV: 1,000 FE/t - factories, processing lines
 * HV: 10,000 FE/t - steel mills, rail power
 * EHV: 1,000,000 FE/t - megabases, long distance backbone
 */
public enum VoltageTier {
    /** Low Voltage - 10 FE/t baseline */
    LV(10, "low"),
    
    /** Medium Voltage - 1,000 FE/t */
    MV(1000, "medium"),
    
    /** High Voltage - 10,000 FE/t */
    HV(10000, "high"),
    
    /** Extreme High Voltage - 1,000,000 FE/t */
    EHV(1000000, "extreme");
    
    private final long transferRate;
    private final String name;
    
    VoltageTier(long transferRate, String name) {
        this.transferRate = transferRate;
        this.name = name;
    }
    
    /**
     * Gets the maximum safe transfer rate for this voltage tier in FE/t.
     */
    public long getTransferRate() {
        return transferRate;
    }
    
    /**
     * Gets the human-readable name of this tier.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Checks if this tier can accept energy from the given tier without overload.
     * Same tier = safe. Lower to higher = safe (step up). Higher to lower = dangerous.
     */
    public boolean canAccept(VoltageTier other) {
        return this.ordinal() >= other.ordinal();
    }
    
    /**
     * Gets the tier from its ordinal position safely.
     */
    public static VoltageTier fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            return LV;
        }
        return values()[ordinal];
    }
    
    /**
     * Gets the next higher tier, or EHV if already at max.
     */
    public VoltageTier stepUp() {
        int next = ordinal() + 1;
        if (next >= values().length) return EHV;
        return values()[next];
    }
    
    /**
     * Gets the next lower tier, or LV if already at min.
     */
    public VoltageTier stepDown() {
        int next = ordinal() - 1;
        if (next < 0) return LV;
        return values()[next];
    }
}