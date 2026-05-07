package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Generator - produces energy. */
public class GeneratorBlock extends Block {
    public GeneratorBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}