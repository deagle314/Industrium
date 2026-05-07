package com.industrium.core.common.heat.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Electric Heater - converts power to heat.
 * Basic heating block for the heat system.
 */
public class ElectricHeaterBlock extends Block {
    
    public ElectricHeaterBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}