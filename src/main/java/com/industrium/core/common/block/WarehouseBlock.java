package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Warehouse - large storage facility. */
public class WarehouseBlock extends Block {
    public WarehouseBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}