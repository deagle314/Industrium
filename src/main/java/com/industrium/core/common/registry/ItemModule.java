package com.industrium.core.common.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ItemModule {
    public static final RegistryObject<Item> BATTERY_CELL = ModRegistry.ITEMS.register("battery_cell", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BEARING = ModRegistry.ITEMS.register("bearing", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CIRCUIT_ADVANCED = ModRegistry.ITEMS.register("circuit_advanced", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CIRCUIT_BASIC = ModRegistry.ITEMS.register("circuit_basic", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GEAR_IRON = ModRegistry.ITEMS.register("gear_iron", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GEAR_STEEL = ModRegistry.ITEMS.register("gear_steel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOTOR = ModRegistry.ITEMS.register("motor", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLATE_STEEL = ModRegistry.ITEMS.register("plate_steel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROTOR = ModRegistry.ITEMS.register("rotor", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WIRE_COPPER = ModRegistry.ITEMS.register("wire_copper", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MACHINE_CASING = ModRegistry.ITEMS.register("machine_casing", () -> new Item(new Item.Properties()));

    public static void init() {}
}
