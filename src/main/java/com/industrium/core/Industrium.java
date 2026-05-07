package com.industrium.core;

import net.minecraftforge.fml.common.Mod;
import com.industrium.core.common.registry.ModRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Industrium - Industrial Foundation Mod.
 * Provides infrastructure for power, heat, rotational, fluid, and item logistics.
 */
@Mod(Industrium.MOD_ID)
public class Industrium {
    
    public static final String MOD_ID = "industrium";
    public static final String MOD_NAME = "Industrium";
    public static final String MOD_VERSION = "1.0.0";
    
    public static final Logger LOGGER = LogManager.getLogger();
    
    public Industrium() {
        LOGGER.info("Initializing {} v{}", MOD_NAME, MOD_VERSION);
        ModRegistry.register();
        LOGGER.info("{} initialized!", MOD_NAME);
    }
}