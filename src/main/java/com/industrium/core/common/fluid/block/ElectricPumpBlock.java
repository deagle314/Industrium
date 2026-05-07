package com.industrium.core.common.fluid.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Electric Pump - power powered fluid pump.
 */
public class ElectricPumpBlock extends Block {
    
    public ElectricPumpBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}