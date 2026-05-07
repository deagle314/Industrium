package com.industrium.core.common.fluid.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Steel Pipe - high pressure fluid transfer.
 * Industrial grade piping.
 */
public class SteelPipeBlock extends Block {
    
    public SteelPipeBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}