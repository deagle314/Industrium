package com.industrium.core.common.heat.blockentity;

import com.industrium.core.api.heat.IHeatContainer;
import com.industrium.core.api.network.IHeatNode;
import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

/**
 * Base block entity for heat-based machines.
 */
public abstract class BaseHeatMachineBlockEntity extends BaseMachineBlockEntity implements IHeatNode, IHeatContainer {
    protected double heat;
    protected double temperature = 20.0;
    protected UUID networkId;

    public BaseHeatMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public double getHeat() {
        return heat;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    @Override
    public void applyHeatDelta(double delta) {
        this.heat += delta;
        this.temperature = calculateTemperature(this.heat);
        markClientSync();
    }

    protected double calculateTemperature(double heat) {
        double capacity = getHeatCapacity();
        return (capacity > 0) ? (heat / capacity) : 20.0;
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
        return 1.0;
    }

    @Override
    public double getMaxHeat() {
        return getHeatCapacity() * 2000.0;
    }

    @Override
    public double getConductivity() {
        return getConductivityModifier();
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
    public net.minecraft.world.level.Level getLevel() {
        return level;
    }

    @Override
    public double receiveHeat(double heat, boolean simulate) {
        double canReceive = Math.min(heat, getMaxHeat() - this.heat);
        if (!simulate && canReceive > 0) {
            applyHeatDelta(canReceive);
        }
        return canReceive;
    }

    @Override
    public double extractHeat(double heat, boolean simulate) {
        double canExtract = Math.min(heat, this.heat);
        if (!simulate && canExtract > 0) {
            applyHeatDelta(-canExtract);
        }
        return canExtract;
    }

    @Override
    public BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public UUID getNetworkId() {
        return networkId;
    }

    @Override
    public void setNetworkId(UUID id) {
        this.networkId = id;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble("Heat", heat);
        if (networkId != null) {
            tag.putUUID("NetworkId", networkId);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.heat = tag.getDouble("Heat");
        this.temperature = calculateTemperature(this.heat);
        if (tag.hasUUID("NetworkId")) {
            this.networkId = tag.getUUID("NetworkId");
        }
    }
}
