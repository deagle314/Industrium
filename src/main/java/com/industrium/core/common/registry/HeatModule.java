package com.industrium.core.common.registry;

import com.industrium.core.common.heat.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class HeatModule {
    public static final RegistryObject<Block> COAL_BURNER = register("coal_burner", CoalBurnerBlock::new);
    public static final RegistryObject<Block> ELECTRIC_HEATER = register("electric_heater", ElectricHeaterBlock::new);
    public static final RegistryObject<Block> HEAT_METER = register("heat_meter", HeatMeterBlock::new);
    public static final RegistryObject<Block> HEAT_PIPE = register("heat_pipe", HeatPipeBlock::new);
    public static final RegistryObject<Block> INSULATED_HEAT_PIPE = register("insulated_heat_pipe", InsulatedHeatPipeBlock::new);
    public static final RegistryObject<Block> RADIATOR = register("radiator", RadiatorBlock::new);
    public static final RegistryObject<Block> STEAM_BOILER = register("steam_boiler", SteamBoilerBlock::new);
    public static final RegistryObject<Block> THERMAL_BATTERY = register("thermal_battery", ThermalBatteryBlock::new);
    public static final RegistryObject<Block> WATER_COOLER = register("water_cooler", WaterCoolerBlock::new);

    private static RegistryObject<Block> register(String name, java.util.function.Supplier<Block> block) {
        RegistryObject<Block> ret = ModRegistry.BLOCKS.register(name, block);
        ModRegistry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return ret;
    }

    public static void init() {}
}
