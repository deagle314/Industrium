package com.industrium.core;

import com.industrium.core.common.registry.ModRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("industrium")
public class Industrium {

    public Industrium() {
        ModRegistry.register(
            FMLJavaModLoadingContext.get().getModEventBus()
        );
    }
}
