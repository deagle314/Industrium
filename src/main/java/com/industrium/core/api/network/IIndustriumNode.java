package com.industrium.core.api.network;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * The root interface for all Industrium simulation nodes.
 * 
 * Every node in the simulation graph extends from this interface,
 * providing world position access and network membership tracking.
 * 
 * This interface extends {@link INetworkAware} to provide lifecycle
 * hooks for reactive graph participation.
 * 
 * Specializations for each system type are provided as sub-interfaces:
 * <ul>
 *   <li>{@link IPowerNode} for power networks</li>
 *   <li>{@link IHeatNode} for heat networks</li>
 *   <li>{@link IFluidNode} for fluid networks</li>
 *   <li>{@link IRotationNode} for rotation networks</li>
 *   <li>{@link IItemNode} for logistics networks</li>
 * </ul>
 */
public interface IIndustriumNode extends INetworkAware {

    /**
     * Gets the position of this node in the world.
     * 
     * @return The block position of this node
     */
    BlockPos getPos();

    /**
     * Gets the level/world containing this node.
     * 
     * @return The world instance
     */
    Level getLevel();

    /**
     * Gets the system type classification for this node.
     * 
     * Used for system identification and validation of network composition.
     * 
     * @return The system type this node belongs to
     */
    SystemType getSystemType();

    /**
     * Checks if this node is valid and operational.
     * 
     * @return true if the node is valid, false otherwise
     */
    default boolean isValid() {
        return getPos() != null && getLevel() != null;
    }

    /**
     * Marks this node as dirty, requiring topology recalculation.
     * 
     * Implementations should override this to trigger network graph updates.
     */
    default void markDirty() {
        // Override to implement dirty tracking
    }
}