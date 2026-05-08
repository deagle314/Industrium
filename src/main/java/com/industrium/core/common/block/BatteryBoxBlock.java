package com.industrium.core.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import com.industrium.core.common.power.blockentity.BatteryBoxBlockEntity;

/** LV Battery Box - stores 10 FE. */
public class BatteryBoxBlock extends Block {
    public BatteryBoxBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }
    
    /**
     * Right-click interaction - send status to player.
     */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, 
            InteractionHand hand, BlockHitResult hit) {
        // Server-side only
        if (level.isClientSide()) return InteractionResult.PASS;
        
        BlockEntity tile = level.getBlockEntity(pos);
        if (!(tile instanceof BatteryBoxBlockEntity battery)) {
            return InteractionResult.PASS;
        }
        
        // Get status based on sneak
        String status;
        if (player.isShiftKeyDown()) {
            // Detailed stats
            String[] stats = battery.getStats();
            status = stats[0] + " | " + stats[1] + " | " + stats[2];
        } else {
            status = battery.getStatusText();
        }
        
        // Send status message to player
        Component msg = Component.literal("[Industrium] " + status);
        player.displayClientMessage(msg, true);
        
        return InteractionResult.SUCCESS;
    }
}