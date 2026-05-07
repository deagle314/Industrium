package com.industrium.core.common.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Central registry for all Industrium content.
 */
public class ModRegistry {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    public static void register() {
        LOGGER.info("Industrium content registered.");
    }
}