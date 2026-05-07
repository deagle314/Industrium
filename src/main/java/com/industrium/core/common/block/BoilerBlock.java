package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Boiler - converts heat to steam or processes fluids. */
public class BoilerBlock extends Block {
    public BoilerBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 10.0f)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops());
    }
}