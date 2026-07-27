package dev.felek.phoenix.modding.builders.recipe;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: Shapeless
 * @author: Felek
 * @date: 27.07.2026 13:51
 */

public class Shapeless {
    private String id;
    private List<String> ingreds = new ArrayList<>();
    private String result;
    private int resultC;
    private String group;
    private String category = "misc";

    public Shapeless(String id) {
        this.id = id;
    }

    public Shapeless define(String itemId) {
        ingreds.add(itemId);
        return this;
    }

    public Shapeless result(String itemId, int count) {
        this.result = itemId;
        this.resultC = count;
        return this;
    }

    public Shapeless category(String category) {
        this.category = category;
        return this;
    }

    public Shapeless group(String group) {
        this.group = group;
        return this;
    }

    public void register() {
        if (ingreds.isEmpty() || result == null) {
            throw new RuntimeException("Illegal recipe declaration.");
        }

        StringBuilder ingredientsJson = new StringBuilder("[");
        for (int i = 0; i < ingreds.size(); i++) {
            ingredientsJson.append("\"%s\"".formatted(ingreds.get(i)));
            if (i < ingreds.size() - 1) ingredientsJson.append(",");
        }
        ingredientsJson.append("]");

        String json = getJson(ingredientsJson);
        Identifier identifier = Identifier.fromNamespaceAndPath(id.split(":")[0], "recipes/" + id.split(":")[1] + ".json");
        PhoenixResources.INSTANCE.addAsset(identifier, json.getBytes(StandardCharsets.UTF_8));
    }

    @NotNull
    private String getJson(StringBuilder ingredientsJson) {
        String extrGrop = group == null ? "" : ",\n    \"group\": \"%s\"".formatted(group);

        String json = """
                {
                    "type": "minecraft:crafting_shapeless",
                    "category": "%s",
                    "ingredients": %s,
                    "result": {
                        "id": "%s",
                        "count": %d
                    }%s
                }
                """.formatted(category, ingredientsJson, result, resultC, extrGrop);
        return json;
    }
}
