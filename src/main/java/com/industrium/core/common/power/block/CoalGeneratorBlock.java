package com.industrium.core.common.power.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Coal Generator - produces LV power from fuel.
 * Basic power generation block.
 */
public class CoalGeneratorBlock extends Block {
    
    public CoalGeneratorBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}