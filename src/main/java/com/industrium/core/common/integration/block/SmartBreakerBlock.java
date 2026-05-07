package com.industrium.core.common.integration.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Smart Breaker Block - automated switching.
 */
public class SmartBreakerBlock extends Block {
    
    public SmartBreakerBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}