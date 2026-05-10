package com.industrium.core.common.registry;

import com.industrium.core.Industrium;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final RegistryObject<CreativeModeTab> MAIN_TAB = ModRegistry.CREATIVE_MODE_TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.industrium"))
                    .icon(() -> new ItemStack(PowerModule.COAL_GENERATOR.get()))
                    .displayItems((parameters, output) -> {
                        ModRegistry.ITEMS.getEntries().forEach(itemRO -> {
                            output.accept(itemRO.get());
                        });
                    })
                    .build());

    public static void init() {}
}
