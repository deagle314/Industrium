package com.industrium.core.common.logistics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Vertical Chute - gravity-powered vertical item drop.
 */
public class VerticalChuteBlock extends Block {
    
    public VerticalChuteBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 5.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}