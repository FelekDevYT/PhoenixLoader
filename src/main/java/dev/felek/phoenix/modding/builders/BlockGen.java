package dev.felek.phoenix.modding.builders;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.resources.Identifier;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @className: BlockGen
 * @author: Felek
 * @date: 29.07.2026 17:20
 */

public class BlockGen {
    private final String id;
    private String oreBlockId;
    private int size = 8;
    private int veinsPerChunk = 6;
    private int minHeight = -64;
    private int maxHeight = 64;
    private final List<String> bims = new ArrayList<>();

    private BlockGen(String id) {
        this.id = id;
    }

    public static BlockGen create(String id) {
        return new BlockGen(id);
    }

    public BlockGen oreBlock(String oreBlockId) {
        this.oreBlockId = oreBlockId;
        return this;
    }

    public BlockGen veinSize(int size) {
        this.size = size;
        return this;
    }

    public BlockGen veinsPerChunk(int veinsPerChunk) {
        this.veinsPerChunk = veinsPerChunk;
        return this;
    }

    public BlockGen minHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    public BlockGen maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public BlockGen heightRange(int min, int max) {
        this.maxHeight = max;
        this.minHeight = min;
        return this;
    }

    public BlockGen biomes(String... bms) {
        bims.addAll(Arrays.asList(bms));
        return this;
    }

    public void register() {
        if (oreBlockId == null) {
            throw new RuntimeException("You should specify block id");
        }

        JSONObject feat = new JSONObject();
        feat.put("type", "minecraft:ore");

        JSONObject conf = new JSONObject();
        conf.put("size", size);
        conf.put("discard_chance_on_air_exposure", 0.0);

        JSONArray target = new JSONArray();
        target.put(oreTarget("minecraft:stone_ore_replaceables"));
        target.put(oreTarget("minecraft:deepslate_ore_replaceables"));
        conf.put("targets", target);
        feat.put("config", conf);

        Identifier configuredId = Identifier.fromNamespaceAndPath(this.id.split(":")[0], "worldgen/configured_feature/" + this.id.split(":")[1] + ".json");
        PhoenixResources.INSTANCE.addAsset(configuredId, feat.toString().getBytes(StandardCharsets.UTF_8));

        JSONObject placed = new JSONObject();
        placed.put("feature", this.id);

        JSONArray place = new JSONArray();
        JSONObject counter = new JSONObject();
        counter.put("type", "minecraft:count");
        counter.put("count", veinsPerChunk);
        place.put(counter);

        JSONObject sq = new JSONObject();
        sq.put("type", "minecraft:in_square");
        place.put(sq);

        JSONObject range = new JSONObject();
        range.put("type", "minecraft:height_range");
        JSONObject height = new JSONObject();
        height.put("type", "minecraft:uniform");
        JSONObject minAbs = new JSONObject();
        minAbs.put("absolute", minHeight);
        JSONObject maxAbs = new JSONObject();
        maxAbs.put("absolute", maxHeight);
        height.put("min_inclusive", minAbs);
        height.put("max_inclusive", maxAbs);
        range.put("height", height);
        place.put(range);

        JSONObject biomeStep = new JSONObject();
        biomeStep.put("type", "minecraft:biome");
        place.put(biomeStep);

        placed.put("placement", place);
        Identifier pid = Identifier.fromNamespaceAndPath(this.id.split(":")[0], "worldgen/placed_feature/" + this.id.split(":")[1] + ".json");
        PhoenixResources.INSTANCE.addAsset(pid, placed.toString().getBytes(StandardCharsets.UTF_8));

        for (String bId : bims) {
            addFeatureToBiome(bId, this.id);
        }
    }

    private JSONObject oreTarget(String tag) {
        JSONObject target = new JSONObject();
        JSONObject predicate = new JSONObject();
        predicate.put("predicate_type", "minecraft:tag_match");
        predicate.put("tag", tag);
        target.put("target", predicate);
        JSONObject state = new JSONObject();
        state.put("Name", oreBlockId);
        target.put("state", state);
        return target;
    }

    private static void addFeatureToBiome(String biome, String placedFeatureId) {
        String biomeNamespace = biome.split(":")[0];
        String biomePath = biome.split(":")[1];
        Identifier id = Identifier.fromNamespaceAndPath(biomeNamespace, "worldgen/biome/" + biomePath + ".json");

        JSONObject biomeJson;
        byte[] existing = PhoenixResources.INSTANCE.getAsset(id);
        if (existing != null) {
            biomeJson = new JSONObject(new String(existing, StandardCharsets.UTF_8));
        } else {
            try (InputStream is = net.minecraft.world.level.biome.Biome.class.getClassLoader()
                    .getResourceAsStream("data/" + biomeNamespace + "/worldgen/biome/" + biomePath + ".json")) {
                if (is == null) {
                    throw new RuntimeException("Biome not found: " + biome);
                }
                biomeJson = new JSONObject(new String(is.readAllBytes(), StandardCharsets.UTF_8));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        JSONArray feats = biomeJson.getJSONArray("features");
        JSONArray underground = feats.getJSONArray(6);
        underground.put(placedFeatureId);

        PhoenixResources.INSTANCE.addAsset(id, biomeJson.toString().getBytes(StandardCharsets.UTF_8));
    }
}
