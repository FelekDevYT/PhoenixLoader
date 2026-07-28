package dev.felek.phoenix.modding.api;

import dev.felek.phoenix.Phoenix;
import dev.felek.phoenix.modding.managers.block.Block;
import dev.felek.phoenix.modding.managers.item.Item;
import dev.felek.phoenix.modding.managers.command.CommandExecutor;

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

    public static void registerCommand(String name, CommandExecutor executor) {
        Phoenix.getManager().commandManager.registerCommand(name, executor);
    }

    public static void addToTab(String tabId, Item item) {
        Phoenix.getManager().tabManager.addToTab(tabId, item.getItem());
    }

    public static void addToTab(String tabId, Block block) {
        Phoenix.getManager().tabManager.addToTab(tabId, block.getItem());
    }
}
