package com.industrium.core.common.rotation.blockentity;

import com.industrium.core.common.registry.RotationModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ShaftBlockEntity extends BaseRotationMachineBlockEntity {
    public ShaftBlockEntity(BlockPos pos, BlockState state) {
        super(RotationModule.SHAFT_BE.get(), pos, state);
    }

    @Override
    public double getInertia() {
        return 0.5;
    }

    @Override
    public double getFriction() {
        return 0.05;
    }

    @Override
    public double getMaxRPM() {
        return 1000.0;
    }

    @Override
    public double getMaxTorque() {
        return 500.0;
    }
}
