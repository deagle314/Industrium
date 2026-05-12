package com.industrium.core.common.power.block;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.InteractionResult;

import com.industrium.core.common.power.blockentity.BatteryBoxBlockEntity;

public class BatteryBoxBlock extends BaseEntityBlock {

    public BatteryBoxBlock() {
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
        return new BatteryBoxBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        Level level, BlockState state, BlockEntityType<T> type) {

        return (l, pos, st, te) -> {
            if (te instanceof BatteryBoxBlockEntity battery) {
                battery.tickServer();
            }
        };
    }

    @Override
    public InteractionResult use(
        BlockState state, Level level, BlockPos pos,
        Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide()) return InteractionResult.SUCCESS;

        BlockEntity tile = level.getBlockEntity(pos);
        if (!(tile instanceof BatteryBoxBlockEntity battery)) {
            return InteractionResult.PASS;
        }

        String status;

        if (player.isShiftKeyDown()) {
            String[] stats = battery.getStats();
            status = stats[0] + " | " + stats[1] + " | " + stats[2];
        } else {
            status = battery.getStatusText();
        }

        player.displayClientMessage(
            Component.literal("[Industrium] " + status),
            true
        );

        return InteractionResult.SUCCESS;
    }
}
