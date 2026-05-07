package com.industrium.core.common.rotation.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Flywheel - stores rotational momentum.
 */
public class FlywheelBlock extends Block {
    
    public FlywheelBlock() {
        super(Block.Properties.of()
            .strength(3.5f, 20.0f)
            .sound(SoundType.METAL));
    }
}