package com.industrium.core.common.registry;

import com.industrium.core.Industrium;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Industrium.MOD_ID);

    // Block Items
    public static final RegistryObject<Item> COAL_GENERATOR =
            ITEMS.register("coal_generator",
                    () -> new BlockItem(ModBlocks.COAL_GENERATOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> BATTERY_BOX =
            ITEMS.register("battery_box",
                    () -> new BlockItem(ModBlocks.BATTERY_BOX.get(), new Item.Properties()));

    public static final RegistryObject<Item> POWER_CABLE =
            ITEMS.register("power_cable",
                    () -> new BlockItem(ModBlocks.POWER_CABLE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ELECTRIC_FURNACE =
            ITEMS.register("electric_furnace",
                    () -> new BlockItem(ModBlocks.ELECTRIC_FURNACE.get(), new Item.Properties()));

    // Component Items
    public static final RegistryObject<Item> BATTERY_CELL = ITEMS.register("battery_cell", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BEARING = ITEMS.register("bearing", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CIRCUIT_ADVANCED = ITEMS.register("circuit_advanced", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CIRCUIT_BASIC = ITEMS.register("circuit_basic", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GEAR_IRON = ITEMS.register("gear_iron", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GEAR_STEEL = ITEMS.register("gear_steel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOTOR = ITEMS.register("motor", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLATE_STEEL = ITEMS.register("plate_steel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROTOR = ITEMS.register("rotor", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WIRE_COPPER = ITEMS.register("wire_copper", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MACHINE_CASING = ITEMS.register("machine_casing", () -> new Item(new Item.Properties()));
}
