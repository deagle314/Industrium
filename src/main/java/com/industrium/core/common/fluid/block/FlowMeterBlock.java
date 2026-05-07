package com.industrium.core.common.fluid.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Flow Meter - displays fluid flow rate.
 */
public class FlowMeterBlock extends Block {
    
    public FlowMeterBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 5.0f)
            .sound(SoundType.METAL));
    }
}