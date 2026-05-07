package com.industrium.core.common.heat.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

/**
 * Thermal Battery - stores heat energy for later use.
 */
public class ThermalBatteryBlock extends Block {
    
    public ThermalBatteryBlock() {
        super(Block.Properties.of()
            .strength(3.0f, 15.0f)
            .sound(SoundType.METAL));
    }
}