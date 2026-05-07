package com.industrium.core.common.info.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Temperature Sensor - monitors heat system temperature.
 */
public class TemperatureSensorBlock extends Block {
    
    public TemperatureSensorBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL));
    }
}