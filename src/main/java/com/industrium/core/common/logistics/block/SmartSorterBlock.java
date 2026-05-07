package com.industrium.core.common.logistics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Smart Sorter - filters items to destinations.
 */
public class SmartSorterBlock extends Block {
    
    public SmartSorterBlock() {
        super(Block.Properties.of()
            .strength(2.5f, 12.0f)
            .sound(SoundType.METAL));
    }
}