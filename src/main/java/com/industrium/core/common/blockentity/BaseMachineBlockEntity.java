package com.industrium.core.common.blockentity;

import com.industrium.core.common.system.MachineStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Base abstract block entity for all Industrium machines.
 * Provides tick handling, save/load, and sync.
 */
public abstract class BaseMachineBlockEntity extends BlockEntity {
    
    protected MachineStatus status = MachineStatus.OFFLINE;
    protected boolean needsClientSync;
    protected int tickCounter;
    protected BlockPos lastKnownPos;
    
    public BaseMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.lastKnownPos = pos;
    }
    
    /**
     * Called each server tick (20 ticks/sec).
     */
    public void tickServer() {
        tickCounter++;
        if (needsClientSync && level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            needsClientSync = false;
        }
    }
    
    /**
     * Called each client tick.
     */
    public void tickClient() {
    }
    
    /**
     * Gets current status.
     */
    public MachineStatus getStatus() {
        return status;
    }
    
    /**
     * Sets status and marks for sync.
     */
    protected void setStatus(MachineStatus newStatus) {
        if (this.status != newStatus) {
            this.status = newStatus;
            markClientSync();
        }
    }
    
    /**
     * Marks that client needs sync.
     */
    protected void markClientSync() {
        this.needsClientSync = true;
    }
    
    /**
     * Creates sync packet.
     */
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        loadClientData(pkt.getTag());
    }
    
    /**
     * Gets update tag for initial sync.
     */
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveClientData(tag);
        return tag;
    }
    
    /**
     * Handles incoming sync packet.
     */
    @Override
    public void handleUpdateTag(CompoundTag tag) {
        loadClientData(tag);
    }
    
    /**
     * Saves NBT data.
     */
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Status", status.name());
        tag.putInt("TickCounter", tickCounter);
    }
    
    /**
     * Loads NBT data.
     */
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        status = MachineStatus.valueOf(tag.getString("Status"));
        tickCounter = tag.getInt("TickCounter");
    }
    
    /**
     * Saves client-specific sync data.
     */
    protected void saveClientData(CompoundTag tag) {
        tag.putString("Status", status.name());
    }
    
    /**
     * Loads client-specific sync data.
     */
    protected void loadClientData(CompoundTag tag) {
        if (tag.contains("Status")) {
            status = MachineStatus.valueOf(tag.getString("Status"));
        }
    }
    
    /**
     * Gets the level, safely.
     */
    public Level getMachineLevel() {
        return this.level;
    }
    
    /**
     * Checks if tile is valid.
     */
    public boolean isValid() {
        return level != null && !level.isClientSide();
    }
}