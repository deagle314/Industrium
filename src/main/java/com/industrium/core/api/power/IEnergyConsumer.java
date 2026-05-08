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
     */
    VoltageTier getRequiredTier();
    
    /**
     * Gets the energy consumption rate per tick.
     */
    long getConsumptionRate();
    
    /**
     * Checks if the consumer can operate at the given tier.
     */
    boolean canOperate(VoltageTier tier);
    
    /**
     * Gets the current operating state.
     */
    boolean isOperating();
    
    /**
     * Gets power demand this tick.
     */
    default long getPowerDemand() {
        return isOperating() ? getConsumptionRate() : 0;
    }
    
    /**
     * Gets max power that can be received per tick.
     */
    default long getMaxPowerReceive() {
        return getRequiredTier().getTransferRate();
    }
    
    /**
     * Called when power is received.
     */
    default void onPowerReceived(long amount) {
        // Default: just consume
    }
    
    /**
     * Called when voltage is too high.
     */
    default void onOvervoltage() {
    }
    
    /**
     * Called when voltage is too low.
     */
    default void onUndervoltage() {
    }
}