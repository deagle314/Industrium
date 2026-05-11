package com.industrium.core.common.rotation.block;

import com.industrium.core.common.rotation.blockentity.ShaftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Steel Shaft - basic rotational transmission.
 * Transfers rotation between machines.
 */
public class ShaftBlock extends BaseEntityBlock {
    
    public ShaftBlock() {
        super(Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShaftBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return (l, p, s, be) -> {
            if (be instanceof ShaftBlockEntity shaft) {
                shaft.tickServer();
            }
        };
    }
}
