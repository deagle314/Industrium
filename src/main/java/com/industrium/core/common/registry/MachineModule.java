package com.industrium.core.common.registry;

import com.industrium.core.common.machine.block.ElectricFurnaceBlock;
import com.industrium.core.common.machine.block.ElectricFurnaceBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class MachineModule {
    public static final RegistryObject<Block> ELECTRIC_FURNACE = ModRegistry.BLOCKS.register("electric_furnace", ElectricFurnaceBlock::new);
    public static final RegistryObject<Item> ELECTRIC_FURNACE_ITEM = ModRegistry.ITEMS.register("electric_furnace", () -> new BlockItem(ELECTRIC_FURNACE.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<ElectricFurnaceBlockEntity>> ELECTRIC_FURNACE_BE = ModRegistry.BLOCK_ENTITIES.register("electric_furnace", 
        () -> BlockEntityType.Builder.of(ElectricFurnaceBlockEntity::new, ELECTRIC_FURNACE.get()).build(null));

    public static void init() {}
}
