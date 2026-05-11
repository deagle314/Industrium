package com.industrium.core.common.power.blockentity;

import com.industrium.core.Industrium;
import com.industrium.core.api.network.IPowerNode;
import com.industrium.core.api.power.VoltageTier;
import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import com.industrium.core.common.registry.PowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

/**
 * Refactored Cable Block Entity - now a thin wrapper for the Network system.
 * Handles power connections and network membership via NetworkManager.
 */
public class CableBlockEntity extends BaseMachineBlockEntity implements IPowerNode {
    
    private VoltageTier cableTier = VoltageTier.LV;
    private UUID networkId = null;
    
    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(PowerModule.POWER_CABLE_BE.get(), pos, state);
    }
    
    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide) {
            Industrium.POWER_NETWORK_MANAGER.registerNode(this);
        }
    }
    
    @Override
    public void setRemoved() {
        if (level != null && !level.isClientSide) {
            Industrium.POWER_NETWORK_MANAGER.unregisterNode(this);
        }
        super.setRemoved();
    }
    
    @Override
    public BlockPos getPos() {
        return worldPosition;
    }
    
    @Override
    public Level getLevel() {
        return level;
    }
    
    @Override
    public UUID getNetworkId() {
        return networkId;
    }
    
    @Override
    public void setNetworkId(UUID id) {
        this.networkId = id;
        markClientSync();
    }
    
    @Override
    public VoltageTier getTier() {
        return cableTier;
    }
    
    /**
     * Sets cable tier.
     */
    public void setCableTier(VoltageTier tier) {
        this.cableTier = tier;
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (networkId != null) {
            tag.putUUID("NetworkId", networkId);
        }
        tag.putString("CableTier", cableTier.name());
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.hasUUID("NetworkId")) {
            networkId = tag.getUUID("NetworkId");
        }
        if (tag.contains("CableTier")) {
            cableTier = VoltageTier.valueOf(tag.getString("CableTier"));
        }
    }
    
    @Override
    protected void saveClientData(CompoundTag tag) {
        super.saveClientData(tag);
        if (networkId != null) {
            tag.putUUID("NetworkId", networkId);
        }
    }
    
    @Override
    protected void loadClientData(CompoundTag tag) {
        super.loadClientData(tag);
        if (tag.hasUUID("NetworkId")) {
            networkId = tag.getUUID("NetworkId");
        }
    }
}
