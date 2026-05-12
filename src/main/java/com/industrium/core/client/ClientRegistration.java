package com.industrium.core.client;

import com.industrium.core.Industrium;
import com.industrium.core.client.screen.BoilerScreen;
import com.industrium.core.client.screen.CrusherScreen;
import com.industrium.core.common.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Industrium.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistration {
    
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.BOILER.get(), BoilerScreen::new);
            MenuScreens.register(ModMenuTypes.CRUSHER.get(), CrusherScreen::new);
        });
    }
}
