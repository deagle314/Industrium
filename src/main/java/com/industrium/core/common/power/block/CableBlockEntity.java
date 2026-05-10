package com.industrium.core.common.power.block;

import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import com.industrium.core.common.registry.PowerModule;
import com.industrium.core.api.power.VoltageTier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Cable Block Entity - handles power connections and network membership.
 * Each cable segment tracks connected networks for efficient power transfer.
 */
public class CableBlockEntity extends BaseMachineBlockEntity {
    
    private VoltageTier cableTier = VoltageTier.LV;
    private String networkId = "";
    private boolean isConnected = false;
    private int connectionCount = 0;
    
    // For network recalculation
    private boolean needsNetworkUpdate = false;
    
    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(PowerModule.POWER_CABLE_BE.get(), pos, state);
    }
    
    /**
     * Sets the network this cable belongs to.
     */
    public void setNetwork(String networkId) {
        if (!this.networkId.equals(networkId)) {
            this.networkId = networkId;
            isConnected = true;
            markClientSync();
        }
    }
    
    /**
     * Gets the connected network ID.
     */
    public String getNetwork() {
        return networkId;
    }
    
    /**
     * Gets cable tier.
     */
    public VoltageTier getCableTier() {
        return cableTier;
    }
    
    /**
     * Sets cable tier.
     */
    public void setCableTier(VoltageTier tier) {
        this.cableTier = tier;
    }
    
    /**
     * Checks if connected to network.
     */
    public boolean isConnected() {
        return isConnected;
    }
    
    /**
     * Gets connection count.
     */
    public int getConnectionCount() {
        return connectionCount;
    }
    
    /**
     * Sets connection count.
     */
    public void setConnectionCount(int count) {
        this.connectionCount = count;
    }
    
    /**
     * Marks for network recalculation.
     */
    public void markNetworkUpdate() {
        this.needsNetworkUpdate = true;
        markClientSync();
    }
    
    /**
     * Clears network update flag.
     */
    public void clearNetworkUpdate() {
        this.needsNetworkUpdate = false;
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("NetworkId", networkId);
        tag.putString("CableTier", cableTier.name());
        tag.putBoolean("IsConnected", isConnected);
        tag.putInt("ConnectionCount", connectionCount);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        networkId = tag.getString("NetworkId");
        cableTier = VoltageTier.valueOf(tag.getString("CableTier"));
        isConnected = tag.getBoolean("IsConnected");
        connectionCount = tag.getInt("ConnectionCount");
    }
    
    @Override
    protected void saveClientData(CompoundTag tag) {
        super.saveClientData(tag);
        tag.putString("NetworkId", networkId);
        tag.putBoolean("IsConnected", isConnected);
        tag.putInt("ConnectionCount", connectionCount);
    }
    
    @Override
    protected void loadClientData(CompoundTag tag) {
        super.loadClientData(tag);
        if (tag.contains("NetworkId")) {
            networkId = tag.getString("NetworkId");
        }
        if (tag.contains("IsConnected")) {
            isConnected = tag.getBoolean("IsConnected");
        }
        if (tag.contains("ConnectionCount")) {
            connectionCount = tag.getInt("ConnectionCount");
        }
    }
}