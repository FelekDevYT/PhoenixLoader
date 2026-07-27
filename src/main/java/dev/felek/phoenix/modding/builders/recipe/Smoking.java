package dev.felek.phoenix.modding.builders.recipe;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.resources.Identifier;

import java.nio.charset.StandardCharsets;

/**
 * @className: Cmoking
 * @author: Felek
 * @date: 27.07.2026 14:49
 */

public class Smoking {
    private float exp = 0.05f;
    private String ingredient;
    private String result;
    private int resultCount = 1;
    private int cookingTime = 200;
    private String group;
    private String category = "misc";
    private String id;

    public Smoking(String id) {
        this.id = id;
    }

    public Smoking exp(float exp) {
        this.exp = exp;
        return this;
    }

    public Smoking ingredient(String ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public Smoking cookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }

    public Smoking group(String group) {
        this.group = group;
        return this;
    }

    public Smoking result(String result) {
        this.result = result;
        return this;
    }

    public Smoking resultCount(int resultCount) {
        this.resultCount = resultCount;
        return this;
    }

    public Smoking category(String category) {
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
                    "type": "minecraft:smoking",
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
