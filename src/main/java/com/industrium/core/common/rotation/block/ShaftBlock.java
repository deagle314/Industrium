package com.industrium.core.common.rotation.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Steel Shaft - basic rotational transmission.
 * Transfers rotation between machines.
 */
public class ShaftBlock extends Block {
    
    public ShaftBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}