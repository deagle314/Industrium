package com.industrium.core.common.power.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * MV Power Cable - medium voltage transmission.
 * Higher capacity than LV cable.
 */
public class MVCableBlock extends Block {
    
    public MVCableBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}