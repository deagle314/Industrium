package com.industrium.core.api.logistics;

/**
 * Enum representing item transfer priorities.
 */
public enum ItemPriority {
    LOW(0),
    NORMAL(1),
    HIGH(2),
    CRITICAL(3);
    
    private final int level;
    
    ItemPriority(int level) {
        this.level = level;
    }
    
    public int getLevel() {
        return level;
    }
}