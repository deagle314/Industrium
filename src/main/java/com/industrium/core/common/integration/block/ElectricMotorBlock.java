package com.industrium.core.common.integration.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Electric Motor Block - converts FE to rotation.
 */
public class ElectricMotorBlock extends Block {
    
    public ElectricMotorBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}