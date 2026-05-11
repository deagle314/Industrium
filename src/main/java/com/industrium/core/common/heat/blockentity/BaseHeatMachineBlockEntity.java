package com.industrium.core.common.heat.blockentity;

import com.industrium.core.api.heat.IHeatContainer;
import com.industrium.core.api.network.IHeatNode;
import com.industrium.core.api.network.SystemType;
import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Base block entity for heat-based machines.
 * Implements both IHeatNode (for network participation) and IHeatContainer (for HU storage).
 */
public abstract class BaseHeatMachineBlockEntity extends BaseMachineBlockEntity implements IHeatNode, IHeatContainer {
    protected double heat = 0.0;
    protected long networkId = -1;

    public BaseHeatMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public SystemType getSystemType() {
        return SystemType.HEAT;
    }

    // IHeatContainer implementation
    @Override
    public double getHeat() {
        return heat;
    }

    @Override
    public double getTemperature() {
        double capacity = getHeatCapacity();
        return (capacity > 0) ? (heat / capacity) : 20.0;
    }

    @Override
    public double getMaxHeat() {
        return getHeatCapacity() * getMaxTemperature();
    }

    @Override
    public double getConductivity() {
        return getConductivityModifier();
    }

    @Override
    public double receiveHeat(double heatAmount, boolean simulate) {
        double canReceive = Math.min(heatAmount, getMaxHeat() - this.heat);
        if (!simulate && canReceive > 0) {
            applyHeatDelta(canReceive);
        }
        return canReceive;
    }

    @Override
    public double extractHeat(double heatAmount, boolean simulate) {
        double canExtract = Math.min(heatAmount, this.heat);
        if (!simulate && canExtract > 0) {
            applyHeatDelta(-canExtract);
        }
        return canExtract;
    }

    // IHeatNode implementation
    @Override
    public void applyHeatDelta(double delta) {
        this.heat += delta;
        this.heat = Math.max(0, Math.min(getMaxHeat(), this.heat));
        markClientSync();
    }

    @Override
    public double getHeatCapacity() {
        return 100.0;
    }

    @Override
    public double getConductivityModifier() {
        return 1.0;
    }

    @Override
    public double getHeatResistance() {
        return 0.5;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public long getNetworkId() {
        return networkId;
    }

    @Override
    public void setNetworkId(long id) {
        this.networkId = id;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide) {
            com.industrium.core.Industrium.HEAT_NETWORK_MANAGER.registerNode(this);
        }
    }

    @Override
    public void setRemoved() {
        if (level != null && !level.isClientSide) {
            com.industrium.core.Industrium.HEAT_NETWORK_MANAGER.unregisterNode(this);
        }
        super.setRemoved();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble("Heat", heat);
        tag.putLong("NetworkId", networkId);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.heat = tag.getDouble("Heat");
        if (tag.contains("NetworkId")) {
            this.networkId = tag.getLong("NetworkId");
        }
    }
}
