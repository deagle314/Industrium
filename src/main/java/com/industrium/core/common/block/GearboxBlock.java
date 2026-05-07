package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/** Gearbox - changes rotational speed/torque. */
public class GearboxBlock extends Block {
    public GearboxBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 5.0f)
            .sound(SoundType.METAL));
    }
}