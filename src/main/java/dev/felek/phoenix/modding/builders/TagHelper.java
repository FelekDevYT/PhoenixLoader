package dev.felek.phoenix.modding.builders;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.resources.Identifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @className: TagHelper
 * @author: Felek
 * @date: 26.07.2026 14:51
 */

public class TagHelper {
    private static final Map<Identifier, Set<String>> TAG_CACHE = new HashMap<>();

    public static void addBlockToTag(String tagNamespace, String tagPath, String blockId) {
        Identifier tagId1 = Identifier.fromNamespaceAndPath(tagNamespace, "tags/block/" + tagPath + ".json");
        Identifier tagId2 = Identifier.fromNamespaceAndPath(tagNamespace, "tags/blocks/" + tagPath + ".json");

        addValueToTag(tagId1, blockId);
        addValueToTag(tagId2, blockId);
    }

    public static void addItemToTag(String tagNamespace, String tagPath, String itemId) {
        Identifier tagId1 = Identifier.fromNamespaceAndPath(tagNamespace, "tags/item/" + tagPath + ".json");
        Identifier tagId2 = Identifier.fromNamespaceAndPath(tagNamespace, "tags/items/" + tagPath + ".json");

        addValueToTag(tagId1, itemId);
        addValueToTag(tagId2, itemId);
    }


    private static void addValueToTag(Identifier tagId, String value) {
        Set<String> vals = TAG_CACHE.computeIfAbsent(tagId, _ -> new LinkedHashSet<>());
        vals.add(value);

        JSONObject json = new JSONObject();
        json.put("replace", false);
        JSONArray valuesArray = new JSONArray();
        vals.forEach(valuesArray::put);
        json.put("values", valuesArray);
        PhoenixResources.INSTANCE.addAsset(tagId, json.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static void mineableWithPickaxe(String blockId) {
        addBlockToTag("minecraft", "mineable/pickaxe", blockId);
    }

    public static void mineableWithAxe(String blockId) {
        addBlockToTag("minecraft", "mineable/axe", blockId);
    }

    public static void mineableWithShovel(String blockId) {
        addBlockToTag("minecraft", "mineable/shovel", blockId);
    }

    public static void mineableWithHoe(String blockId) {
        addBlockToTag("minecraft", "mineable/hoe", blockId);
    }

    public static void needsStoneTool(String blockId) {
        addBlockToTag("minecraft", "needs_stone_tool", blockId);
    }

    public static void needsIronTool(String blockId) {
        addBlockToTag("minecraft", "needs_iron_tool", blockId);
    }

    public static void needsDiamondTool(String blockId) {
        addBlockToTag("minecraft", "needs_diamond_tool", blockId);
    }
}
