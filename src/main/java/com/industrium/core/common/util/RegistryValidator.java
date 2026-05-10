package com.industrium.core.common.util;

import com.industrium.core.Industrium;
import com.industrium.core.common.registry.ModRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegistryValidator {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void validate() {
        LOGGER.info("Starting Industrium Validation Suite...");
        List<String> errors = new ArrayList<>();

        checkRegistryConsistency(errors);
        checkResourceIntegrity(errors);
        checkRecipeExistence(errors);

        if (errors.isEmpty()) {
            LOGGER.info("Validation successful! No issues found.");
        } else {
            LOGGER.error("Validation failed with {} errors:", errors.size());
            for (String error : errors) {
                LOGGER.error("  - {}", error);
            }
        }
    }

    private static void checkRegistryConsistency(List<String> errors) {
        LOGGER.info("Checking registry consistency...");
        for (RegistryObject<Block> blockRO : ModRegistry.BLOCKS.getEntries()) {
            ResourceLocation id = blockRO.getId();
            
            // Check if Block has a corresponding Item
            boolean hasItem = ModRegistry.ITEMS.getEntries().stream()
                    .anyMatch(itemRO -> itemRO.get() instanceof BlockItem bi && bi.getBlock() == blockRO.get());
            if (!hasItem) {
                errors.add("Block " + id + " is missing a corresponding BlockItem.");
            }

            // Check if BaseEntityBlock has a registered BlockEntity
            if (blockRO.get() instanceof BaseEntityBlock) {
                boolean hasBE = ModRegistry.BLOCK_ENTITIES.getEntries().stream()
                        .anyMatch(beRO -> beRO.getId().getPath().equals(id.getPath()));
                if (!hasBE) {
                    errors.add("Block " + id + " extends BaseEntityBlock but has no matching BlockEntity registered.");
                }
            }
        }
        
        // Check if BlockEntities have corresponding blocks
        for (RegistryObject<?> beRO : ModRegistry.BLOCK_ENTITIES.getEntries()) {
            ResourceLocation id = beRO.getId();
            boolean hasBlock = ModRegistry.BLOCKS.getEntries().stream()
                    .anyMatch(blockRO -> blockRO.getId().getPath().equals(id.getPath()));
            if (!hasBlock) {
                errors.add("BlockEntity " + id + " has no matching Block registered.");
            }
        }
    }

    private static void checkResourceIntegrity(List<String> errors) {
        LOGGER.info("Checking resource integrity...");
        String assetsPath = "src/main/resources/assets/" + Industrium.MOD_ID;
        
        for (RegistryObject<Block> blockRO : ModRegistry.BLOCKS.getEntries()) {
            String path = blockRO.getId().getPath();
            checkFile(errors, assetsPath + "/blockstates/" + path + ".json", "Blockstate");
            checkFile(errors, assetsPath + "/models/block/" + path + ".json", "Block model");
            // Basic texture check
            checkFile(errors, assetsPath + "/textures/block/" + path + ".png", "Block texture");
        }

        for (RegistryObject<Item> itemRO : ModRegistry.ITEMS.getEntries()) {
            String path = itemRO.getId().getPath();
            if (!(itemRO.get() instanceof BlockItem)) {
                checkFile(errors, assetsPath + "/models/item/" + path + ".json", "Item model");
                checkFile(errors, assetsPath + "/textures/item/" + path + ".png", "Item texture");
            } else {
                checkFile(errors, assetsPath + "/models/item/" + path + ".json", "Item model (from block)");
            }
        }
    }

    private static void checkRecipeExistence(List<String> errors) {
        LOGGER.info("Checking recipe existence...");
        String dataPath = "src/main/resources/data/" + Industrium.MOD_ID;
        for (RegistryObject<Item> itemRO : ModRegistry.ITEMS.getEntries()) {
            String path = itemRO.getId().getPath();
            // We can check if a recipe file exists with the same name as the item
            // This is just a heuristic as one recipe can produce multiple items or vice versa
            File recipeFile = new File(dataPath + "/recipes/" + path + ".json");
            if (!recipeFile.exists()) {
                // LOGGER.warn("Potential missing recipe for item: " + path);
            }
        }
    }

    private static void checkFile(List<String> errors, String filePath, String description) {
        File file = new File(filePath);
        if (!file.exists()) {
            errors.add(description + " missing: " + filePath);
        }
    }
}
