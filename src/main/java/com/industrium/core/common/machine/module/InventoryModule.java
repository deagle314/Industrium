package com.industrium.core.common.machine.module;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

/**
 * Module for handling item storage.
 */
public class InventoryModule implements MachineModule {
    private final NonNullList<ItemStack> items;

    public InventoryModule(int size) {
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    public void tick() {}

    @Override
    public void save(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, items);
    }

    @Override
    public void load(CompoundTag tag) {
        items.clear();
        ContainerHelper.loadAllItems(tag, items);
    }

    @Override
    public void saveClientData(CompoundTag tag) {
        // Inventories are typically handled by containers, so we don't sync them by default
    }

    @Override
    public void loadClientData(CompoundTag tag) {
        // Inventories are typically handled by containers, so we don't sync them by default
    }

    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
    }
    
    public int getSize() {
        return items.size();
    }
    
    public NonNullList<ItemStack> getItems() {
        return items;
    }
}
