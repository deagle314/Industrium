package com.industrium.core.common.logistics.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Unloader - automated item extraction from belts.
 */
public class UnloaderBlock extends Block {
    
    public UnloaderBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}