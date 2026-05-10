package com.industrium.core.common.power.block;

import com.industrium.core.common.power.blockentity.CoalGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionResult;

/**
 * Coal Generator - produces LV power from fuel.
 * Basic power generation block.
 */
public class CoalGeneratorBlock extends Block {
    
    public CoalGeneratorBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
    
    
    @Override
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CoalGeneratorBlockEntity(pos, state);
    }

    @Override
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (l, pos, st, te) -> {
            if (te instanceof CoalGeneratorBlockEntity gen) {
                gen.tickServer();
            }
        };
    }
    
    /**
     * Right-click interaction - show generator status.
     */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hit) {
        // Server-side only
        if (level.isClientSide()) return InteractionResult.PASS;
        
        BlockEntity tile = level.getBlockEntity(pos);
        if (!(tile instanceof CoalGeneratorBlockEntity gen)) {
            return InteractionResult.PASS;
        }
        
        // Status available via interaction
        return InteractionResult.SUCCESS;
    }
}