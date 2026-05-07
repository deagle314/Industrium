package com.industrium.core.common.fluid.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Fluid Tank - stores liquids and gases.
 * Standard storage vessel.
 */
public class FluidTankBlock extends Block {
    
    public FluidTankBlock() {
        super(Block.Properties.of()
            .strength(2.5f, 12.0f)
            .sound(SoundType.METAL));
    }
}