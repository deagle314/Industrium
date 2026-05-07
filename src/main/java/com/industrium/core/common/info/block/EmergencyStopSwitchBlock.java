package com.industrium.core.common.info.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Emergency Stop Switch - instant shutdown for entire sections.
 */
public class EmergencyStopSwitchBlock extends Block {
    
    public EmergencyStopSwitchBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
}