package com.industrium.core.api.network;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.UUID;

/**
 * Common interface for all network nodes.
 */
public interface IIndustriumNode {
    BlockPos getPos();
    Level getLevel();
    UUID getNetworkId();
    void setNetworkId(UUID id);
}
