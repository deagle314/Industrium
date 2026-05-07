package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Axle - transfers rotational power horizontally. */
public class AxleBlock extends Block {
    public AxleBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 5.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}