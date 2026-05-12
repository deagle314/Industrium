package com.industrium.core.common.network.heat;

import com.industrium.core.api.network.IHeatNode;
import com.industrium.core.common.network.AbstractNetworkGraph;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.*;

/**
 * Physics-based thermal simulation engine using a discrete thermal diffusion model.
 * Updated to use long IDs and Level-aware constructor.
 */
public class HeatNetwork extends AbstractNetworkGraph<IHeatNode> {
    private static final double DAMPING_FACTOR = 0.2;
    private static final double AMBIENT_TRANSFER_COEFFICIENT = 0.05;
    
    private final Map<BlockPos, List<BlockPos>> adjacencyCache = new HashMap<>();
    private final Map<BlockPos, IHeatNode> nodeInstanceCache = new HashMap<>();

    public HeatNetwork(Level level, long id) {
        super(level, id);
    }

    @Override
    protected void onNodeAdded(BlockPos pos) {
        clearCaches();
    }

    @Override
    protected void onNodeRemoved(BlockPos pos) {
        clearCaches();
    }

    private void clearCaches() {
        adjacencyCache.clear();
        nodeInstanceCache.clear();
    }

    private void updateCaches() {
        if (!adjacencyCache.isEmpty()) return;

        for (BlockPos pos : nodes) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof IHeatNode heatNode) {
                nodeInstanceCache.put(pos, heatNode);
                List<BlockPos> neighbors = new ArrayList<>();
                for (Direction dir : Direction.values()) {
                    BlockPos neighborPos = pos.relative(dir);
                    if (nodes.contains(neighborPos)) {
                        neighbors.add(neighborPos);
                    }
                }
                adjacencyCache.put(pos, neighbors);
            }
        }
    }

    @Override
    public void tick() {
        if (nodes.isEmpty()) return;

        updateCaches();

        Map<BlockPos, Double> deltas = new HashMap<>();
        for (BlockPos pos : nodes) {
            deltas.put(pos, 0.0);
        }

        // 1. Internal Diffusion
        Set<Set<BlockPos>> processedPairs = new HashSet<>();
        for (BlockPos posA : nodes) {
            IHeatNode nodeA = nodeInstanceCache.get(posA);
            if (nodeA == null) continue;

            List<BlockPos> neighbors = adjacencyCache.get(posA);
            if (neighbors == null) continue;

            for (BlockPos posB : neighbors) {
                Set<BlockPos> pair = new HashSet<>(Arrays.asList(posA, posB));
                if (processedPairs.contains(pair)) continue;
                processedPairs.add(pair);

                IHeatNode nodeB = nodeInstanceCache.get(posB);
                if (nodeB == null) continue;

                double tempA = nodeA.getTemperature();
                double tempB = nodeB.getTemperature();
                
                if (Math.abs(tempA - tempB) < 0.01) continue;

                // ΔQ = (T_A - T_B) * k_eff * transferFactor / R_eff
                double k_eff = (nodeA.getConductivityModifier() + nodeB.getConductivityModifier()) / 2.0;
                double r_eff = (nodeA.getHeatResistance() + nodeB.getHeatResistance()) / 2.0;
                if (r_eff < 0.001) r_eff = 0.001; // Prevent division by zero

                double flux = (tempA - tempB) * k_eff / r_eff;
                flux *= DAMPING_FACTOR;

                double capA = nodeA.getHeatCapacity();
                double capB = nodeB.getHeatCapacity();
                double targetTemp = (tempA * capA + tempB * capB) / (capA + capB);
                double maxFlux = (tempA - targetTemp) * capA;
                
                if (flux > 0) {
                    flux = Math.min(flux, maxFlux);
                } else {
                    flux = Math.max(flux, maxFlux);
                }

                deltas.put(posA, deltas.get(posA) - flux);
                deltas.put(posB, deltas.get(posB) + flux);
            }

            // 2. Ambient Exchange (Newton's Law of Cooling)
            // Use the modern Forge 1.20.1 biome temperature API
            double ambientTemp = level.getBiome(posA).get().getBaseTemperature() * 20.0;
            double h = AMBIENT_TRANSFER_COEFFICIENT * nodeA.getConductivityModifier();
            double ambientFlux = h * (ambientTemp - nodeA.getTemperature());
            
            double maxAmbientFlux = (ambientTemp - nodeA.getTemperature()) * nodeA.getHeatCapacity() * DAMPING_FACTOR;
            if (ambientFlux > 0) {
                ambientFlux = Math.min(ambientFlux, maxAmbientFlux);
            } else {
                ambientFlux = Math.max(ambientFlux, maxAmbientFlux);
            }
            
            deltas.put(posA, deltas.get(posA) + ambientFlux);
        }

        // 3. Apply deltas
        for (Map.Entry<BlockPos, Double> entry : deltas.entrySet()) {
            IHeatNode node = nodeInstanceCache.get(entry.getKey());
            if (node != null && Math.abs(entry.getValue()) > 1e-6) {
                node.applyHeatDelta(entry.getValue());
            }
        }
    }
}
