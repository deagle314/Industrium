package com.industrium.core.common.fluid.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Boiler - converts water to steam using heat.
 */
public class BoilerBlock extends Block {
    
    public BoilerBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 15.0f)
            .sound(SoundType.METAL));
    }
}