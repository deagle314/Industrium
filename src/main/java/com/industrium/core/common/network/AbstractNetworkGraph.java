package com.industrium.core.common.network;

import com.industrium.core.api.network.IIndustriumNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Base class for all network graphs.
 * Optimized for incremental updates and caching.
 */
public abstract class AbstractNetworkGraph<T extends IIndustriumNode> {
    protected final long id;
    protected final Level level;
    protected final Set<BlockPos> nodes = new HashSet<>();

    protected AbstractNetworkGraph(Level level, long id) {
        this.level = level;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Level getLevel() {
        return level;
    }

    public void addNode(BlockPos pos) {
        if (nodes.add(pos)) {
            onNodeAdded(pos);
        }
    }

    public void removeNode(BlockPos pos) {
        if (nodes.remove(pos)) {
            onNodeRemoved(pos);
        }
    }

    protected void onNodeAdded(BlockPos pos) {}
    protected void onNodeRemoved(BlockPos pos) {}

    public Set<BlockPos> getNodes() {
        return Collections.unmodifiableSet(nodes);
    }

    public void onMerge(AbstractNetworkGraph<T> other) {
        for (BlockPos pos : other.nodes) {
            addNode(pos);
        }
    }

    public abstract void tick();
}
