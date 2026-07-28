package dev.felek.phoenix.modding.managers.tab;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: CreativeTabManager
 * @author: Felek
 * @date: 28.07.2026 15:08
 */

public class CreativeTabManager {
    private Map<String, List<Item>> items = new HashMap<>();

    public void addToTab(String id, Item item) {
        items.computeIfAbsent(id, k -> new ArrayList<>()).add(item);
    }

    public List<Item> getItemsForTab(String id) {
        return items.getOrDefault(id, List.of());
    }
}