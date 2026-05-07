package com.industrium.core.common.heat.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Coal Burner - solid fuel heater.
 */
public class CoalBurnerBlock extends Block {
    
    public CoalBurnerBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}