package com.industrium.core.common.heat.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Heat Meter - displays temperature and flow.
 */
public class HeatMeterBlock extends Block {
    
    public HeatMeterBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 5.0f)
            .sound(SoundType.METAL));
    }
}