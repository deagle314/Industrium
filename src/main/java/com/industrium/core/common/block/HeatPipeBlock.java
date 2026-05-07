package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Heat Pipe - transfers heat between blocks. */
public class HeatPipeBlock extends Block {
    public HeatPipeBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 5.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}