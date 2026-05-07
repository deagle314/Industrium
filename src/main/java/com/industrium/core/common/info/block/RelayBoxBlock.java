package com.industrium.core.common.info.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Relay Box - amplifies and repeats control signals.
 */
public class RelayBoxBlock extends Block {
    
    public RelayBoxBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL));
    }
}