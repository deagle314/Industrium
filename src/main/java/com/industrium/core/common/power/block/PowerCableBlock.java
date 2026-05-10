package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Power Cable - transfers energy between blocks. */
public class PowerCableBlock extends Block {
    public PowerCableBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 5.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
}
