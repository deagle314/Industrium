package com.industrium.core.common.registry;

import com.industrium.core.common.fluid.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class FluidModule {
    public static final RegistryObject<Block> BOILER = register("boiler", BoilerBlock::new);
    public static final RegistryObject<Block> CHECK_VALVE = register("check_valve", CheckValveBlock::new);
    public static final RegistryObject<Block> CONDENSER = register("condenser", CondenserBlock::new);
    public static final RegistryObject<Block> COPPER_PIPE = register("copper_pipe", CopperPipeBlock::new);
    public static final RegistryObject<Block> ELECTRIC_PUMP = register("electric_pump", ElectricPumpBlock::new);
    public static final RegistryObject<Block> FLOW_METER = register("flow_meter", FlowMeterBlock::new);
    public static final RegistryObject<Block> FLUID_TANK = register("fluid_tank", FluidTankBlock::new);
    public static final RegistryObject<Block> MANUAL_VALVE = register("manual_valve", ManualValveBlock::new);
    public static final RegistryObject<Block> MECHANICAL_PUMP = register("mechanical_pump", MechanicalPumpBlock::new);
    public static final RegistryObject<Block> STEEL_PIPE = register("steel_pipe", SteelPipeBlock::new);

    private static RegistryObject<Block> register(String name, java.util.function.Supplier<Block> block) {
        RegistryObject<Block> ret = ModRegistry.BLOCKS.register(name, block);
        ModRegistry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return ret;
    }

    public static void init() {}
}
