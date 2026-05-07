package com.industrium.core.api.heat;

/**
 * Temperature tiers for machine requirements.
 * Defines common temperature bands for industrial processes.
 */
public enum HeatTier {
    /** Ambient - room temperature ~20°C */
    AMBIENT(20, "ambient"),
    
    /** Warm - low heat applications 50-100°C */
    WARM(75, "warm"),
    
    /** Hot - basic industrial 200-400°C */
    HOT(300, "hot"),
    
    /** Very Hot - metal treatment 600-800°C */
    VERY_HOT(700, "very_hot"),
    
    /** Extreme - forge operations 1000-1400°C */
    EXTREME(1200, "extreme"),
    
    /** Blasting - steel processing 1600-1800°C */
    BLAST(1700, "blast"),
    
    /** Reactor - extreme future 2500+°C */
    REACTOR(2500, "reactor");
    
    private final double baselineTemp;
    private final String name;
    
    HeatTier(double baseline, String name) {
        this.baselineTemp = baseline;
        this.name = name;
    }
    
    /**
     * Gets the baseline temperature for this tier.
     */
    public double getBaseline() {
        return baselineTemp;
    }
    
    /**
     * Gets the tier name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns minimum temperature bound.
     */
    public double getMin() {
        return baselineTemp * 0.8;
    }
    
    /**
     * Returns maximum temperature bound.
     */
    public double getMax() {
        return baselineTemp * 1.2;
    }
    
    /**
     * Checks if a temperature is within this tier's range.
     */
    public boolean contains(double temp) {
        return temp >= getMin() && temp <= getMax();
    }
    
    /**
     * Gets the next higher tier, or REACTOR if max.
     */
    public HeatTier stepUp() {
        int next = ordinal() + 1;
        if (next >= values().length) return REACTOR;
        return values()[next];
    }
    
    /**
     * Gets the next lower tier, or AMBIENT if min.
     */
    public HeatTier stepDown() {
        int next = ordinal() - 1;
        if (next < 0) return AMBIENT;
        return values()[next];
    }
}