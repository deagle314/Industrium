package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Fluid Pipe - transfers fluids. */
public class FluidPipeBlock extends Block {
    public FluidPipeBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 5.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}