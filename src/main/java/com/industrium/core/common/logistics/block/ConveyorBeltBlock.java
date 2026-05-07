package com.industrium.core.common.logistics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Basic Conveyor Belt - primary item transport.
 * Standard speed rubber belt.
 */
public class ConveyorBeltBlock extends Block {
    
    public ConveyorBeltBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 5.0f)
            .sound(SoundType.METAL));
    }
}