package com.industrium.core.common.heat.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Copper Heat Pipe - high conductivity heat transfer.
 */
public class HeatPipeBlock extends Block {
    
    public HeatPipeBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}