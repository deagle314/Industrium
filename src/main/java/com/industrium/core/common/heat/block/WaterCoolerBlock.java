package com.industrium.core.common.heat.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Water Cooler - actively removes heat using water.
 */
public class WaterCoolerBlock extends Block {
    
    public WaterCoolerBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}