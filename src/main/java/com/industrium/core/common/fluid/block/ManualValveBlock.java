package com.industrium.core.common.fluid.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Manual Valve - controls fluid flow manually.
 */
public class ManualValveBlock extends Block {
    
    public ManualValveBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL));
    }
}