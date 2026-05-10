package com.industrium.core.common.registry;

import com.industrium.core.common.logistics.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class LogisticsModule {
    public static final RegistryObject<Block> CONVEYOR_BELT = register("conveyor_belt", ConveyorBeltBlock::new);
    public static final RegistryObject<Block> CRATE = register("crate", CrateBlock::new);
    public static final RegistryObject<Block> FAST_CONVEYOR_BELT = register("fast_conveyor_belt", FastConveyorBeltBlock::new);
    public static final RegistryObject<Block> LOADER = register("loader", LoaderBlock::new);
    public static final RegistryObject<Block> MERGER = register("merger", MergerBlock::new);
    public static final RegistryObject<Block> SMART_SORTER = register("smart_sorter", SmartSorterBlock::new);
    public static final RegistryObject<Block> SPLITTER = register("splitter", SplitterBlock::new);
    public static final RegistryObject<Block> UNLOADER = register("unloader", UnloaderBlock::new);
    public static final RegistryObject<Block> VERTICAL_CHUTE = register("vertical_chute", VerticalChuteBlock::new);
    public static final RegistryObject<Block> WAREHOUSE_BIN = register("warehouse_bin", WarehouseBinBlock::new);

    private static RegistryObject<Block> register(String name, java.util.function.Supplier<Block> block) {
        RegistryObject<Block> ret = ModRegistry.BLOCKS.register(name, block);
        ModRegistry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return ret;
    }

    public static void init() {}
}
