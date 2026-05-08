package com.industrium.core.common.power.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import java.util.*;
import com.industrium.core.common.power.block.*;

/**
 * Power network graph.
 * Manages cable connections, producer/consumer balancing, and energy distribution.
 */
public class PowerNetwork {
    
    private final Level level;
    private final Set<BlockPos> nodes = new HashSet<>();
    private final List<BlockPos> producers = new ArrayList<>();
    private final List<BlockPos> consumers = new ArrayList<>();
    private final List<BlockPos> storages = new ArrayList<>();
    private final String networkId;
    private int lastUpdateTick;
    private boolean isDirty = true;
    
    // Network stats
    private long totalGeneration;
    private long totalConsumption;
    private long storedEnergy;
    private long capacity;
    
    public PowerNetwork(Level level, String networkId) {
        this.level = level;
        this.networkId = networkId;
        this.lastUpdateTick = 0;
    }
    
    /**
     * Gets network ID.
     */
    public String getNetworkId() {
        return networkId;
    }
    
    /**
     * Adds a node to network.
     */
    public void addNode(BlockPos pos) {
        if (nodes.add(pos)) {
            isDirty = true;
        }
    }
    
    /**
     * Removes a node.
     */
    public void removeNode(BlockPos pos) {
        if (nodes.remove(pos)) {
            producers.remove(pos);
            consumers.remove(pos);
            storages.remove(pos);
            isDirty = true;
        }
    }
    
    /**
     * Rebuilds network by scanning from origin.
     */
    public void rebuild(BlockPos origin) {
        nodes.clear();
        producers.clear();
        consumers.clear();
        storages.clear();
        
        Queue<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();
        
        queue.add(origin);
        visited.add(origin);
        
        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            nodes.add(current);
            
            // Check block type and classify
            Block block = level.getBlockState(current).getBlock();
            if (block instanceof PowerCableBlock) {
                // Cable - scan neighbors
                for (Direction dir : Direction.values()) {
                    BlockPos neighbor = current.relative(dir);
                    if (!visited.contains(neighbor) && isPowerConductor(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            } else if (block instanceof com.industrium.core.common.power.block.CoalGeneratorBlock) {
                producers.add(current);
            } else if (block instanceof BatteryBoxBlock) {
                storages.add(current);
            } else if (isPowerConsumer(neighborType(current))) {
                consumers.add(current);
            }
        }
        
        isDirty = false;
    }
    
    /**
     * Checks if a position is a power conductor.
     */
    private boolean isPowerConductor(BlockPos pos) {
        if (!level.isLoaded(pos)) return false;
        Block block = level.getBlockState(pos).getBlock();
        return block instanceof PowerCableBlock || block instanceof MVCableBlock;
    }
    
    /**
     * Gets neighbor block entity type.
     */
    private BlockEntity getPowerTile(BlockPos pos) {
        return level.getBlockEntity(pos);
    }
    
    /**
     * Checks if block is a power consumer.
     */
    private boolean isPowerConsumer(BlockEntity tile) {
        return tile != null; // Simplified - all machines can accept power
    }
    
    private BlockEntity neighborType(BlockPos pos) {
        return level.getBlockEntity(pos);
    }
    
    /**
     * Gets node count.
     */
    public int getNodeCount() {
        return nodes.size();
    }
    
    /**
     * Gets producer count.
     */
    public int getProducerCount() {
        return producers.size();
    }
    
    /**
     * Gets consumer count.
     */
    public int getConsumerCount() {
        return consumers.size();
    }
    
    /**
     * Gets storage count.
     */
    public int getStorageCount() {
        return storages.size();
    }
    
    /**
     * Gets total generation.
     */
    public long getTotalGeneration() {
        return totalGeneration;
    }
    
    /**
     * Gets total consumption.
     */
    public long getTotalConsumption() {
        return totalConsumption;
    }
    
    /**
     * Gets stored energy.
     */
    public long getStoredEnergy() {
        return storedEnergy;
    }
    
    /**
     * Gets capacity.
     */
    public long getCapacity() {
        return capacity;
    }
    
    /**
     * Checks if dirty.
     */
    public boolean isDirty() {
        return isDirty;
    }
    
    /**
     * Marks clean.
     */
    public void markClean() {
        isDirty = false;
    }
    
    /**
     * Gets all node positions.
     */
    public Collection<BlockPos> getNodes() {
        return Collections.unmodifiableCollection(nodes);
    }
}