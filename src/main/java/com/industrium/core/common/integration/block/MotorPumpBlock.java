package com.industrium.core.common.integration.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Motor Pump Block - integrated power/rotation/fluid.
 */
public class MotorPumpBlock extends Block {
    
    public MotorPumpBlock() {
        super(Block.Properties.of()
            .strength(2.5f, 12.0f)
            .sound(SoundType.METAL));
    }
}