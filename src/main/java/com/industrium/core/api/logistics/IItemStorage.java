package com.industrium.core.api.logistics;

import net.minecraft.world.item.ItemStack;

/**
 * Item storage interface for logistics system.
 */
public interface IItemStorage {
    
    /** Get stored items. */
    ItemStack getItem();
    
    /** Get storage capacity in slots. */
    int getCapacity();
    
    /** Insert items. */
    ItemStack insert(ItemStack stack, boolean simulate);
    
    /** Extract items. */
    ItemStack extract(int amount, boolean simulate);
}