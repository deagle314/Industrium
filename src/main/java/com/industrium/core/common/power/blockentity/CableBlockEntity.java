package com.industrium.core.common.power.blockentity;

import com.industrium.core.Industrium;
import com.industrium.core.api.network.IPowerNode;
import com.industrium.core.api.network.SystemType;
import com.industrium.core.api.power.VoltageTier;
import com.industrium.core.common.machine.AbstractMachineBlockEntity;
import com.industrium.core.common.registry.PowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Refactored Cable Block Entity - now a thin wrapper for the Network system.
 * Handles power connections and network membership via NetworkManager.
 */
public class CableBlockEntity extends AbstractMachineBlockEntity implements IPowerNode {
    
    private VoltageTier cableTier = VoltageTier.LV;
    private long networkId = -1;
    
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
    public long getNetworkId() {
        return networkId;
    }
    
    @Override
    public void setNetworkId(long id) {
        this.networkId = id;
        markClientSync();
    }
    
    @Override
    public VoltageTier getTier() {
        return cableTier;
    }
    
    @Override
    public SystemType getSystemType() {
        return SystemType.POWER;
    }

    @Override
    public double getResistance() {
        return 0.1;
    }

    @Override
    public double getConductivity() {
        return 1.0;
    }

    @Override
    public double getInertia() {
        return 0.0;
    }

    @Override
    public long getEnergy() {
        return 0;
    }

    @Override
    public long getCapacity() {
        return 0;
    }

    @Override
    public long receive(long maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public long extract(long maxExtract, boolean simulate) {
        return 0;
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
        tag.putLong("NetworkId", networkId);
        tag.putString("CableTier", cableTier.name());
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        networkId = tag.getLong("NetworkId");
        if (tag.contains("CableTier")) {
            cableTier = VoltageTier.valueOf(tag.getString("CableTier"));
        }
    }
}
