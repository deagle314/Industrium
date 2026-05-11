package com.industrium.core.common.network;

import com.industrium.core.api.network.IIndustriumNode;
import com.industrium.core.api.network.IFluidNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Sole authority for graph topology. Handles node registration, unregistration,
 * merging, and splitting using BFS-based adjacency logic.
 * Updated for long IDs and optimized localized BFS.
 */
public class NetworkManager<T extends IIndustriumNode, G extends AbstractNetworkGraph<T>> {
    private final Map<Long, G> networks = new HashMap<>();
    private final Map<BlockPos, Long> posToNetworkId = new HashMap<>();
    private final BiFunction<Level, Long, G> graphFactory;
    private long nextId = 1;

    public NetworkManager(BiFunction<Level, Long, G> graphFactory) {
        this.graphFactory = graphFactory;
    }

    private boolean isCompatible(T node, G graph) {
        if (node instanceof IFluidNode fluidNode) {
            FluidStack nodeFluid = fluidNode.getFluid();
            if (nodeFluid.isEmpty()) return true;

            for (BlockPos pos : graph.getNodes()) {
                BlockEntity be = node.getLevel().getBlockEntity(pos);
                if (be instanceof IFluidNode graphNode) {
                    FluidStack graphFluid = graphNode.getFluid();
                    if (!graphFluid.isEmpty() && !graphFluid.isFluidEqual(nodeFluid)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void registerNode(T node) {
        Level level = node.getLevel();
        BlockPos pos = node.getPos();
        
        if (posToNetworkId.containsKey(pos)) return;

        Set<Long> neighboringNetworks = new HashSet<>();
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            Long networkId = posToNetworkId.get(neighborPos);
            if (networkId != null && networkId != -1) {
                G neighboringGraph = networks.get(networkId);
                if (neighboringGraph != null && isCompatible(node, neighboringGraph)) {
                    neighboringNetworks.add(networkId);
                }
            }
        }

        if (neighboringNetworks.isEmpty()) {
            long newId = nextId++;
            G graph = graphFactory.apply(level, newId);
            graph.addNode(pos);
            networks.put(newId, graph);
            posToNetworkId.put(pos, newId);
            node.onNetworkJoin(newId);
        } else if (neighboringNetworks.size() == 1) {
            long networkId = neighboringNetworks.iterator().next();
            G graph = networks.get(networkId);
            graph.addNode(pos);
            posToNetworkId.put(pos, networkId);
            node.onNetworkJoin(networkId);
        } else {
            // Merge
            Iterator<Long> it = neighboringNetworks.iterator();
            long primaryId = it.next();
            G primaryGraph = networks.get(primaryId);
            primaryGraph.addNode(pos);
            posToNetworkId.put(pos, primaryId);
            node.onNetworkJoin(primaryId);

            while (it.hasNext()) {
                long otherId = it.next();
                G otherGraph = networks.remove(otherId);
                primaryGraph.onMerge(otherGraph);
                for (BlockPos otherPos : otherGraph.getNodes()) {
                    posToNetworkId.put(otherPos, primaryId);
                    BlockEntity be = level.getBlockEntity(otherPos);
                    if (be instanceof IIndustriumNode industriumNode) {
                        industriumNode.onNetworkJoin(primaryId);
                    }
                }
            }
        }
    }

    public void unregisterNode(T node) {
        BlockPos pos = node.getPos();
        Long networkId = posToNetworkId.remove(pos);
        if (networkId == null || networkId == -1) return;

        G graph = networks.get(networkId);
        if (graph == null) return;
        graph.removeNode(pos);
        node.onNetworkLeave(networkId);

        if (graph.getNodes().isEmpty()) {
            networks.remove(networkId);
            return;
        }

        // Localized BFS for split check
        List<BlockPos> neighbors = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            Long neighborNetworkId = posToNetworkId.get(neighborPos);
            if (neighborNetworkId != null && neighborNetworkId.equals(networkId)) {
                neighbors.add(neighborPos);
            }
        }

        if (neighbors.size() <= 1) return;

        // More than one neighbor, potential split
        rebuildNetwork(node.getLevel(), networkId, graph);
    }

    private void rebuildNetwork(Level level, long oldId, G graph) {
        Set<BlockPos> remainingNodes = new HashSet<>(graph.getNodes());
        networks.remove(oldId);
        // Clear old network IDs for nodes in this graph
        for (BlockPos pos : remainingNodes) {
            posToNetworkId.remove(pos);
        }

        while (!remainingNodes.isEmpty()) {
            BlockPos start = remainingNodes.iterator().next();
            Set<BlockPos> component = new HashSet<>();
            Queue<BlockPos> queue = new LinkedList<>();
            
            queue.add(start);
            component.add(start);
            remainingNodes.remove(start);

            while (!queue.isEmpty()) {
                BlockPos current = queue.poll();
                for (Direction dir : Direction.values()) {
                    BlockPos next = current.relative(dir);
                    if (remainingNodes.contains(next)) {
                        remainingNodes.remove(next);
                        component.add(next);
                        queue.add(next);
                    }
                }
            }

            long newId = nextId++;
            G newGraph = graphFactory.apply(level, newId);
            for (BlockPos compPos : component) {
                newGraph.addNode(compPos);
                posToNetworkId.put(compPos, newId);
                BlockEntity be = level.getBlockEntity(compPos);
                if (be instanceof IIndustriumNode industriumNode) {
                    industriumNode.onNetworkJoin(newId);
                }
            }
            networks.put(newId, newGraph);
        }
    }

    public G getNetwork(long id) {
        return networks.get(id);
    }

    public void tick() {
        for (G graph : new ArrayList<>(networks.values())) {
            graph.tick();
        }
    }
}
