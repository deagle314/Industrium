package com.industrium.core.api.network;

import com.industrium.core.api.logistics.ItemPriority;
import net.minecraft.world.item.ItemStack;

/**
 * Specialized node for item logistics networks.
 * 
 * Defines data contracts for item transport, priority handling,
 * and slot-based storage management.
 */
public interface IItemNode extends IIndustriumNode {

    /**
     * Gets the primary item stack at this node.
     * 
     * @return The current item stack (may be empty)
     */
    ItemStack getItem();

    /**
     * Gets the storage capacity in number of item slots.
     * 
     * @return Number of available slots
     */
    int getSlotCapacity();

    /**
     * Gets the priority level of this node for logistics routing.
     * 
     * @return The item priority
     */
    ItemPriority getPriority();

    /**
     * Inserts an item stack into this node.
     * 
     * @param stack The item stack to insert
     * @param simulate If true, only simulate the insertion
     * @return The remaining items that could not be inserted
     */
    ItemStack insert(ItemStack stack, boolean simulate);

    /**
     * Extracts items from this node.
     * 
     * @param amount Maximum number of items to extract
     * @param simulate If true, only simulate the extraction
     * @return The extracted item stack
     */
    ItemStack extract(int amount, boolean simulate);

    /**
     * Checks if this node can receive items from the given source.
     * 
     * @param sourceId The network ID of the source node
     * @return true if items can be received
     */
    default boolean canReceiveFrom(int sourceId) {
        return true;
    }
}