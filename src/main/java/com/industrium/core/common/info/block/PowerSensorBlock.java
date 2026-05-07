package com.industrium.core.common.info.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Power Sensor - monitors FE/t consumption or storage level.
 */
public class PowerSensorBlock extends Block {
    
    public PowerSensorBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL));
    }
}