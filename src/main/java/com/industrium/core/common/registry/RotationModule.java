package com.industrium.core.common.registry;

import com.industrium.core.common.rotation.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class RotationModule {
    public static final RegistryObject<Block> BELT_PULLEY = register("belt_pulley", BeltPulleyBlock::new);
    public static final RegistryObject<Block> CHAIN_DRIVE = register("chain_drive", ChainDriveBlock::new);
    public static final RegistryObject<Block> CLUTCH = register("clutch", ClutchBlock::new);
    public static final RegistryObject<Block> CRUSHER = register("crusher", CrusherBlock::new);
    public static final RegistryObject<Block> ELECTRIC_MOTOR = register("electric_motor", ElectricMotorBlock::new);
    public static final RegistryObject<Block> FLYWHEEL = register("flywheel", FlywheelBlock::new);
    public static final RegistryObject<Block> GEARBOX = register("gearbox", GearboxBlock::new);
    public static final RegistryObject<Block> MECHANICAL_PUMP = register("mechanical_pump", MechanicalPumpBlock::new);
    public static final RegistryObject<Block> SHAFT = register("shaft", ShaftBlock::new);
    public static final RegistryObject<Block> STEAM_ENGINE = register("steam_engine", SteamEngineBlock::new);

    private static RegistryObject<Block> register(String name, java.util.function.Supplier<Block> block) {
        RegistryObject<Block> ret = ModRegistry.BLOCKS.register(name, block);
        ModRegistry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return ret;
    }

    public static void init() {}
}
