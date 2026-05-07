package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Shaft - transfers rotational power vertically. */
public class ShaftBlock extends Block {
    public ShaftBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 5.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}