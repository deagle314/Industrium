package com.industrium.core.common.heat.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Steam Boiler - converts heat to steam for fluid system.
 */
public class SteamBoilerBlock extends Block {
    
    public SteamBoilerBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 15.0f)
            .sound(SoundType.METAL));
    }
}