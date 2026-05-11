package com.industrium.core.common.registry;

import com.industrium.core.common.rotation.block.*;
import com.industrium.core.common.rotation.blockentity.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

    public static final RegistryObject<BlockEntityType<ShaftBlockEntity>> SHAFT_BE = ModRegistry.BLOCK_ENTITIES.register("shaft",
        () -> BlockEntityType.Builder.of(ShaftBlockEntity::new, SHAFT.get()).build(null));

    public static final RegistryObject<BlockEntityType<FlywheelBlockEntity>> FLYWHEEL_BE = ModRegistry.BLOCK_ENTITIES.register("flywheel",
        () -> BlockEntityType.Builder.of(FlywheelBlockEntity::new, FLYWHEEL.get()).build(null));

    public static final RegistryObject<BlockEntityType<GearboxBlockEntity>> GEARBOX_BE = ModRegistry.BLOCK_ENTITIES.register("gearbox",
        () -> BlockEntityType.Builder.of(GearboxBlockEntity::new, GEARBOX.get()).build(null));

    public static final RegistryObject<BlockEntityType<SteamEngineBlockEntity>> STEAM_ENGINE_BE = ModRegistry.BLOCK_ENTITIES.register("steam_engine",
        () -> BlockEntityType.Builder.of(SteamEngineBlockEntity::new, STEAM_ENGINE.get()).build(null));

    private static RegistryObject<Block> register(String name, java.util.function.Supplier<Block> block) {
        RegistryObject<Block> ret = ModRegistry.BLOCKS.register(name, block);
        ModRegistry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return ret;
    }

    public static void init() {}
}
