package com.industrium.core.common.power.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * LV Power Cable - basic power transmission.
 * Connects machines and transfers power at LV tier.
 */
public class PowerCableBlock extends Block {
    
    public PowerCableBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 5.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}