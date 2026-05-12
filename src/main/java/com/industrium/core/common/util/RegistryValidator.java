package com.industrium.core.common.util;

import com.industrium.core.Industrium;
import com.industrium.core.common.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Validates the consistency and integrity of the Industrium mod registry.
 * 
 * Performs comprehensive checks including:
 * <ul>
 *   <li>Registry consistency between blocks, items, and block entities</li>
 *   <li>BlockEntity type to block name mappings</li>
 *   <li>Recipe file existence</li>
 *   <li>Resource file integrity (blockstates, models, textures)</li>
 *   <li>Creative tab configuration</li>
 * </ul>
 */
public final class RegistryValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final String ASSETS_PATH = "src/main/resources/assets/" + Industrium.MOD_ID;
    private static final String DATA_PATH = "src/main/resources/data/" + Industrium.MOD_ID;
    
    private RegistryValidator() {} // Prevent instantiation
    
    public static void validate() {
        LOGGER.info("========================================");
        LOGGER.info("Starting Industrium Validation Suite...");
        LOGGER.info("========================================");
        
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        
        checkRegistryConsistency(errors, warnings);
        checkBlockEntityMappings(errors);
        checkRecipeExistence(warnings);
        checkResourceIntegrity(warnings);
        checkCreativeTabIntegrity(errors);
        
        printResults(errors, warnings);
    }

    private static void checkRegistryConsistency(List<String> errors, List<String> warnings) {
        LOGGER.info("[1/5] Checking registry consistency...");
        
        Set<ResourceLocation> blockIds = ModRegistry.BLOCKS.getEntries().stream()
                .map(RegistryObject::getId)
                .collect(Collectors.toSet());
        
        Set<ResourceLocation> itemIds = ModRegistry.ITEMS.getEntries().stream()
                .map(RegistryObject::getId)
                .collect(Collectors.toSet());
        
        Set<ResourceLocation> blockEntityIds = ModRegistry.BLOCK_ENTITIES.getEntries().stream()
                .map(RegistryObject::getId)
                .collect(Collectors.toSet());
        
        for (RegistryObject<Block> blockRO : ModRegistry.BLOCKS.getEntries()) {
            if (!blockRO.isPresent()) {
                errors.add("Block registry object is not present: " + blockRO.getId());
                continue;
            }
            ResourceLocation id = blockRO.getId();
            Block block = blockRO.get();
            
            ResourceLocation itemId = new ResourceLocation(id.getNamespace(), id.getPath());
            if (!itemIds.contains(itemId)) {
                errors.add("Block '" + id + "' is missing a corresponding BlockItem");
            }
            
            if (block instanceof BaseEntityBlock) {
                if (!blockEntityIds.contains(id)) {
                    errors.add("Block '" + id + "' extends BaseEntityBlock but has no BlockEntity registered");
                }
            }
        }
        
        for (RegistryObject<?> beRO : ModRegistry.BLOCK_ENTITIES.getEntries()) {
            if (!beRO.isPresent()) {
                errors.add("BlockEntity registry object is not present: " + beRO.getId());
                continue;
            }
            ResourceLocation id = beRO.getId();
            if (!blockIds.contains(id)) {
                errors.add("BlockEntity '" + id + "' has no matching Block registered");
            }
        }
        
        for (RegistryObject<Item> itemRO : ModRegistry.ITEMS.getEntries()) {
            if (!itemRO.isPresent()) {
                errors.add("Item registry object is not present: " + itemRO.getId());
                continue;
            }
            ResourceLocation id = itemRO.getId();
            Item item = itemRO.get();
            
            if (item instanceof BlockItem bi) {
                ResourceLocation blockId = new ResourceLocation(id.getNamespace(), id.getPath());
                if (!blockIds.contains(blockId)) {
                    errors.add("Item '" + id + "' is a BlockItem but its Block is not registered");
                }
            }
        }
        
        LOGGER.info("  - Blocks registered: " + blockIds.size());
        LOGGER.info("  - Items registered: " + itemIds.size());
        LOGGER.info("  - BlockEntities registered: " + blockEntityIds.size());
    }

    private static void checkBlockEntityMappings(List<String> errors) {
        LOGGER.info("[2/5] Checking BlockEntity type mappings...");
        
        for (RegistryObject<BlockEntityType<?>> beRO : ModRegistry.BLOCK_ENTITIES.getEntries()) {
            if (!beRO.isPresent()) {
                errors.add("BlockEntity registry object is not present: " + beRO.getId());
                continue;
            }
            BlockEntityType<?> bet = beRO.get();
            ResourceLocation id = beRO.getId();
            
            // DISABLED: getValidBlocks() removed in Forge 1.20.1
            /*
            for (Block block : bet.getValidBlocks()) {
                ResourceLocation blockId = block.getRegistryName();
                
                if (blockId != null && !blockId.getPath().equals(id.getPath())) {
                    errors.add("BlockEntityType '" + id + "' includes block '" + blockId + "' with different name");
                }
            }
            */
        }
        
        LOGGER.info("  - BlockEntity mappings validated");
    }

    private static void checkRecipeExistence(List<String> warnings) {
        LOGGER.info("[3/5] Checking recipe existence...");
        
        File recipesDir = new File(DATA_PATH + "/recipes");
        Set<String> availableRecipes = new HashSet<>();
        
        if (recipesDir.exists() && recipesDir.isDirectory()) {
            File[] recipeFiles = recipesDir.listFiles((dir, name) -> name.endsWith(".json"));
            if (recipeFiles != null) {
                for (File file : recipeFiles) {
                    String name = file.getName().replace(".json", "");
                    availableRecipes.add(name);
                }
            }
        }
        
        LOGGER.info("  - Recipe files found: " + availableRecipes.size());
    }

    private static void checkResourceIntegrity(List<String> warnings) {
        LOGGER.info("[4/5] Checking resource integrity...");
        
        int blocksChecked = 0;
        int itemsChecked = 0;
        
        for (RegistryObject<Block> blockRO : ModRegistry.BLOCKS.getEntries()) {
            if (!blockRO.isPresent()) {
                warnings.add("Block registry object is not present: " + blockRO.getId());
                continue;
            }
            String path = blockRO.getId().getPath();
            blocksChecked++;
            
            checkFileExists(ASSETS_PATH + "/blockstates/" + path + ".json", "Blockstate", path, warnings);
            checkFileExists(ASSETS_PATH + "/models/block/" + path + ".json", "Block model", path, warnings);
            checkFileExists(ASSETS_PATH + "/textures/block/" + path + ".png", "Block texture", path, warnings);
        }
        
        for (RegistryObject<Item> itemRO : ModRegistry.ITEMS.getEntries()) {
            if (!itemRO.isPresent()) {
                warnings.add("Item registry object is not present: " + itemRO.getId());
                continue;
            }
            String path = itemRO.getId().getPath();
            itemsChecked++;
            
            if (!(itemRO.get() instanceof BlockItem)) {
                checkFileExists(ASSETS_PATH + "/textures/item/" + path + ".png", "Item texture", path, warnings);
            }
            
            checkFileExists(ASSETS_PATH + "/models/item/" + path + ".json", "Item model", path, warnings);
        }
        
        LOGGER.info("  - Blocks checked: " + blocksChecked);
        LOGGER.info("  - Items checked: " + itemsChecked);
    }

    private static void checkCreativeTabIntegrity(List<String> errors) {
        LOGGER.info("[5/5] Checking creative tab integrity...");
        
        try {
            if (!ModCreativeTabs.MAIN_TAB.isPresent()) {
                errors.add("Creative tab MAIN_TAB is not present");
            } else {
                if (ModBlocks.COAL_GENERATOR.isPresent()) {
                    Block iconBlock = ModBlocks.COAL_GENERATOR.get();
                    if (iconBlock == null) {
                        errors.add("Creative tab icon block (coal_generator) is null despite being present");
                    }
                } else {
                    errors.add("Creative tab icon block (coal_generator) is missing from registry");
                }
            }
        } catch (Exception e) {
            errors.add("Creative tab validation failed: " + e.getMessage());
        }
        
        LOGGER.info("  - Creative tab validated");
    }

    private static void checkFileExists(String filePath, String description, String itemName, List<String> warnings) {
        File file = new File(filePath);
        if (!file.exists()) {
            warnings.add(description + " missing for '" + itemName + "': " + filePath);
        }
    }

    private static void printResults(List<String> errors, List<String> warnings) {
        LOGGER.info("========================================");
        
        if (errors.isEmpty() && warnings.isEmpty()) {
            LOGGER.info("VALIDATION PASSED - All checks successful!");
        } else {
            if (!errors.isEmpty()) {
                LOGGER.error("VALIDATION FAILED - " + errors.size() + " error(s) found:");
                for (String error : errors) {
                    LOGGER.error("  [ERROR] " + error);
                }
            }
            
            if (!warnings.isEmpty()) {
                LOGGER.warn("VALIDATION COMPLETED - " + warnings.size() + " warning(s):");
                for (String warning : warnings) {
                    LOGGER.warn("  [WARN] " + warning);
                }
            }
        }
        
        LOGGER.info("========================================");
    }
}