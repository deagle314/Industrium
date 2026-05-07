package com.industrium.core.common.logistics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Belt Merger - combines two inputs to one output.
 */
public class MergerBlock extends Block {
    
    public MergerBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL));
    }
}