package com.industrium.core.common.logistics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Fast Conveyor Belt - high speed item transport.
 * Steel-reinforced accelerated belt.
 */
public class FastConveyorBeltBlock extends Block {
    
    public FastConveyorBeltBlock() {
        super(Block.Properties.of()
            .strength(1.5f, 7.0f)
            .sound(SoundType.METAL));
    }
}