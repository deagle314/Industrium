package com.industrium.core.api.network;

/**
 * Interface for reactive graph participation in network simulations.
 * 
 * Implementations can respond to lifecycle events when joining or leaving
 * a network, enabling proper setup and teardown of network-related state.
 */
public interface INetworkAware {

    /**
     * Called when this node joins a network.
     * 
     * @param networkId The unique identifier of the network being joined
     */
    default void onNetworkJoin(long networkId) {
        setNetworkId(networkId);
    }

    /**
     * Called when this node leaves a network.
     * 
     * @param networkId The unique identifier of the network being left
     */
    default void onNetworkLeave(long networkId) {
        setNetworkId(-1);
    }

    /**
     * Sets the network ID for this node.
     * 
     * @param id The network ID, or -1 if not part of any network
     */
    void setNetworkId(long id);

    /**
     * Gets the current network ID for this node.
     * 
     * @return The network ID, or -1 if not part of any network
     */
    long getNetworkId();
}
