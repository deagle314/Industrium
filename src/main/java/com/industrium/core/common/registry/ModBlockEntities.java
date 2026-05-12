package com.industrium.core.common.registry;

import com.industrium.core.common.fluid.blockentity.BoilerBlockEntity;
import com.industrium.core.common.machine.block.ElectricFurnaceBlockEntity;
import com.industrium.core.common.power.blockentity.BatteryBoxBlockEntity;
import com.industrium.core.common.power.blockentity.CableBlockEntity;
import com.industrium.core.common.power.blockentity.CoalGeneratorBlockEntity;
import com.industrium.core.common.rotation.blockentity.CrusherBlockEntity;
import com.industrium.core.common.rotation.blockentity.FlywheelBlockEntity;
import com.industrium.core.common.rotation.blockentity.GearboxBlockEntity;
import com.industrium.core.common.rotation.blockentity.ShaftBlockEntity;
import com.industrium.core.common.rotation.blockentity.SteamEngineBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    // Power
    public static final RegistryObject<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR = ModRegistry.BLOCK_ENTITIES.register("coal_generator",
        () -> BlockEntityType.Builder.of(CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<BatteryBoxBlockEntity>> BATTERY_BOX = ModRegistry.BLOCK_ENTITIES.register("battery_box",
        () -> BlockEntityType.Builder.of(BatteryBoxBlockEntity::new, ModBlocks.BATTERY_BOX.get()).build(null));

    public static final RegistryObject<BlockEntityType<CableBlockEntity>> POWER_CABLE = ModRegistry.BLOCK_ENTITIES.register("power_cable",
        () -> BlockEntityType.Builder.of(CableBlockEntity::new, ModBlocks.POWER_CABLE.get()).build(null));

    // Machine
    public static final RegistryObject<BlockEntityType<ElectricFurnaceBlockEntity>> ELECTRIC_FURNACE = ModRegistry.BLOCK_ENTITIES.register("electric_furnace",
        () -> BlockEntityType.Builder.of(ElectricFurnaceBlockEntity::new, ModBlocks.ELECTRIC_FURNACE.get()).build(null));

    // Fluid
    public static final RegistryObject<BlockEntityType<BoilerBlockEntity>> BOILER = ModRegistry.BLOCK_ENTITIES.register("boiler",
        () -> BlockEntityType.Builder.of(BoilerBlockEntity::new, ModBlocks.BOILER.get()).build(null));

    // Rotation
    public static final RegistryObject<BlockEntityType<ShaftBlockEntity>> SHAFT = ModRegistry.BLOCK_ENTITIES.register("shaft",
        () -> BlockEntityType.Builder.of(ShaftBlockEntity::new, ModBlocks.SHAFT.get()).build(null));

    public static final RegistryObject<BlockEntityType<FlywheelBlockEntity>> FLYWHEEL = ModRegistry.BLOCK_ENTITIES.register("flywheel",
        () -> BlockEntityType.Builder.of(FlywheelBlockEntity::new, ModBlocks.FLYWHEEL.get()).build(null));

    public static final RegistryObject<BlockEntityType<GearboxBlockEntity>> GEARBOX = ModRegistry.BLOCK_ENTITIES.register("gearbox",
        () -> BlockEntityType.Builder.of(GearboxBlockEntity::new, ModBlocks.GEARBOX.get()).build(null));

    public static final RegistryObject<BlockEntityType<SteamEngineBlockEntity>> STEAM_ENGINE = ModRegistry.BLOCK_ENTITIES.register("steam_engine",
        () -> BlockEntityType.Builder.of(SteamEngineBlockEntity::new, ModBlocks.STEAM_ENGINE.get()).build(null));

    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER = ModRegistry.BLOCK_ENTITIES.register("crusher",
        () -> BlockEntityType.Builder.of(CrusherBlockEntity::new, ModBlocks.CRUSHER.get()).build(null));

    public static void init() {}
}
