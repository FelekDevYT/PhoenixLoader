package dev.felek.phoenix.modding.builders;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootTable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @className: LootTableBuilder
 * @author: Felek
 * @date: 26.07.2026 14:37
 */

public class LootTableBuilder {
    public static void dropSelf(String id) {
        dropItem(id, id);
    }

    public static void addToLoot(String lTableName, String item, float chance) {
        Identifier id = Identifier.fromNamespaceAndPath(lTableName.split(":")[0], "loot_table/" + lTableName.split(":")[1] + ".json");

        JSONObject table;
        byte[] exsist = PhoenixResources.INSTANCE.getAsset(id);
        if (exsist != null) {
            table = new JSONObject(new String(exsist, StandardCharsets.UTF_8));
        } else {
            try (InputStream is = LootTable.class.getClassLoader().getResourceAsStream("data/" + lTableName.split(":")[0] + "/loot_table/" + lTableName.split(":")[1] + ".json")) {
                if (is != null) {
                    table = new JSONObject(new String(is.readAllBytes(), StandardCharsets.UTF_8));
                } else {
                    table = new JSONObject();
                    table.put("type", "minecraft:chest");
                    table.put("pools", new JSONArray());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        JSONArray pools = table.getJSONArray("pools");
        if (pools == null) {
            pools = new JSONArray();
            table.put("pools", pools);
        }

        JSONObject condition = new JSONObject();
        condition.put("condition", "minecraft:random_chance");
        condition.put("chance", chance);

        JSONObject entry = new JSONObject();
        entry.put("type", "minecraft:item");
        entry.put("name", item);
        entry.put("conditions", new JSONArray().put(condition));

        JSONObject pool = new JSONObject();
        pool.put("rolls", 1);
        pool.put("entries", new JSONArray().put(entry));

        pools.put(pool);

        PhoenixResources.INSTANCE.addAsset(id, table.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static void dropItem(String block, String item) {
        String json = """
                {
                  "type": "minecraft:block",
                  "pools": [
                    {
                      "bonus_rolls": 0.0,
                      "conditions": [
                        {
                          "condition": "minecraft:survives_explosion"
                        }
                      ],
                      "entries": [
                        {
                          "type": "minecraft:item",
                          "name": "%s"
                        }
                      ],
                      "rolls": 1.0
                    }
                  ]
                }
                """.formatted(item);

        Identifier id1 = Identifier.fromNamespaceAndPath(block.split(":")[0], "loot_table/blocks/" + block.split(":")[1] + ".json");
        Identifier id2 = Identifier.fromNamespaceAndPath(block.split(":")[0], "loot_tables/blocks/" + block.split(":")[1] + ".json");
        PhoenixResources.INSTANCE.addAsset(id1, json.getBytes(StandardCharsets.UTF_8));
        PhoenixResources.INSTANCE.addAsset(id2, json.getBytes(StandardCharsets.UTF_8));
    }
}
