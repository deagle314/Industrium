package com.industrium.core.common.network;

import com.industrium.core.api.network.IIndustriumNode;
import net.minecraft.core.BlockPos;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Base class for all network graphs.
 */
public abstract class AbstractNetworkGraph<T extends IIndustriumNode> {
    protected final UUID id;
    protected final Set<BlockPos> nodes = new HashSet<>();

    protected AbstractNetworkGraph(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void addNode(BlockPos pos) {
        nodes.add(pos);
    }

    public void removeNode(BlockPos pos) {
        nodes.remove(pos);
    }

    public Set<BlockPos> getNodes() {
        return Collections.unmodifiableSet(nodes);
    }

    public void onMerge(AbstractNetworkGraph<T> other) {
    }

    public void onSplit(Set<BlockPos> newComponentNodes) {
    }

    public abstract void tick();
}
