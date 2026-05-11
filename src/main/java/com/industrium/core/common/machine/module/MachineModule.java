package com.industrium.core.common.machine.module;

import net.minecraft.nbt.CompoundTag;

/**
 * Interface for machine modules that can be attached to an AbstractMachineBlockEntity.
 */
public interface MachineModule {
    void tick();
    void save(CompoundTag tag);
    void load(CompoundTag tag);
    
    default void saveClientData(CompoundTag tag) {
        save(tag);
    }
    
    default void loadClientData(CompoundTag tag) {
        load(tag);
    }
    
    default void onMachineUpdate() {}
}
