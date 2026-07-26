package dev.felek.phoenix.modding.api;

import dev.felek.phoenix.Phoenix;
import dev.felek.phoenix.modding.managers.Block;
import dev.felek.phoenix.modding.managers.Item;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;

/**
 * @className: PhoenixMC
 * @author: Felek
 * @date: 25.07.2026 18:57
 */

public class PhoenixMC {
    public static void registerItem(String id, Item item) {
        if (id.split(":").length != 2) {
            return;//we cant register without namespace
        }

        Phoenix.getManager().itemManager.registerItem(item);
        System.out.println("Registered item %s.".formatted(id));
    }

    public static void registerBlock(String id, Block block) {
        if (id.split(":").length != 2) {
            throw new RuntimeException("Invalid block ID.");
        }

        Phoenix.getManager().blockManager.registerBlock(block);
    }
}
