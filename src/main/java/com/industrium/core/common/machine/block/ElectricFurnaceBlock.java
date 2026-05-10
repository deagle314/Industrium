package com.industrium.core.common.machine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.Level;

/**
 * Electric Furnace - powered smelting machine.
 * Uses LV power to smelt items automatically.
 */
public class ElectricFurnaceBlock extends Block {

    public ElectricFurnaceBlock() {
        super(Block.Properties.of()
            .strength(2.0f, 10.0f)
            .sound(SoundType.METAL));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ElectricFurnaceBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (l, pos, st, te) -> {
            if (te instanceof ElectricFurnaceBlockEntity furnace) {
                furnace.tickServer();
            }
        };
    }
}
