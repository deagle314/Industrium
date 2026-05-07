package com.industrium.core.common.rotation.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Belt Pulley - flexible power transfer over distance.
 */
public class BeltPulleyBlock extends Block {
    
    public BeltPulleyBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 6.0f)
            .sound(SoundType.METAL));
    }
}