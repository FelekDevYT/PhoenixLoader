package dev.felek.phoenix.modding.builders;

import dev.felek.phoenix.modding.api.PhoenixMC;
import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @className: ItemBuilder
 * @author: Felek
 * @date: 25.07.2026 19:45
 */

public class ItemBuilder {
    private String id;
    private Item.Properties props;
    private String modName = "exampleMod";
    private String textureName;

    public ItemBuilder(String id) {
        this.id = id;

        Identifier identifier = Identifier.fromNamespaceAndPath(id.split(":")[0], id.split(":")[1]);
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, identifier);

        this.props = new Item.Properties().setId(key);
    }

    public static ItemBuilder create(String id) {
        return new ItemBuilder(id);
    }

    public ItemBuilder maxStackSize(int count) {
        props.stacksTo(count);
        return this;
    }

    public ItemBuilder durability(int maxDamage) {
        props.durability(maxDamage);
        return this;
    }

    public ItemBuilder fireResistant() {
        props.fireResistant();
        return this;
    }

    public ItemBuilder setTextureName(String textureName) {
        this.textureName = textureName;
        return this;
    }

    public ItemBuilder setModName(String modName) {
        this.modName = modName;
        return this;
    }

    public dev.felek.phoenix.modding.managers.item.Item buildAndRegister() {
        if (textureName != null) {
            try {
                String clean = textureName.endsWith(".png") ? textureName : textureName + ".png";
                File texture = new File("mods/" + modName + "/assets/textures/items/" + clean);

                if (texture.exists()) {
                    byte[] bytes = Files.readAllBytes(texture.toPath());
                    String noExt = clean.replace(".png", "");

                    Identifier identifier = Identifier.fromNamespaceAndPath(id.split(":")[0], "textures/item/" + noExt + ".png");
                    PhoenixResources.INSTANCE.addAsset(identifier, bytes);

                    String json = """
                            {
                              "parent": "minecraft:item/generated",
                              "textures": {
                                "layer0": "%s:item/%s"
                              }
                            }
                            """.formatted(id.split(":")[0], noExt);

                    String itemInfo = """
                            {
                              "model": {
                                "type": "minecraft:model",
                                "model": "%s:item/%s"
                              }
                            }
                            """.formatted(id.split(":")[0], id.split(":")[1]);

                    //i hate this stuff
                    Identifier modelId1 = Identifier.fromNamespaceAndPath(id.split(":")[0], "models/item/" + id.split(":")[1] + ".json");
                    Identifier modelId2 = Identifier.fromNamespaceAndPath(id.split(":")[0], "items/" + id.split(":")[1] + ".json");

                    PhoenixResources.INSTANCE.addAsset(modelId1, json.getBytes(StandardCharsets.UTF_8));
                    PhoenixResources.INSTANCE.addAsset(modelId2, itemInfo.getBytes(StandardCharsets.UTF_8));
                } else {
                    System.err.println("ERROR WHILE LOADING TEXTURE; TEXTURE DOES NOT EXISTS!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Item mc = new Item(props);
        dev.felek.phoenix.modding.managers.item.Item phoenix = new dev.felek.phoenix.modding.managers.item.Item(id, mc);

        PhoenixMC.registerItem(id, phoenix);
        return phoenix;
    }
}