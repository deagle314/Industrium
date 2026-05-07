package com.industrium.core.common.integration.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Electric Heater Block - power to heat conversion.
 */
public class ElectricHeaterBlock extends Block {
    
    public ElectricHeaterBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}