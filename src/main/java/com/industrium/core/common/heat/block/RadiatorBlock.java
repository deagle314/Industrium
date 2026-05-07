package com.industrium.core.common.heat.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Radiator - passive heat dissipation.
 */
public class RadiatorBlock extends Block {
    
    public RadiatorBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 5.0f)
            .sound(SoundType.METAL));
    }
}