package com.industrium.core.common.power.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Battery Box - stores power at LV tier.
 * Basic energy storage.
 */
public class BatteryBoxBlock extends Block {
    
    public BatteryBoxBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}