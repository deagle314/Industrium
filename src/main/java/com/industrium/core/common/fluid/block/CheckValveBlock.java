package com.industrium.core.common.fluid.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Check Valve - one-way fluid flow.
 */
public class CheckValveBlock extends Block {
    
    public CheckValveBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL));
    }
}