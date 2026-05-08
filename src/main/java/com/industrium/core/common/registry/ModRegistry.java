package com.industrium.core.common.registry;

import com.industrium.core.common.power.block.*;
import com.industrium.core.common.power.blockentity.*;
import com.industrium.core.common.block.BatteryBoxBlock;
import com.industrium.core.common.machine.block.ElectricFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
    
    // Blocks registry
    public static final DeferredRegister<Block> BLOCKS = 
        DeferredRegister.create(ForgeRegistries.BLOCKS, "industrium");
    
    // Items registry  
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, "industrium");
    
    // Power Blocks
    public static final RegistryObject<Block> COAL_GENERATOR = BLOCKS.register("coal_generator", CoalGeneratorBlock::new);
    public static final RegistryObject<Block> BATTERY_BOX = BLOCKS.register("battery_box", BatteryBoxBlock::new);
    public static final RegistryObject<Block> POWER_CABLE = BLOCKS.register("power_cable", PowerCableBlock::new);
    
    // Machine Blocks
    public static final RegistryObject<Block> ELECTRIC_FURNACE = BLOCKS.register("electric_furnace", ElectricFurnaceBlock::new);
    
    // Block Entity Types (for tile entities)
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "industrium");
    
    // Block Entity Types
    public static final RegistryObject<BlockEntityType<?>> BATTERY_BOX_TE = 
        BLOCK_ENTITIES.register("battery_box", () -> 
            BlockEntityType.Builder.of(BatteryBoxBlockEntity::new).build(null));
    
    public static final RegistryObject<BlockEntityType<?>> COAL_GENERATOR_TE = 
        BLOCK_ENTITIES.register("coal_generator", () -> 
            BlockEntityType.Builder.of(CoalGeneratorBlockEntity::new).build(null));
    
    public static void register() {
        LOGGER.info("Industrium content registered.");
    }
    
    /**
     * Registers all content with the mod bus.
     */
    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        LOGGER.info("Industrium content registered.");
    }
}