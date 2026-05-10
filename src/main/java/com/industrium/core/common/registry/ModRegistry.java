package com.industrium.core.common.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public class ModRegistry {
    public static void register(IEventBus bus) {
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(bus);
    }
}
