package com.industrium.core.common.power.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;

/**
 * LV Power Cable - basic power transmission.
 * Connects machines and transfers power at LV tier.
 */
public class PowerCableBlock extends Block {
    
    public PowerCableBlock() {
        super(Block.Properties.of()
            .strength(1.0f, 5.0f)
            .sound(SoundType.METAL)
            .noOcclusion());
    }
    
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (l, pos, st, te) -> {
            if (te instanceof CableBlockEntity cable) {
                cable.tickServer();
            }
        };
    }
}