package dev.felek.phoenix.modding.builders.recipe;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.resources.Identifier;

import java.nio.charset.StandardCharsets;

/**
 * @className: Cooking
 * @author: Felek
 * @date: 27.07.2026 14:51
 */

public class Cooking {
    private float exp = 0.05f;
    private String ingredient;
    private String result;
    private int resultCount = 1;
    private int cookingTime = 200;
    private String group;
    private String category = "misc";
    private String id;

    public Cooking(String id) {
        this.id = id;
    }

    public Cooking exp(float exp) {
        this.exp = exp;
        return this;
    }

    public Cooking ingredient(String ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public Cooking cookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }

    public Cooking group(String group) {
        this.group = group;
        return this;
    }

    public Cooking result(String result) {
        this.result = result;
        return this;
    }

    public Cooking resultCount(int resultCount) {
        this.resultCount = resultCount;
        return this;
    }

    public Cooking category(String category) {
        this.category = category;
        return this;
    }

    public void register() {
        if (ingredient == null || result == null) {
            throw new RuntimeException("Illegal recipe declaration.");
        }

        String extra = group == null ? "" : ",\n\r\r\r\"group\": \"%s\"".formatted(group);

        String json = """
                {
                    "type": "minecraft:campfire_cooking",
                    "category": "%s",
                    "cookingtime": %d,
                    "experience": %f,
                    "ingredient": "%s",
                    "result": {
                        "id": "%s",
                        "count": %d
                    }%s
                }
                """.formatted(category, cookingTime, exp, ingredient, result, resultCount, extra);

        Identifier identifier = Identifier.fromNamespaceAndPath(id.split(":")[0], "recipes/" + id.split(":")[1] + ".json");
        PhoenixResources.INSTANCE.addAsset(identifier, json.getBytes(StandardCharsets.UTF_8));
    }
}
