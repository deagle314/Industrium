package com.industrium.core.common.rotation.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Crusher - rotational machine for processing.
 */
public class CrusherBlock extends Block {
    
    public CrusherBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 15.0f)
            .sound(SoundType.METAL));
    }
}