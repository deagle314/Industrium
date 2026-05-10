package com.industrium.core.common.registry;

import com.industrium.core.common.block.BatteryBoxBlock;
import com.industrium.core.common.block.PowerCableBlock;
import com.industrium.core.common.power.block.CoalGeneratorBlock;
import com.industrium.core.common.power.block.CableBlockEntity;
import com.industrium.core.common.power.blockentity.BatteryBoxBlockEntity;
import com.industrium.core.common.power.blockentity.CoalGeneratorBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class PowerModule {
    public static final RegistryObject<Block> COAL_GENERATOR = ModRegistry.BLOCKS.register("coal_generator", CoalGeneratorBlock::new);
    public static final RegistryObject<Item> COAL_GENERATOR_ITEM = ModRegistry.ITEMS.register("coal_generator", () -> new BlockItem(COAL_GENERATOR.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR_BE = ModRegistry.BLOCK_ENTITIES.register("coal_generator", 
        () -> BlockEntityType.Builder.of(CoalGeneratorBlockEntity::new, COAL_GENERATOR.get()).build(null));

    public static final RegistryObject<Block> BATTERY_BOX = ModRegistry.BLOCKS.register("battery_box", BatteryBoxBlock::new);
    public static final RegistryObject<Item> BATTERY_BOX_ITEM = ModRegistry.ITEMS.register("battery_box", () -> new BlockItem(BATTERY_BOX.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<BatteryBoxBlockEntity>> BATTERY_BOX_BE = ModRegistry.BLOCK_ENTITIES.register("battery_box", 
        () -> BlockEntityType.Builder.of(BatteryBoxBlockEntity::new, BATTERY_BOX.get()).build(null));

    public static final RegistryObject<Block> POWER_CABLE = ModRegistry.BLOCKS.register("power_cable", PowerCableBlock::new);
    public static final RegistryObject<Item> POWER_CABLE_ITEM = ModRegistry.ITEMS.register("power_cable", () -> new BlockItem(POWER_CABLE.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<CableBlockEntity>> POWER_CABLE_BE = ModRegistry.BLOCK_ENTITIES.register("power_cable", 
        () -> BlockEntityType.Builder.of(CableBlockEntity::new, POWER_CABLE.get()).build(null));

    public static void init() {}
}
