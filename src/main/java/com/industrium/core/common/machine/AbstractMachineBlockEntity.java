package com.industrium.core.common.machine;

import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import com.industrium.core.common.machine.module.MachineModule;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for machines using a compositional module-based approach.
 */
public abstract class AbstractMachineBlockEntity extends BaseMachineBlockEntity {
    private final List<MachineModule> modules = new ArrayList<>();

    public AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected <T extends MachineModule> T addModule(T module) {
        modules.add(module);
        return module;
    }

    @Override
    public void tickServer() {
        super.tickServer();
        for (MachineModule module : modules) {
            module.tick();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundTag modulesTag = new CompoundTag();
        for (int i = 0; i < modules.size(); i++) {
            CompoundTag moduleTag = new CompoundTag();
            modules.get(i).save(moduleTag);
            modulesTag.put("Module" + i, moduleTag);
        }
        tag.put("Modules", modulesTag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Modules")) {
            CompoundTag modulesTag = tag.getCompound("Modules");
            for (int i = 0; i < modules.size(); i++) {
                if (modulesTag.contains("Module" + i)) {
                    modules.get(i).load(modulesTag.getCompound("Module" + i));
                }
            }
        }
    }
    
    public void onModuleUpdated() {
        for (MachineModule module : modules) {
            module.onMachineUpdate();
        }
        markClientSync();
    }
}
