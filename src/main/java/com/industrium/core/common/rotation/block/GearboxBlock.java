package com.industrium.core.common.rotation.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Gearbox - changes RPM/PU ratio mechanically.
 */
public class GearboxBlock extends Block {
    
    public GearboxBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 15.0f)
            .sound(SoundType.METAL));
    }
}