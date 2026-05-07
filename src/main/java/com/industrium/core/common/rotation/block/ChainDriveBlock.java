package com.industrium.core.common.rotation.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Chain Drive - higher torque transmission.
 */
public class ChainDriveBlock extends Block {
    
    public ChainDriveBlock() {
        super(Block.Properties.of()
            .strength(2.5f, 12.0f)
            .sound(SoundType.METAL));
    }
}