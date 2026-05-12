package com.industrium.core.common.rotation.blockentity;

import com.industrium.core.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FlywheelBlockEntity extends BaseRotationMachineBlockEntity {
    public FlywheelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLYWHEEL.get(), pos, state);
    }

    @Override
    public double getInertia() {
        return 20.0;
    }

    @Override
    public double getFriction() {
        return 0.02; // Flywheels are usually well-balanced
    }

    @Override
    public double getMaxRPM() {
        return 500.0;
    }

    @Override
    public double getMaxTorque() {
        return 2000.0;
    }
}
