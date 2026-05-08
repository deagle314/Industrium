package com.industrium.core.common.registry;

import com.industrium.core.common.power.blockentity.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
    
    // Block Entity Types
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "industrium");
    
    // Block Entities
    public static final RegistryObject<BlockEntityType<?>> BATTERY_BOX = 
        BLOCK_ENTITIES.register("battery_box", () -> 
            BlockEntityType.Builder.of(BatteryBoxBlockEntity::new).build(null));
    
    public static final RegistryObject<BlockEntityType<?>> COAL_GENERATOR = 
        BLOCK_ENTITIES.register("coal_generator", () -> 
            BlockEntityType.Builder.of(CoalGeneratorBlockEntity::new).build(null));
    
    public static void register() {
        LOGGER.info("Industrium content registered.");
    }
    
    /**
     * Registers all content with the mod bus.
     */
    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
        LOGGER.info("Industrium block entities registered.");
    }
}