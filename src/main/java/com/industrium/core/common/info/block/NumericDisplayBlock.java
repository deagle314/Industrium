package com.industrium.core.common.info.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Numeric Display - shows numeric values.
 */
public class NumericDisplayBlock extends Block {
    
    public NumericDisplayBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 5.0f)
            .sound(SoundType.METAL));
    }
}