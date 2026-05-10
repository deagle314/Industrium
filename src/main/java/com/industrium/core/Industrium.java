package com.industrium.core;

import com.industrium.core.common.registry.ModRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Industrium.MOD_ID)
public class Industrium {

    public static final String MOD_ID = "industrium";

    public Industrium() {
        ModRegistry.register(
            FMLJavaModLoadingContext.get().getModEventBus()
        );
    }
}
