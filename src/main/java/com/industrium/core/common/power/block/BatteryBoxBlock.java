package com.industrium.core.common.power.block;

import com.industrium.core.common.power.blockentity.BatteryBoxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Battery Box - stores power at LV tier.
 * Basic energy storage.
 */
public class BatteryBoxBlock extends Block {
    
    public BatteryBoxBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
    
    
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BatteryBoxBlockEntity(pos, state);
    }
}