package com.industrium.core.common.fluid.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Condenser - converts steam to water.
 */
public class CondenserBlock extends Block {
    
    public CondenserBlock() {
        super(Block.Properties.of()
            .strength(2.5f, 12.0f)
            .sound(SoundType.METAL));
    }
}