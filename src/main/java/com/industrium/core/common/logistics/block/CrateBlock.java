package com.industrium.core.common.logistics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Crate - small manual storage.
 */
public class CrateBlock extends Block {
    
    public CrateBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.WOOD));
    }
}