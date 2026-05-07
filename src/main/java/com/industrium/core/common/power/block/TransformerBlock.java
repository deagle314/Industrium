package com.industrium.core.common.power.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Transformer block - converts between LV and MV.
 */
public class TransformerBlock extends Block {
    
    public TransformerBlock() {
        super(Block.Properties.of()
            .strength(2.5f, 10.0f)
            .sound(SoundType.METAL));
    }
}