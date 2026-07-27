package dev.felek.phoenix.modding.builders.recipe;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: Shaped
 * @author: Felek
 * @date: 27.07.2026 13:29
 */

public class Shaped {
    private String id;
    private Map<String, String> keys = new HashMap<>();
    private String[] pattern;
    private String resultId;
    private int resultCount = 1;
    private String group;
    private String category = "misc";

    public Shaped(String id) {
        this.id = id;
    }

    public Shaped pattern(String... rows) {
        this.pattern = rows;
        return this;
    }

    public Shaped define(String key, String id) {
        keys.put(key, id);
        return this;
    }

    public Shaped result(String itemId, int count) {
        this.resultId = itemId;
        this.resultCount = count;
        return this;
    }

    public Shaped category(String category) {
        this.category = category;
        return this;
    }

    public Shaped group(String group) {
        this.group = group;
        return this;
    }

    public void register() {
        if (pattern == null || pattern.length == 0 || resultId == null) {
            throw new RuntimeException("Illegal recipe declaration.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        int c = 0;
        for (Map.Entry<String, String> e : keys.entrySet()) {
            sb.append("    \"%s\": \"%s\"".formatted(e.getKey(), e.getValue()));
            if (c < keys.size() - 1) sb.append(",");
            sb.append("\n");
            c++;
        }
        sb.append("  }");

        String json = getJson(sb);
        Identifier identifier = Identifier.fromNamespaceAndPath(id.split(":")[0], "recipes/" + id.split(":")[1] + ".json");
        PhoenixResources.INSTANCE.addAsset(identifier, json.getBytes(StandardCharsets.UTF_8));
    }

    @NotNull
    private String getJson(StringBuilder sb) {
        StringBuilder patternJSON = new StringBuilder("[");
        for (int i = 0; i < pattern.length; i++) {
            patternJSON.append("\"%s\"".formatted(pattern[i]));
            if (i < pattern.length - 1) patternJSON.append(",");
        }

        patternJSON.append("]");
        String extraGroup = group == null ? "" : ",\n    \"group\": \"%s\"".formatted(group);
        String json = """
        {
            "type": "minecraft:crafting_shaped",
            "category": "%s",
            "key": %s,
            "pattern": %s,
            "result": {
                "id": "%s",
                "count": %d
            }%s
        }
        """.formatted(category, sb.toString(), patternJSON, resultId, resultCount, extraGroup);
        return json;
    }
}
