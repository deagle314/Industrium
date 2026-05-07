package com.industrium.core.common.rotation.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Electric Motor - converts power to rotation.
 */
public class ElectricMotorBlock extends Block {
    
    public ElectricMotorBlock() {
        super(Block.Properties.of()
            .strength(2.5f, 10.0f)
            .sound(SoundType.METAL));
    }
}