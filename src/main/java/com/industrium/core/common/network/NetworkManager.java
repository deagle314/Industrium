package com.industrium.core.common.network;

import com.industrium.core.api.network.IIndustriumNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Sole authority for graph topology. Handles node registration, unregistration,
 * merging, and splitting using BFS-based adjacency logic.
 */
public class NetworkManager<T extends IIndustriumNode, G extends AbstractNetworkGraph<T>> {
    private final Map<UUID, G> networks = new HashMap<>();
    private final Map<BlockPos, UUID> posToNetworkId = new HashMap<>();
    private final BiFunction<Level, UUID, G> graphFactory;

    public NetworkManager(BiFunction<Level, UUID, G> graphFactory) {
        this.graphFactory = graphFactory;
    }

    public void registerNode(T node) {
        Level level = node.getLevel();
        BlockPos pos = node.getPos();
        
        if (posToNetworkId.containsKey(pos)) return;

        Set<UUID> neighboringNetworks = new HashSet<>();
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            UUID networkId = posToNetworkId.get(neighborPos);
            if (networkId != null) {
                neighboringNetworks.add(networkId);
            }
        }

        if (neighboringNetworks.isEmpty()) {
            UUID newId = UUID.randomUUID();
            G graph = graphFactory.apply(level, newId);
            graph.addNode(pos);
            networks.put(newId, graph);
            posToNetworkId.put(pos, newId);
            node.setNetworkId(newId);
        } else if (neighboringNetworks.size() == 1) {
            UUID networkId = neighboringNetworks.iterator().next();
            G graph = networks.get(networkId);
            graph.addNode(pos);
            posToNetworkId.put(pos, networkId);
            node.setNetworkId(networkId);
        } else {
            // Merge
            Iterator<UUID> it = neighboringNetworks.iterator();
            UUID primaryId = it.next();
            G primaryGraph = networks.get(primaryId);
            primaryGraph.addNode(pos);
            posToNetworkId.put(pos, primaryId);
            node.setNetworkId(primaryId);

            while (it.hasNext()) {
                UUID otherId = it.next();
                G otherGraph = networks.remove(otherId);
                for (BlockPos otherPos : otherGraph.getNodes()) {
                    primaryGraph.addNode(otherPos);
                    posToNetworkId.put(otherPos, primaryId);
                    BlockEntity be = level.getBlockEntity(otherPos);
                    if (be instanceof IIndustriumNode) {
                        ((IIndustriumNode) be).setNetworkId(primaryId);
                    }
                }
            }
        }
    }

    public void unregisterNode(T node) {
        BlockPos pos = node.getPos();
        UUID networkId = posToNetworkId.remove(pos);
        if (networkId == null) return;

        G graph = networks.get(networkId);
        graph.removeNode(pos);
        node.setNetworkId(null);

        if (graph.getNodes().isEmpty()) {
            networks.remove(networkId);
            return;
        }

        // Split check
        Level level = node.getLevel();
        List<BlockPos> neighbors = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            if (posToNetworkId.get(neighborPos) == networkId) {
                neighbors.add(neighborPos);
            }
        }

        if (neighbors.size() <= 1) return;

        // BFS to find components
        Set<BlockPos> remainingNodes = new HashSet<>(graph.getNodes());
        networks.remove(networkId);

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

            UUID newId = (component.size() == graph.getNodes().size() - 1) ? networkId : UUID.randomUUID();
            // Actually always newId is safer or we reuse the old one for one of the components.
            // Let's just use a new one for simplicity and consistency.
            UUID actualId = UUID.randomUUID();
            G newGraph = graphFactory.apply(level, actualId);
            for (BlockPos compPos : component) {
                newGraph.addNode(compPos);
                posToNetworkId.put(compPos, actualId);
                BlockEntity be = level.getBlockEntity(compPos);
                if (be instanceof IIndustriumNode) {
                    ((IIndustriumNode) be).setNetworkId(actualId);
                }
            }
            networks.put(actualId, newGraph);
        }
    }

    public G getNetwork(UUID id) {
        return networks.get(id);
    }

    public void tick() {
        for (G graph : networks.values()) {
            graph.tick();
        }
    }
}
