package com.industrium.core;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import com.industrium.core.common.registry.ModRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Industrium - Industrial Foundation Mod.
 * Provides infrastructure for power, heat, rotational, fluid, and item logistics.
 */
@Mod(Industrium.MOD_ID)
@EventBusSubscriber(modid = "industrium")
public class Industrium {
    
    public static final String MOD_ID = "industrium";
    public static final String MOD_NAME = "Industrium";
    public static final String MOD_VERSION = "0.1.0";
    
    public static final Logger LOGGER = LogManager.getLogger();
    
    public Industrium() {
        LOGGER.info("Initializing {} v{}", MOD_NAME, MOD_VERSION);
        ModRegistry.register();
        LOGGER.info("{} initialized!", MOD_NAME);
    }
}