package com.industrium.core.common.network;

import com.industrium.core.Industrium;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles network-related events.
 */
@Mod.EventBusSubscriber(modid = Industrium.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NetworkEventHandler {
    
    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.level.isClientSide) {
            Industrium.POWER_NETWORK_MANAGER.tick();
            Industrium.HEAT_NETWORK_MANAGER.tick();
            Industrium.FLUID_NETWORK_MANAGER.tick();
        }
    }
}
