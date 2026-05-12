package com.industrium.core.common.rotation.blockentity;

import com.industrium.core.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GearboxBlockEntity extends BaseRotationMachineBlockEntity {
    public GearboxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEARBOX.get(), pos, state);
    }

    @Override
    public double getInertia() {
        return 1.0;
    }

    @Override
    public double getFriction() {
        return 0.1;
    }

    @Override
    public double getMaxRPM() {
        return 2000.0;
    }

    @Override
    public double getMaxTorque() {
        return 1000.0;
    }
    
    // Future: implement gear ratio transformations
}
