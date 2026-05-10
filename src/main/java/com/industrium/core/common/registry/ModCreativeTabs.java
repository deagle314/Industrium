package com.industrium.core.common.registry;

import com.industrium.core.Industrium;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Industrium.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.industrium"))
                    .icon(() -> new ItemStack(ModBlocks.COAL_GENERATOR.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.COAL_GENERATOR.get());
                        output.accept(ModItems.BATTERY_BOX.get());
                        output.accept(ModItems.POWER_CABLE.get());
                        output.accept(ModItems.ELECTRIC_FURNACE.get());
                        
                        output.accept(ModItems.BATTERY_CELL.get());
                        output.accept(ModItems.BEARING.get());
                        output.accept(ModItems.CIRCUIT_ADVANCED.get());
                        output.accept(ModItems.CIRCUIT_BASIC.get());
                        output.accept(ModItems.GEAR_IRON.get());
                        output.accept(ModItems.GEAR_STEEL.get());
                        output.accept(ModItems.MOTOR.get());
                        output.accept(ModItems.PLATE_STEEL.get());
                        output.accept(ModItems.ROTOR.get());
                        output.accept(ModItems.WIRE_COPPER.get());
                        output.accept(ModItems.MACHINE_CASING.get());
                    })
                    .build());
}
