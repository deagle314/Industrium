package com.industrium.core.common.registry;

import com.industrium.core.common.integration.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class IntegrationModule {
    public static final RegistryObject<Block> ELECTRIC_HEATER = register("integration_electric_heater", ElectricHeaterBlock::new);
    public static final RegistryObject<Block> ELECTRIC_MOTOR = register("integration_electric_motor", ElectricMotorBlock::new);
    public static final RegistryObject<Block> HEAT_EXCHANGER = register("heat_exchanger", HeatExchangerBlock::new);
    public static final RegistryObject<Block> MOTOR_PUMP = register("motor_pump", MotorPumpBlock::new);
    public static final RegistryObject<Block> SMART_BREAKER = register("smart_breaker", SmartBreakerBlock::new);
    public static final RegistryObject<Block> STEAM_TURBINE = register("steam_turbine", SteamTurbineBlock::new);

    private static RegistryObject<Block> register(String name, java.util.function.Supplier<Block> block) {
        RegistryObject<Block> ret = ModRegistry.BLOCKS.register(name, block);
        ModRegistry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return ret;
    }

    public static void init() {}
}
