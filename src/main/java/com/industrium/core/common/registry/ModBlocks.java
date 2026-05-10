package com.industrium.core.common.registry;

import com.industrium.core.Industrium;
import com.industrium.core.common.block.BatteryBoxBlock;
import com.industrium.core.common.block.PowerCableBlock;
import com.industrium.core.common.machine.block.ElectricFurnaceBlock;
import com.industrium.core.common.power.block.CoalGeneratorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Industrium.MOD_ID);

    public static final RegistryObject<Block> COAL_GENERATOR =
            BLOCKS.register("coal_generator", CoalGeneratorBlock::new);

    public static final RegistryObject<Block> BATTERY_BOX =
            BLOCKS.register("battery_box", BatteryBoxBlock::new);

    public static final RegistryObject<Block> POWER_CABLE =
            BLOCKS.register("power_cable", PowerCableBlock::new);

    public static final RegistryObject<Block> ELECTRIC_FURNACE =
            BLOCKS.register("electric_furnace", ElectricFurnaceBlock::new);
}
