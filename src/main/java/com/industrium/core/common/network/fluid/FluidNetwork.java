package com.industrium.core.common.network.fluid;

import com.industrium.core.api.network.IFluidNode;
import com.industrium.core.common.network.AbstractNetworkGraph;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

/**
 * Physics-based fluid simulation engine.
 * Updated to use long IDs and Level-aware constructor.
 */
public class FluidNetwork extends AbstractNetworkGraph<IFluidNode> {
    private static final double DAMPING_FACTOR = 0.5;
    private static final long AMBIENT_PRESSURE = 100; // kPa
    private static final double K_FACTOR = 1000.0;
    private static final double T_FACTOR = 0.01;

    private final Map<BlockPos, List<BlockPos>> adjacencyCache = new HashMap<>();
    private final Map<BlockPos, IFluidNode> nodeInstanceCache = new HashMap<>();

    public FluidNetwork(Level level, long id) {
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
            if (be instanceof IFluidNode fluidNode) {
                nodeInstanceCache.put(pos, fluidNode);
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

        // 1. Calculate pressures and check for failures
        for (BlockPos pos : nodes) {
            IFluidNode node = nodeInstanceCache.get(pos);
            if (node == null) continue;

            double v = node.getFluid().getAmount();
            double vMax = node.getCapacity();
            double t = node.getTemperature();

            // P = P_ambient + (V/V_max * (1 + T * T_factor) * k)
            long pressure = (long) (AMBIENT_PRESSURE + (v / vMax * (1.0 + Math.max(0, t) * T_FACTOR) * K_FACTOR));
            node.setPressure(pressure);

            // Failure states (rupture/explosion)
            if (pressure > 8000 || t > 1000) {
                level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 2.0f, Level.ExplosionInteraction.BLOCK);
                return; 
            }
        }

        // 2. Calculate flows
        Map<BlockPos, Long> deltas = new HashMap<>();
        for (BlockPos pos : nodes) deltas.put(pos, 0L);

        Set<Set<BlockPos>> processedPairs = new HashSet<>();
        for (BlockPos posA : nodes) {
            IFluidNode nodeA = nodeInstanceCache.get(posA);
            if (nodeA == null) continue;

            List<BlockPos> neighbors = adjacencyCache.get(posA);
            if (neighbors == null) continue;

            for (BlockPos posB : neighbors) {
                Set<BlockPos> pair = new HashSet<>(Arrays.asList(posA, posB));
                if (processedPairs.contains(pair)) continue;
                processedPairs.add(pair);

                IFluidNode nodeB = nodeInstanceCache.get(posB);
                if (nodeB == null) continue;

                long pA = nodeA.getPressure();
                long pB = nodeB.getPressure();

                if (pA == pB) continue;

                double conductivity = (nodeA.getDiameter() + nodeB.getDiameter()) / 2.0;
                double resistance = (nodeA.getViscosity() + nodeB.getViscosity()) / 2.0;
                if (resistance < 0.001) resistance = 0.001;

                double flow = (pA - pB) * conductivity / resistance;
                flow *= DAMPING_FACTOR;

                long roundedFlow = (long) flow;
                if (roundedFlow == 0) continue;

                if (roundedFlow > 0) { // A to B
                    FluidStack stackA = nodeA.getFluid();
                    if (stackA.isEmpty()) continue;
                    if (!nodeB.canHold(stackA)) continue;

                    roundedFlow = Math.min(roundedFlow, (long) stackA.getAmount());
                    roundedFlow = Math.min(roundedFlow, nodeB.getCapacity() - nodeB.getFluid().getAmount());

                    deltas.put(posA, deltas.get(posA) - roundedFlow);
                    deltas.put(posB, deltas.get(posB) + roundedFlow);
                } else { // B to A
                    long absFlow = -roundedFlow;
                    FluidStack stackB = nodeB.getFluid();
                    if (stackB.isEmpty()) continue;
                    if (!nodeA.canHold(stackB)) continue;

                    absFlow = Math.min(absFlow, (long) stackB.getAmount());
                    absFlow = Math.min(absFlow, nodeA.getCapacity() - nodeA.getFluid().getAmount());

                    deltas.put(posA, deltas.get(posA) + absFlow);
                    deltas.put(posB, deltas.get(posB) - absFlow);
                }
            }
        }

        // 3. Apply deltas (Volume Conservation)
        for (Map.Entry<BlockPos, Long> entry : deltas.entrySet()) {
            IFluidNode node = nodeInstanceCache.get(entry.getKey());
            if (node == null || entry.getValue() == 0) continue;

            if (entry.getValue() > 0) {
                FluidStack toFill = FluidStack.EMPTY;
                if (!node.getFluid().isEmpty()) {
                    toFill = node.getFluid().copy();
                } else {
                    for (Direction dir : Direction.values()) {
                        BlockPos neighborPos = entry.getKey().relative(dir);
                        if (nodes.contains(neighborPos)) {
                            IFluidNode neighbor = nodeInstanceCache.get(neighborPos);
                            if (neighbor != null && !neighbor.getFluid().isEmpty()) {
                                toFill = neighbor.getFluid().copy();
                                break;
                            }
                        }
                    }
                }
                
                if (!toFill.isEmpty()) {
                    toFill.setAmount(entry.getValue().intValue());
                    node.fill(toFill, false);
                }
            } else {
                node.drain(-entry.getValue(), false);
            }
        }
    }
}
