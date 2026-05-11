package com.industrium.core.api.network;

import java.util.UUID;

/**
 * Interface for reactive graph participation in network simulations.
 * 
 * Implementations can respond to lifecycle events when joining or leaving
 * a network, enabling proper setup and teardown of network-related state.
 * 
 * This interface is designed to be mixed into nodes that require reactive
 * behavior beyond passive data access, such as connection management,
 * state initialization, or cleanup operations.
 */
public interface INetworkAware {

    /**
     * Called when this node joins a network.
     * 
     * @param networkId The unique identifier of the network being joined
     */
    default void onNetworkJoin(UUID networkId) {
        setNetworkId(networkId);
    }

    /**
     * Called when this node leaves a network.
     * 
     * @param networkId The unique identifier of the network being left
     */
    default void onNetworkLeave(UUID networkId) {
        setNetworkId(null);
    }

    /**
     * Sets the network ID for this node.
     * 
     * @param id The network ID, or null if not part of any network
     */
    void setNetworkId(UUID id);

    /**
     * Gets the current network ID for this node.
     * 
     * @return The network ID, or null if not part of any network
     */
    UUID getNetworkId();
}