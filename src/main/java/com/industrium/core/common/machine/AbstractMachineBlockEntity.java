package com.industrium.core.common.machine;

import com.industrium.core.common.blockentity.BaseMachineBlockEntity;
import com.industrium.core.common.machine.module.MachineModule;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Base class for machines using a compositional module-based approach.
 * Stabilized for consistent NBT handling and performance.
 */
public abstract class AbstractMachineBlockEntity extends BaseMachineBlockEntity {
    private final Map<String, MachineModule> modules = new LinkedHashMap<>();

    public AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected <T extends MachineModule> T addModule(String name, T module) {
        modules.put(name, module);
        return module;
    }

    @Override
    public void tickServer() {
        // BaseMachineBlockEntity.tickServer() currently marks sync every tick, 
        // we should probably avoid that or override it.
        this.tickCounter++;
        
        for (MachineModule module : modules.values()) {
            module.tick();
        }
        
        if (this.needsClientSync) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            this.needsClientSync = false;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundTag modulesTag = new CompoundTag();
        for (Map.Entry<String, MachineModule> entry : modules.entrySet()) {
            CompoundTag moduleTag = new CompoundTag();
            entry.getValue().save(moduleTag);
            modulesTag.put(entry.getKey(), moduleTag);
        }
        tag.put("Modules", modulesTag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Modules")) {
            CompoundTag modulesTag = tag.getCompound("Modules");
            for (Map.Entry<String, MachineModule> entry : modules.entrySet()) {
                if (modulesTag.contains(entry.getKey())) {
                    entry.getValue().load(modulesTag.getCompound(entry.getKey()));
                }
            }
        }
    }
    
    @Override
    protected void saveClientData(CompoundTag tag) {
        super.saveClientData(tag);
        CompoundTag modulesTag = new CompoundTag();
        for (Map.Entry<String, MachineModule> entry : modules.entrySet()) {
            CompoundTag moduleTag = new CompoundTag();
            entry.getValue().save(moduleTag); // For now, modules save same data to client
            modulesTag.put(entry.getKey(), moduleTag);
        }
        tag.put("Modules", modulesTag);
    }

    @Override
    protected void loadClientData(CompoundTag tag) {
        super.loadClientData(tag);
        if (tag.contains("Modules")) {
            CompoundTag modulesTag = tag.getCompound("Modules");
            for (Map.Entry<String, MachineModule> entry : modules.entrySet()) {
                if (modulesTag.contains(entry.getKey())) {
                    entry.getValue().load(modulesTag.getCompound(entry.getKey()));
                }
            }
        }
    }

    public void onModuleUpdated() {
        for (MachineModule module : modules.values()) {
            module.onMachineUpdate();
        }
        markClientSync();
    }
}
