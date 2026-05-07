package com.industrium.core.common.logistics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Loader - automated item input to belts.
 */
public class LoaderBlock extends Block {
    
    public LoaderBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}