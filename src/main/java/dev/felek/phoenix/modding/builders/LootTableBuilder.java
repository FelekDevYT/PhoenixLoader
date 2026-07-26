package dev.felek.phoenix.modding.builders;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.resources.Identifier;

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
