package com.industrium.core.common.registry;

import com.industrium.core.Industrium;
import com.industrium.core.common.power.block.CableBlockEntity;
import com.industrium.core.common.machine.block.ElectricFurnaceBlockEntity;
import com.industrium.core.common.power.blockentity.BatteryBoxBlockEntity;
import com.industrium.core.common.power.blockentity.CoalGeneratorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Industrium.MOD_ID);

    public static final RegistryObject<BlockEntityType<BatteryBoxBlockEntity>> BATTERY_BOX =
            BLOCK_ENTITIES.register("battery_box",
                    () -> BlockEntityType.Builder.of(
                            BatteryBoxBlockEntity::new,
                            ModBlocks.BATTERY_BOX.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR =
            BLOCK_ENTITIES.register("coal_generator",
                    () -> BlockEntityType.Builder.of(
                            CoalGeneratorBlockEntity::new,
                            ModBlocks.COAL_GENERATOR.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<CableBlockEntity>> POWER_CABLE =
            BLOCK_ENTITIES.register("power_cable",
                    () -> BlockEntityType.Builder.of(
                            CableBlockEntity::new,
                            ModBlocks.POWER_CABLE.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<ElectricFurnaceBlockEntity>> ELECTRIC_FURNACE =
            BLOCK_ENTITIES.register("electric_furnace",
                    () -> BlockEntityType.Builder.of(
                            ElectricFurnaceBlockEntity::new,
                            ModBlocks.ELECTRIC_FURNACE.get()
                    ).build(null));
}
