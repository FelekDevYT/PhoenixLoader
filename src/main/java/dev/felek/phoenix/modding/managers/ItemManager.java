package dev.felek.phoenix.modding.managers;

import dev.felek.phoenix.modding.Mod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: ItemManager
 * @author: Felek
 * @date: 25.07.2026 18:42
 */

public class ItemManager {
    private List<Item> registeredItems;

    public ItemManager() {
        registeredItems = new ArrayList<>();
    }

    public void registerItem(Item item) {
        registeredItems.add(item);
    }

    public void registerAll() {
        for (Item i : registeredItems) {
            Registry.register(
                    BuiltInRegistries.ITEM,
                    Identifier.fromNamespaceAndPath(i.getName().split(":")[0], i.getName().split(":")[1]),//todo: name VS id
                    i.getItem()
            );
        }
    }
}
