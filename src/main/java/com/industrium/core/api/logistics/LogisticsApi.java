package com.industrium.core.api.logistics;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Main API entry point for the Industrium Logistics System.
 * Factory and utility methods.
 */
public final class LogisticsApi {
    
    public static final String LOGISTICS_NETWORK = "industrium:logistics";
    
    /** Default belt speed in items per tick */
    public static final double DEFAULT_BELT_SPEED = 1.0;
    
    private LogisticsApi() {
        // Utility class
    }
    
    /**
     * Creates a new item storage with default capacity.
     */
    public static IItemStorage createStorage(int slots) {
        return new CommonItemStorage(slots);
    }
    
    /**
     * Creates a new item storage with custom throughput.
     */
    public static IItemStorage createStorage(int slots, int maxExtract) {
        return new CommonItemStorage(slots, maxExtract);
    }
    
    /**
     * Gets the visual belt speed multiplier.
     */
    public static double getBeltSpeedMultiplier(double baseSpeed) {
        return baseSpeed;
    }
    
    /**
     * Common item storage implementation.
     */
    private static class CommonItemStorage implements IItemStorage {
        private final int capacity;
        private final int maxExtract;
        private ItemStack storedItem;
        private int itemCount;
        
        CommonItemStorage(int capacity) {
            this(capacity, 64);
        }
        
        CommonItemStorage(int capacity, int maxExtract) {
            this.capacity = capacity;
            this.maxExtract = maxExtract;
            this.storedItem = ItemStack.EMPTY;
            this.itemCount = 0;
        }
        
        @Override
        public ItemStack getItem() {
            return storedItem;
        }
        
        @Override
        public int getCapacity() {
            return capacity;
        }
        
        @Override
        public ItemStack insert(ItemStack stack, boolean simulate) {
            if (stack.isEmpty()) return ItemStack.EMPTY;
            
            if (!storedItem.isEmpty() && !ItemStack.matches(storedItem, stack)) {
                return stack;
            }
            
            int toInsert = Math.min(stack.getCount(), capacity - itemCount);
            if (!simulate) {
                itemCount += toInsert;
                if (storedItem.isEmpty()) {
                    storedItem = stack.copy();
                    storedItem.setCount(toInsert);
                } else {
                    storedItem.grow(toInsert);
                }
            }
            
            ItemStack result = stack.copy();
            result.shrink(toInsert);
            return result;
        }
        
        @Override
        public ItemStack extract(int amount, boolean simulate) {
            if (storedItem.isEmpty() || itemCount <= 0) return ItemStack.EMPTY;
            
            int toExtract = Math.min(amount, Math.min(maxExtract, itemCount));
            if (!simulate) {
                itemCount -= toExtract;
                if (itemCount <= 0) {
                    storedItem = ItemStack.EMPTY;
                }
            }
            
            ItemStack result = storedItem.copy();
            result.setCount(toExtract);
            return result;
        }
    }
}