package com.industrium.core.common.rotation.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Steam Engine - converts heat to rotation.
 */
public class SteamEngineBlock extends Block {
    
    public SteamEngineBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 15.0f)
            .sound(SoundType.METAL));
    }
}