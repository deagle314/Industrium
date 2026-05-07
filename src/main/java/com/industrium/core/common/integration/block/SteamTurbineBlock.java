package com.industrium.core.common.integration.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Steam Turbine Block - integrated heat/fluid/rotation/power.
 */
public class SteamTurbineBlock extends Block {
    
    public SteamTurbineBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 15.0f)
            .sound(SoundType.METAL));
    }
}