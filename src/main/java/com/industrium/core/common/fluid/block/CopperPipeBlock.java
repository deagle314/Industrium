package com.industrium.core.common.fluid.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Copper Pipe - basic fluid transfer.
 * Good for water and low-temperature fluids.
 */
public class CopperPipeBlock extends Block {
    
    public CopperPipeBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}