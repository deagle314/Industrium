package com.industrium.core.common.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public class RegistryBootstrap {
    public static void register(IEventBus bus) {
        ModRegistry.BLOCKS.register(bus);
        ModRegistry.ITEMS.register(bus);
        ModRegistry.BLOCK_ENTITIES.register(bus);
        ModRegistry.MENU_TYPES.register(bus);
        ModRegistry.CREATIVE_MODE_TABS.register(bus);

        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
        ModMenuTypes.init();
        
        ModCreativeTabs.init();
    }
}
