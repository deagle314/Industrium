package com.industrium.core.common.system;

/**
 * Generic network node interface.
 * Used by all Industrium network types.
 */
public interface INetworkNode {
    
    /**
     * Gets the network ID this node belongs to.
     */
    String getNetworkId();
    
    /**
     * Sets the network ID.
     */
    void setNetworkId(String id);
    
    /**
     * Gets the node position in world.
     */
    default long getPosition() {
        return 0L;
    }
    
    /**
     * Marks this node as dirty (needs topology recalc).
     */
    default void markDirty() {
        // Override to implement
    }
    
    /**
     * Checks if node is valid.
     */
    default boolean isValid() {
        return true;
    }
}