package com.industrium.core.common.info.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Central Console - multi-channel control hub and dashboard.
 */
public class CentralConsoleBlock extends Block {
    
    public CentralConsoleBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 15.0f)
            .sound(SoundType.METAL));
    }
}