package com.industrium.core.common.registry;

import com.industrium.core.Industrium;
import com.industrium.core.common.block.BatteryBoxBlock;
import com.industrium.core.common.machine.block.ElectricFurnaceBlock;
import com.industrium.core.common.power.block.CoalGeneratorBlock;
import com.industrium.core.common.power.block.PowerCableBlock;
import com.industrium.core.common.power.blockentity.BatteryBoxBlockEntity;
import com.industrium.core.common.power.blockentity.CoalGeneratorBlockEntity;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Central registry for all Industrium content.
 */
public class ModRegistry {

    private static final Logger LOGGER = LogManager.getLogger();

    // Blocks
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Industrium.MOD_ID);

    // Items
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Industrium.MOD_ID);

    // Block Entities
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Industrium.MOD_ID);

    /* ----------------------------
       Blocks
       ---------------------------- */

    public static final RegistryObject<Block> COAL_GENERATOR =
            BLOCKS.register("coal_generator", CoalGeneratorBlock::new);

    public static final RegistryObject<Block> BATTERY_BOX =
            BLOCKS.register("battery_box", BatteryBoxBlock::new);

    public static final RegistryObject<Block> POWER_CABLE =
            BLOCKS.register("power_cable", PowerCableBlock::new);

    public static final RegistryObject<Block> ELECTRIC_FURNACE =
            BLOCKS.register("electric_furnace", ElectricFurnaceBlock::new);

    /* ----------------------------
       Block Items
       ---------------------------- */

    public static final RegistryObject<Item> COAL_GENERATOR_ITEM =
            ITEMS.register("coal_generator",
                    () -> new BlockItem(COAL_GENERATOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> BATTERY_BOX_ITEM =
            ITEMS.register("battery_box",
                    () -> new BlockItem(BATTERY_BOX.get(), new Item.Properties()));

    public static final RegistryObject<Item> POWER_CABLE_ITEM =
            ITEMS.register("power_cable",
                    () -> new BlockItem(POWER_CABLE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ELECTRIC_FURNACE_ITEM =
            ITEMS.register("electric_furnace",
                    () -> new BlockItem(ELECTRIC_FURNACE.get(), new Item.Properties()));

    /* ----------------------------
       Block Entities
       ---------------------------- */

    public static final RegistryObject<BlockEntityType<BatteryBoxBlockEntity>> BATTERY_BOX_TE =
            BLOCK_ENTITIES.register("battery_box",
                    () -> BlockEntityType.Builder.of(
                            BatteryBoxBlockEntity::new,
                            BATTERY_BOX.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR_TE =
            BLOCK_ENTITIES.register("coal_generator",
                    () -> BlockEntityType.Builder.of(
                            CoalGeneratorBlockEntity::new,
                            COAL_GENERATOR.get()
                    ).build(null));

    /**
     * Register everything to Forge mod event bus.
     */
    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);

        LOGGER.info("Industrium registries connected.");
    }
}
