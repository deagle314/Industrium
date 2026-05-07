package com.industrium.core.common.fluid.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Mechanical Pump - rotation powered fluid pump.
 */
public class MechanicalPumpBlock extends Block {
    
    public MechanicalPumpBlock() {
        super(Block.Properties.of()
            .strength(2.5f, 10.0f)
            .sound(SoundType.METAL));
    }
}