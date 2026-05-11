package com.industrium.core.common.network.rotation;

import com.industrium.core.api.network.IRotationNode;
import com.industrium.core.common.network.AbstractNetworkGraph;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.*;

/**
 * Simulation engine for mechanical rotation.
 */
public class RotationNetwork extends AbstractNetworkGraph<IRotationNode> {
    private final Level level;
    private double currentRPM = 0;
    private final Map<BlockPos, IRotationNode> nodeInstanceCache = new HashMap<>();

    public RotationNetwork(Level level, UUID id) {
        super(id);
        this.level = level;
    }

    @Override
    public void addNode(BlockPos pos) {
        if (nodes.contains(pos)) return;
        
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof IRotationNode node) {
            double nodeInertia = node.getInertia();
            double nodeRPM = node.getRPM();
            double totalInertia = getTotalInertia();
            
            if (totalInertia + nodeInertia > 0) {
                currentRPM = (currentRPM * totalInertia + nodeRPM * nodeInertia) / (totalInertia + nodeInertia);
            }
        }
        
        super.addNode(pos);
        nodeInstanceCache.clear();
    }

    @Override
    public void removeNode(BlockPos pos) {
        super.removeNode(pos);
        nodeInstanceCache.clear();
    }

    private void updateCaches() {
        if (!nodeInstanceCache.isEmpty() || nodes.isEmpty()) return;

        for (BlockPos pos : nodes) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof IRotationNode rotationNode) {
                nodeInstanceCache.put(pos, rotationNode);
            }
        }
    }

    public double getTotalInertia() {
        double total = 0;
        updateCaches();
        for (IRotationNode node : nodeInstanceCache.values()) {
            total += node.getInertia();
        }
        return total;
    }

    @Override
    public void onMerge(AbstractNetworkGraph<IRotationNode> other) {
        if (other instanceof RotationNetwork otherRotation) {
            double thisInertia = getTotalInertia();
            double otherInertia = otherRotation.getTotalInertia();
            
            if (thisInertia + otherInertia > 0) {
                this.currentRPM = (this.currentRPM * thisInertia + otherRotation.currentRPM * otherInertia) / (thisInertia + otherInertia);
            }
        }
    }

    @Override
    public void tick() {
        if (nodes.isEmpty()) return;

        updateCaches();

        double totalInertia = 0;
        double totalFriction = 0;
        double totalInputTorque = 0;

        for (IRotationNode node : nodeInstanceCache.values()) {
            totalInertia += node.getInertia();
            totalFriction += node.getFriction();
            totalInputTorque += node.getTorque() * node.getEfficiency();
        }

        if (totalInertia <= 0) totalInertia = 0.1;

        // Physics Model: Net Torque = (Input Torque * Efficiency) - (Current RPM * Friction)
        double netTorque = totalInputTorque - (currentRPM * totalFriction);

        // alpha = net_torque / inertia
        double alpha = netTorque / totalInertia;

        // Euler integration
        currentRPM += alpha;
        if (currentRPM < 0) currentRPM = 0;

        List<BlockPos> toRemove = new ArrayList<>();
        for (Map.Entry<BlockPos, IRotationNode> entry : nodeInstanceCache.entrySet()) {
            IRotationNode node = entry.getValue();
            if (currentRPM > node.getMaxRPM()) {
                toRemove.add(entry.getKey());
            } else {
                node.setRPM(currentRPM);
            }
        }

        for (BlockPos pos : toRemove) {
            level.destroyBlock(pos, true);
        }
    }
}
