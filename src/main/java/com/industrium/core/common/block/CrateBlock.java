package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Crate - simple storage container. */
public class CrateBlock extends Block {
    public CrateBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 5.0f)
            .sound(SoundType.WOOD));
    }
}