package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** LV Battery Box - stores 10 FE. */
public class BatteryBoxBlock extends Block {
    public BatteryBoxBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}