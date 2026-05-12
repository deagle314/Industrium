package com.industrium.core.common.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    // Items
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

    // Block Items
    public static final RegistryObject<Item> COAL_GENERATOR = fromBlock(ModBlocks.COAL_GENERATOR);
    public static final RegistryObject<Item> BATTERY_BOX = fromBlock(ModBlocks.BATTERY_BOX);
    public static final RegistryObject<Item> POWER_CABLE = fromBlock(ModBlocks.POWER_CABLE);
    public static final RegistryObject<Item> ELECTRIC_FURNACE = fromBlock(ModBlocks.ELECTRIC_FURNACE);
    public static final RegistryObject<Item> BOILER = fromBlock(ModBlocks.BOILER);
    public static final RegistryObject<Item> CHECK_VALVE = fromBlock(ModBlocks.CHECK_VALVE);
    public static final RegistryObject<Item> CONDENSER = fromBlock(ModBlocks.CONDENSER);
    public static final RegistryObject<Item> COPPER_PIPE = fromBlock(ModBlocks.COPPER_PIPE);
    public static final RegistryObject<Item> ELECTRIC_PUMP = fromBlock(ModBlocks.ELECTRIC_PUMP);
    public static final RegistryObject<Item> FLOW_METER = fromBlock(ModBlocks.FLOW_METER);
    public static final RegistryObject<Item> FLUID_TANK = fromBlock(ModBlocks.FLUID_TANK);
    public static final RegistryObject<Item> MANUAL_VALVE = fromBlock(ModBlocks.MANUAL_VALVE);
    public static final RegistryObject<Item> STEEL_PIPE = fromBlock(ModBlocks.STEEL_PIPE);
    public static final RegistryObject<Item> COAL_BURNER = fromBlock(ModBlocks.COAL_BURNER);
    public static final RegistryObject<Item> ELECTRIC_HEATER = fromBlock(ModBlocks.ELECTRIC_HEATER);
    public static final RegistryObject<Item> HEAT_METER = fromBlock(ModBlocks.HEAT_METER);
    public static final RegistryObject<Item> HEAT_PIPE = fromBlock(ModBlocks.HEAT_PIPE);
    public static final RegistryObject<Item> INSULATED_HEAT_PIPE = fromBlock(ModBlocks.INSULATED_HEAT_PIPE);
    public static final RegistryObject<Item> RADIATOR = fromBlock(ModBlocks.RADIATOR);
    public static final RegistryObject<Item> STEAM_BOILER = fromBlock(ModBlocks.STEAM_BOILER);
    public static final RegistryObject<Item> THERMAL_BATTERY = fromBlock(ModBlocks.THERMAL_BATTERY);
    public static final RegistryObject<Item> WATER_COOLER = fromBlock(ModBlocks.WATER_COOLER);
    public static final RegistryObject<Item> CONVEYOR_BELT = fromBlock(ModBlocks.CONVEYOR_BELT);
    public static final RegistryObject<Item> CRATE = fromBlock(ModBlocks.CRATE);
    public static final RegistryObject<Item> FAST_CONVEYOR_BELT = fromBlock(ModBlocks.FAST_CONVEYOR_BELT);
    public static final RegistryObject<Item> LOADER = fromBlock(ModBlocks.LOADER);
    public static final RegistryObject<Item> MERGER = fromBlock(ModBlocks.MERGER);
    public static final RegistryObject<Item> SMART_SORTER = fromBlock(ModBlocks.SMART_SORTER);
    public static final RegistryObject<Item> SPLITTER = fromBlock(ModBlocks.SPLITTER);
    public static final RegistryObject<Item> UNLOADER = fromBlock(ModBlocks.UNLOADER);
    public static final RegistryObject<Item> VERTICAL_CHUTE = fromBlock(ModBlocks.VERTICAL_CHUTE);
    public static final RegistryObject<Item> WAREHOUSE_BIN = fromBlock(ModBlocks.WAREHOUSE_BIN);
    public static final RegistryObject<Item> BELT_PULLEY = fromBlock(ModBlocks.BELT_PULLEY);
    public static final RegistryObject<Item> CHAIN_DRIVE = fromBlock(ModBlocks.CHAIN_DRIVE);
    public static final RegistryObject<Item> CLUTCH = fromBlock(ModBlocks.CLUTCH);
    public static final RegistryObject<Item> CRUSHER = fromBlock(ModBlocks.CRUSHER);
    public static final RegistryObject<Item> ELECTRIC_MOTOR = fromBlock(ModBlocks.ELECTRIC_MOTOR);
    public static final RegistryObject<Item> FLYWHEEL = fromBlock(ModBlocks.FLYWHEEL);
    public static final RegistryObject<Item> GEARBOX = fromBlock(ModBlocks.GEARBOX);
    public static final RegistryObject<Item> MECHANICAL_PUMP = fromBlock(ModBlocks.MECHANICAL_PUMP);
    public static final RegistryObject<Item> SHAFT = fromBlock(ModBlocks.SHAFT);
    public static final RegistryObject<Item> STEAM_ENGINE = fromBlock(ModBlocks.STEAM_ENGINE);
    public static final RegistryObject<Item> CENTRAL_CONSOLE = fromBlock(ModBlocks.CENTRAL_CONSOLE);
    public static final RegistryObject<Item> EMERGENCY_STOP_SWITCH = fromBlock(ModBlocks.EMERGENCY_STOP_SWITCH);
    public static final RegistryObject<Item> INDICATOR_LAMP = fromBlock(ModBlocks.INDICATOR_LAMP);
    public static final RegistryObject<Item> NUMERIC_DISPLAY = fromBlock(ModBlocks.NUMERIC_DISPLAY);
    public static final RegistryObject<Item> POWER_SENSOR = fromBlock(ModBlocks.POWER_SENSOR);
    public static final RegistryObject<Item> RELAY_BOX = fromBlock(ModBlocks.RELAY_BOX);
    public static final RegistryObject<Item> SIGNAL_CABLE = fromBlock(ModBlocks.SIGNAL_CABLE);
    public static final RegistryObject<Item> TEMPERATURE_SENSOR = fromBlock(ModBlocks.TEMPERATURE_SENSOR);
    public static final RegistryObject<Item> THRESHOLD_CONTROLLER = fromBlock(ModBlocks.THRESHOLD_CONTROLLER);
    public static final RegistryObject<Item> TIMER_UNIT = fromBlock(ModBlocks.TIMER_UNIT);
    public static final RegistryObject<Item> INTEGRATION_ELECTRIC_HEATER = fromBlock(ModBlocks.INTEGRATION_ELECTRIC_HEATER);
    public static final RegistryObject<Item> INTEGRATION_ELECTRIC_MOTOR = fromBlock(ModBlocks.INTEGRATION_ELECTRIC_MOTOR);
    public static final RegistryObject<Item> HEAT_EXCHANGER = fromBlock(ModBlocks.HEAT_EXCHANGER);
    public static final RegistryObject<Item> MOTOR_PUMP = fromBlock(ModBlocks.MOTOR_PUMP);
    public static final RegistryObject<Item> SMART_BREAKER = fromBlock(ModBlocks.SMART_BREAKER);
    public static final RegistryObject<Item> STEAM_TURBINE = fromBlock(ModBlocks.STEAM_TURBINE);

    private static RegistryObject<Item> fromBlock(RegistryObject<net.minecraft.world.level.block.Block> block) {
        return ModRegistry.ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void init() {}
}
