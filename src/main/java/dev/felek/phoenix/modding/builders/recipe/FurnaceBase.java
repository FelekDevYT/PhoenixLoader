package dev.felek.phoenix.modding.builders.recipe;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.resources.Identifier;

import java.nio.charset.StandardCharsets;

/**
 * @className: FurnaceBase
 * @author: Felek
 * @date: 27.07.2026 15:11
 */

public class FurnaceBase {
    private float exp = 0.05f;
    private String ingredient;
    private String result;
    private int resultCount = 1;
    private int cookingTime = 200;
    private String group;
    private String category = "misc";
    private String id;
    private String type;

    public FurnaceBase(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public FurnaceBase exp(float exp) {
        this.exp = exp;
        return this;
    }

    public FurnaceBase ingredient(String ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public FurnaceBase cookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }

    public FurnaceBase group(String group) {
        this.group = group;
        return this;
    }

    public FurnaceBase result(String result) {
        this.result = result;
        return this;
    }

    public FurnaceBase resultCount(int resultCount) {
        this.resultCount = resultCount;
        return this;
    }

    public FurnaceBase category(String category) {
        this.category = category;
        return this;
    }

    public void register() {
        if (ingredient == null || result == null) {
            throw new RuntimeException("Illegal recipe declaration.");
        }

        String extra = group == null ? "" : ",\n\t\t\t\"group\": \"%s\"".formatted(group);

        String json = """
                {
                    "type": "%s",
                    "category": "%s",
                    "cookingtime": %d,
                    "experience": %f,
                    "ingredient": "%s",
                    "result": {
                        "id": "%s",
                        "count": %d
                    }%s
                }
                """.formatted(type, category, cookingTime, exp, ingredient, result, resultCount, extra);

        Identifier identifier = Identifier.fromNamespaceAndPath(id.split(":")[0], "recipes/" + id.split(":")[1] + ".json");
        PhoenixResources.INSTANCE.addAsset(identifier, json.getBytes(StandardCharsets.UTF_8));
    }
}
