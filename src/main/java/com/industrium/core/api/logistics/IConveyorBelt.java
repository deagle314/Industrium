package com.industrium.core.api.logistics;

import net.minecraft.core.Direction;

/**
 * Interface for item conveyor belts.
 */
public interface IConveyorBelt {
    
    /**
     * Gets the belt direction of movement.
     * 
     * @return Direction items move
     */
    Direction getDirection();
    
    /**
     * Gets the belt speed in items per tick.
     * 
     * @return Items/tick
     */
    double getSpeed();
    
    /**
     * Gets the belt slot (for multi-lane belts).
     */
    int getSlot();
    
    /**
     * Checks if belt is running.
     */
    boolean isRunning();
}