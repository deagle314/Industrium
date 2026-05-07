package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Conveyor Belt - moves items. */
public class ConveyorBeltBlock extends Block {
    public ConveyorBeltBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 5.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}