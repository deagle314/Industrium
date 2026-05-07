package com.industrium.core.api.power;

/**
 * Interface for machines that consume energy.
 * 
 * Consumers require energy at specific voltage tiers and can be damaged by
 * incorrect voltage levels.
 */
public interface IEnergyConsumer {
    
    /**
     * Gets the required voltage tier for this consumer.
     * 
     * @return Required voltage tier
     */
    VoltageTier getRequiredTier();
    
    /**
     * Gets the energy consumption rate per tick.
     * 
     * @return FE/tick consumed when active
     */
    long getConsumptionRate();
    
    /**
     * Checks if the consumer can operate at the given tier.
     * 
     * @param tier The available voltage tier
     * @return True if can operate
     */
    boolean canOperate(VoltageTier tier);
    
    /**
     * Gets the current operating state.
     * 
     * @return True if active
     */
    boolean isOperating();
    
    /**
     * Called when voltage is too high - should handle protection.
     */
    default void onOvervoltage() {
        // Default: go to safe mode or shutdown
    }
    
    /**
     * Called when voltage is too low - should handle low power state.
     */
    default void onUndervoltage() {
        // Default: reduce efficiency or shutdown
    }
}