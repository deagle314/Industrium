package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Basic Tank - stores fluids (gases and liquids). */
public class BasicTankBlock extends Block {
    public BasicTankBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 10.0f)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops());
    }
}