package com.industrium.core.common.heat.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Insulated Heat Pipe - low loss heat transfer.
 */
public class InsulatedHeatPipeBlock extends Block {
    
    public InsulatedHeatPipeBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}