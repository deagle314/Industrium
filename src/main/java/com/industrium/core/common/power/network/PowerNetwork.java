package com.industrium.core.common.power.network;

import com.industrium.core.api.network.IPowerNode;
import com.industrium.core.common.network.AbstractNetworkGraph;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import com.industrium.core.api.power.*;

import java.util.*;

/**
 * Thin adapter for the power network.
 * Managed by the NetworkManager.
 */
public class PowerNetwork extends AbstractNetworkGraph<IPowerNode> {
    private final Level level;
    
    // Stats
    private long totalGeneration;
    private long totalConsumption;
    private long storedEnergy;
    private long capacity;
    private long cableLoss;
    
    private static final double CABLE_LOSS_PERCENT = 0.01;
    private static final int MAX_TRANSFER_BATCH = 1000;

    public PowerNetwork(Level level, UUID id) {
        super(id);
        this.level = level;
    }

    @Override
    public void tick() {
        if (nodes.isEmpty()) return;

        Set<BlockPos> producers = new HashSet<>();
        Set<BlockPos> consumers = new HashSet<>();
        Set<BlockPos> storages = new HashSet<>();

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

        gatherGeneration(producers);
        applyCableLoss();
        distributeToStorages(storages);
        distributeToConsumers(consumers);
        calculateCapacity(storages);
    }

    private void gatherGeneration(Set<BlockPos> producers) {
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
            totalGeneration -= cableLoss;
        } else {
            cableLoss = 0;
        }
    }

    private void distributeToStorages(Set<BlockPos> storages) {
        if (totalGeneration <= 0) return;
        long remaining = totalGeneration;

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
        totalConsumption = totalGeneration - remaining;
    }

    private void distributeToConsumers(Set<BlockPos> consumers) {
        long remaining = totalConsumption;
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
    }

    private void calculateCapacity(Set<BlockPos> storages) {
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
}
