package com.industrium.core.common.power.network;

import com.industrium.core.api.network.IPowerNode;
import com.industrium.core.api.power.IEnergyConsumer;
import com.industrium.core.api.power.IEnergyStorage;
import com.industrium.core.api.power.IGenerator;
import com.industrium.core.common.network.AbstractNetworkGraph;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * Optimized power network with caching.
 * Managed by the NetworkManager.
 */
public class PowerNetwork extends AbstractNetworkGraph<IPowerNode> {
    private final Set<BlockPos> producers = new HashSet<>();
    private final Set<BlockPos> consumers = new HashSet<>();
    private final Set<BlockPos> storages = new HashSet<>();
    
    private long totalGeneration;
    private long totalConsumption;
    private long storedEnergy;
    private long capacity;
    private long cableLoss;
    
    private boolean cacheDirty = true;
    
    private static final double CABLE_LOSS_PERCENT = 0.01;
    private static final int MAX_TRANSFER_BATCH = 10000;

    public PowerNetwork(Level level, long id) {
        super(level, id);
    }

    @Override
    protected void onNodeAdded(BlockPos pos) {
        cacheDirty = true;
    }

    @Override
    protected void onNodeRemoved(BlockPos pos) {
        cacheDirty = true;
    }

    private void updateCache() {
        if (!cacheDirty) return;
        
        producers.clear();
        consumers.clear();
        storages.clear();
        for (BlockPos nodePos : nodes) {
            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = nodePos.relative(dir);
                if (nodes.contains(neighborPos)) continue;

                BlockEntity be = level.getBlockEntity(neighborPos);
                if (be == null) continue;

                if (be instanceof IGenerator) producers.add(neighborPos);
                if (be instanceof IEnergyStorage) storages.add(neighborPos);
                if (be instanceof IEnergyConsumer && !(be instanceof IEnergyStorage)) consumers.add(neighborPos);
            }
        }
        cacheDirty = false;
    }

    @Override
    public void tick() {
        if (nodes.isEmpty()) return;
        
        updateCache();

        gatherGeneration();
        applyCableLoss();
        
        long remaining = totalGeneration;
        remaining = distributeToConsumers(remaining);
        remaining = distributeToStorages(remaining);
        
        totalConsumption = totalGeneration - remaining;
        calculateCapacity();
    }

    private void gatherGeneration() {
        totalGeneration = 0;
        for (BlockPos pos : producers) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof IGenerator generator && generator.isActive()) {
                totalGeneration += generator.generate();
            }
        }
    }

    private void applyCableLoss() {
        if (totalGeneration > 0 && nodes.size() > 1) {
            cableLoss = (long) (totalGeneration * CABLE_LOSS_PERCENT * nodes.size());
            totalGeneration = Math.max(0, totalGeneration - cableLoss);
        } else {
            cableLoss = 0;
        }
    }

    private long distributeToConsumers(long available) {
        long remaining = available;
        for (BlockPos pos : consumers) {
            if (remaining <= 0) break;
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof IEnergyConsumer consumer) {
                long needed = consumer.getPowerDemand();
                if (needed > 0) {
                    long canProvide = Math.min(needed, remaining);
                    canProvide = Math.min(canProvide, consumer.getMaxPowerReceive());
                    canProvide = Math.min(canProvide, MAX_TRANSFER_BATCH);
                    if (canProvide > 0) {
                        consumer.onPowerReceived(canProvide);
                        remaining -= canProvide;
                    }
                }
            }
        }
        return remaining;
    }

    private long distributeToStorages(long available) {
        long remaining = available;
        for (BlockPos pos : storages) {
            if (remaining <= 0) break;
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof IEnergyStorage storage) {
                long needed = storage.getMaxEnergy() - storage.getEnergy();
                if (needed > 0) {
                    long canReceive = Math.min(needed, remaining);
                    canReceive = Math.min(canReceive, storage.getTransferRate());
                    canReceive = Math.min(canReceive, MAX_TRANSFER_BATCH);
                    if (canReceive > 0) {
                        remaining -= storage.receiveEnergy(canReceive, false);
                    }
                }
            }
        }
        return remaining;
    }

    private void calculateCapacity() {
        capacity = 0;
        storedEnergy = 0;
        for (BlockPos pos : storages) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof IEnergyStorage storage) {
                capacity += storage.getMaxEnergy();
                storedEnergy += storage.getEnergy();
            }
        }
    }

    public long getTotalGeneration() { return totalGeneration; }
    public long getTotalConsumption() { return totalConsumption; }
    public long getStoredEnergy() { return storedEnergy; }
    public long getCapacity() { return capacity; }
    public long getCableLoss() { return cableLoss; }
    public int getNodeCount() { return nodes.size(); }
    
    public void markDirty() {
        this.cacheDirty = true;
    }
}
