package com.industrium.core.common.logistics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Belt Splitter - divides items to two outputs.
 */
public class SplitterBlock extends Block {
    
    public SplitterBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL));
    }
}