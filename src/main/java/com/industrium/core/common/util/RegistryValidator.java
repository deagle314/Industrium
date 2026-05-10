package com.industrium.core.common.util;

import com.industrium.core.Industrium;
import com.industrium.core.common.registry.ModBlocks;
import com.industrium.core.common.registry.ModItems;
import com.industrium.core.common.registry.ModBlockEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
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
        for (RegistryObject<Block> blockRO : ModBlocks.BLOCKS.getEntries()) {
            ResourceLocation id = blockRO.getId();
            boolean hasItem = ModItems.ITEMS.getEntries().stream()
                    .anyMatch(itemRO -> itemRO.get() instanceof BlockItem bi && bi.getBlock() == blockRO.get());
            if (!hasItem) {
                errors.add("Block " + id + " is missing a corresponding BlockItem.");
            }

            if (blockRO.get() instanceof EntityBlock) {
                boolean hasBE = ModBlockEntities.BLOCK_ENTITIES.getEntries().stream()
                        .anyMatch(beRO -> beRO.getId().getPath().equals(id.getPath()));
                if (!hasBE) {
                    errors.add("Block " + id + " implements EntityBlock but has no matching BlockEntity registered.");
                }
            }
        }
    }

    private static void checkResourceIntegrity(List<String> errors) {
        LOGGER.info("Checking resource integrity...");
        String assetsPath = "src/main/resources/assets/" + Industrium.MOD_ID;
        
        for (RegistryObject<Block> blockRO : ModBlocks.BLOCKS.getEntries()) {
            String path = blockRO.getId().getPath();
            checkFile(errors, assetsPath + "/blockstates/" + path + ".json", "Blockstate");
            checkFile(errors, assetsPath + "/models/block/" + path + ".json", "Block model");
            // Check for at least one texture
            checkFile(errors, assetsPath + "/textures/block/" + path + ".png", "Block texture");
        }

        for (RegistryObject<Item> itemRO : ModItems.ITEMS.getEntries()) {
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
        for (RegistryObject<Item> itemRO : ModItems.ITEMS.getEntries()) {
            String path = itemRO.getId().getPath();
            // Not all items MUST have recipes, but for this mod it's a good check
            checkFile(errors, dataPath + "/recipes/" + path + ".json", "Recipe");
        }
    }

    private static void checkFile(List<String> errors, String filePath, String description) {
        File file = new File(filePath);
        if (!file.exists()) {
            // We only add as error if we are sure it should exist.
            // Some things might be optional or handled differently.
            errors.add(description + " missing: " + filePath);
        }
    }
}
