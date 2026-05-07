package com.industrium.core.common.info.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Threshold Controller - triggers at set values.
 */
public class ThresholdControllerBlock extends Block {
    
    public ThresholdControllerBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}