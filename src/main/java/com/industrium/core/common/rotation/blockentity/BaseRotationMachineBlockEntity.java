package com.industrium.core.common.rotation.blockentity;

import com.industrium.core.api.network.IRotationNode;
import com.industrium.core.api.network.SystemType;
import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import com.industrium.core.Industrium;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseRotationMachineBlockEntity extends BaseMachineBlockEntity implements IRotationNode {
    protected double rpm = 0.0;
    protected double torque = 0.0;
    protected double conductivity = 1.0;
    protected long networkId = -1;

    public BaseRotationMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public SystemType getSystemType() {
        return SystemType.ROTATION;
    }

    @Override
    public double getRPM() {
        return rpm;
    }

    public void setRPM(double rpm) {
        this.rpm = rpm;
    }

    @Override
    public double getTorque() {
        return torque;
    }

    public void setTorque(double torque) {
        this.torque = torque;
    }

    @Override
    public double getConductivity() {
        return conductivity;
    }

    @Override
    public double getResistance() {
        return 0.0;
    }

    @Override
    public double getInertia() {
        return 1.0;
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
    }

    @Override
    public BlockPos getPos() {
        return worldPosition;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide) {
            Industrium.ROTATION_NETWORK_MANAGER.registerNode(this);
        }
    }

    @Override
    public void setRemoved() {
        if (level != null && !level.isClientSide) {
            Industrium.ROTATION_NETWORK_MANAGER.unregisterNode(this);
        }
        super.setRemoved();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble("RPM", rpm);
        tag.putDouble("Torque", torque);
        tag.putLong("NetworkId", networkId);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        rpm = tag.getDouble("RPM");
        torque = tag.getDouble("Torque");
        if (tag.contains("NetworkId")) {
            networkId = tag.getLong("NetworkId");
        }
    }
}
