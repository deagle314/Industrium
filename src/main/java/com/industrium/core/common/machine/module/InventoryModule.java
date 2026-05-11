package com.industrium.core.common.machine.module;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

/**
 * Module for handling item storage.
 * Compositional replacement for IInventory/Container.
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

    public ItemStack getItem(int slot) {
        if (slot < 0 || slot >= items.size()) return ItemStack.EMPTY;
        return items.get(slot);
    }

    public void setItem(int slot, ItemStack stack) {
        if (slot >= 0 && slot < items.size()) {
            items.set(slot, stack);
        }
    }
    
    public int getSize() {
        return items.size();
    }
    
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }
}
