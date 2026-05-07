package com.industrium.core.common.rotation.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Clutch - engage/disengage mechanical transmission.
 */
public class ClutchBlock extends Block {
    
    public ClutchBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}