package com.industrium.core.common.power.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import java.util.*;
import com.industrium.core.common.power.blockentity.BatteryBoxBlockEntity;
import com.industrium.core.common.power.block.*;
import com.industrium.core.api.power.*;

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
    private long cableLoss = 0;
    
    // Constants
    private static final double CABLE_LOSS_PERCENT = 0.01; // 1% loss per tick
    private static final int MAX_TRANSFER_BATCH = 1000; // Max FE per tick per connection
    
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
            if (block instanceof PowerCableBlock || block instanceof MVCableBlock) {
                // Cable - scan neighbors
                for (Direction dir : Direction.values()) {
                    BlockPos neighbor = current.relative(dir);
                    if (!visited.contains(neighbor) && isPowerConductor(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            } else if (block instanceof CoalGeneratorBlock) {
                producers.add(current);
            } else if (block instanceof BatteryBoxBlock) {
                storages.add(current);
            } else if (isPowerConsumer(neighborType(current))) {
                consumers.add(current);
            }
        }
        
        // Calculate totals
        calculateCapacity();
        isDirty = false;
    }
    
    /**
     * Calculates total network capacity.
     */
    private void calculateCapacity() {
        capacity = 0;
        storedEnergy = 0;
        
        for (BlockPos pos : storages) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof BatteryBoxBlockEntity battery) {
                capacity += battery.getMaxEnergy();
                storedEnergy += battery.getEnergy();
            }
        }
    }
    
    /**
     * Main tick - distributes power.
     */
    public void tick() {
        if (nodes.isEmpty()) return;
        
        // Step 1: Gather generation from producers
        gatherGeneration();
        
        // Step 2: Apply cable loss
        applyCableLoss();
        
        // Step 3: Distribute to storages first (fast charge)
        distributeToStorages();
        
        // Step 4: Distribute to consumers
        distributeToConsumers();
    }
    
    /**
     * Gathers power from all generators.
     */
    private void gatherGeneration() {
        totalGeneration = 0;
        
        for (BlockPos pos : producers) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof IGenerator generator && generator.isActive()) {
                long generated = generator.generate();
                totalGeneration += generated;
            }
        }
    }
    
    /**
     * Applies cable loss factor.
     */
    private void applyCableLoss() {
        if (totalGeneration > 0 && nodes.size() > 1) {
            long loss = (long)(totalGeneration * CABLE_LOSS_PERCENT * nodes.size());
            cableLoss = loss;
            totalGeneration -= loss;
        }
    }
    
    /**
     * Distributes energy to storage blocks first.
     */
    private void distributeToStorages() {
        if (totalGeneration <= 0) return;
        
        long remaining = totalGeneration;
        
        // Round-robin to storages
        for (BlockPos pos : storages) {
            if (remaining <= 0) break;
            
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof BatteryBoxBlockEntity battery) {
                long needed = battery.getMaxEnergy() - battery.getEnergy();
                if (needed > 0) {
                    long canReceive = Math.min(needed, remaining);
                    canReceive = Math.min(canReceive, battery.getTransferRate());
                    canReceive = Math.min(canReceive, MAX_TRANSFER_BATCH);
                    
                    if (canReceive > 0) {
                        battery.receiveEnergy(canReceive, false);
                        remaining -= canReceive;
                    }
                }
            }
        }
        
        // What's left goes to consumers
        totalConsumption = totalGeneration - remaining;
    }
    
    /**
     * Distributes remaining power to consumers.
     */
    private void distributeToConsumers() {
        long remaining = totalConsumption;
        
        for (BlockPos pos : consumers) {
            if (remaining <= 0) break;
            
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof IEnergyConsumer consumer) {
                // Skip batteries - they're handled in storages
                if (tile instanceof BatteryBoxBlockEntity) continue;
                
                long needed = consumer.getPowerDemand();
                if (needed > 0) {
                    long canProvide = Math.min(needed, remaining);
                    canProvide = Math.min(canProvide, consumer.getMaxPowerReceive());
                    canProvide = Math.min(canProvide, MAX_TRANSFER_BATCH);
                    
                    if (canProvide > 0) {
                        // Use battery's receiveEnergy to add power to consumer
                        if (tile instanceof BatteryBoxBlockEntity battery) {
                            long received = battery.receiveEnergy(canProvide, false);
                            remaining -= received;
                            consumer.onPowerReceived(received);
                        } else {
                            // Direct power to consumer that has power storage
                            consumer.onPowerReceived(canProvide);
                            remaining -= canProvide;
                        }
                    }
                }
            }
        }
        
        // Update stored energy stat
        calculateCapacity();
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
        if (tile == null) return false;
        // Machines that consume power
        return tile instanceof IEnergyConsumer;
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
     * Gets cable loss.
     */
    public long getCableLoss() {
        return cableLoss;
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