package com.industrium.core.common.info.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Indicator Lamp - visual status display light.
 */
public class IndicatorLampBlock extends Block {
    
    public IndicatorLampBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 3.0f)
            .sound(SoundType.METAL)
            .lightLevel((state) -> 7));
    }
}