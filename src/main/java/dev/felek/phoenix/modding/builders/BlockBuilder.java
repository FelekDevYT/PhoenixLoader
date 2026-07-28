package dev.felek.phoenix.modding.builders;

import dev.felek.phoenix.modding.api.PhoenixMC;
import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @className: BlockBuilder
 * @author: Felek
 * @date: 26.07.2026 13:25
 */

public class BlockBuilder {
    private String id;
    private BlockBehaviour.Properties props;
    private String modName = "exampleMod";
    private String textureName;

    public BlockBuilder(String id) {
        this.id = id;

        Identifier identifier = Identifier.fromNamespaceAndPath(id.split(":")[0], id.split(":")[1]);
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, identifier);

        this.props = BlockBehaviour.Properties.of().setId(key);
    }

    public static BlockBuilder create(String id) {
        return new BlockBuilder(id);
    }

    public BlockBuilder hardnessAndResistance(float hardness, float resistance) {
        props.destroyTime(hardness).explosionResistance(resistance);
        return this;
    }

    public BlockBuilder sound(SoundType soundType) {
        props.sound(soundType);
        return this;
    }

    public BlockBuilder requiresCorrectToolForDrops() {
        props.requiresCorrectToolForDrops();
        return this;
    }

    public BlockBuilder setTextureName(String textureName) {
        this.textureName = textureName;
        return this;
    }

    public BlockBuilder setModName(String modName) {
        this.modName = modName;
        return this;
    }

    public dev.felek.phoenix.modding.managers.block.Block buildAndRegister() {
        if (textureName != null) {
            try {
                String clean = textureName.endsWith(".png") ? textureName : textureName + ".png";
                File texture = new File("mods/" + modName + "/assets/textures/blocks/" + clean);

                if (!texture.exists()) {
                    throw new RuntimeException(clean);
                }

                byte[] bytes = Files.readAllBytes(texture.toPath());
                String noExt = clean.replace(".png", "");

                Identifier identifier = Identifier.fromNamespaceAndPath(id.split(":")[0], "textures/block/" + noExt + ".png");
                PhoenixResources.INSTANCE.addAsset(identifier, bytes);

                String blockState = """
                            {
                              "variants": {
                                "": {
                                  "model": "%s:block/%s"
                                }
                              }
                            }
                            """.formatted(id.split(":")[0], id.split(":")[1]);
                String model = """
                            {
                              "parent": "minecraft:block/cube_all",
                              "textures": {
                                "all": "%s:block/%s"
                              }
                            }
                            """.formatted(id.split(":")[0], noExt);
                String ii = """
                            {
                              "model": {
                                "type": "minecraft:model",
                                "model": "%s:block/%s"
                              }
                            }
                            """.formatted(id.split(":")[0], id.split(":")[1]);
                String itemMdel = """
                            {
                              "parent": "%s:block/%s"
                            }
                            """.formatted(id.split(":")[0], id.split(":")[1]);

                Identifier bsId = Identifier.fromNamespaceAndPath(id.split(":")[0], "blockstates/" + id.split(":")[1] + ".json");
                Identifier bmId = Identifier.fromNamespaceAndPath(id.split(":")[0], "models/block/" + id.split(":")[1] + ".json");
                Identifier iiId = Identifier.fromNamespaceAndPath(id.split(":")[0], "items/" + id.split(":")[1] + ".json");
                Identifier imId = Identifier.fromNamespaceAndPath(id.split(":")[0], "models/item/" + id.split(":")[1] + ".json");

                PhoenixResources.INSTANCE.addAsset(bsId, blockState.getBytes(StandardCharsets.UTF_8));
                PhoenixResources.INSTANCE.addAsset(bmId, model.getBytes(StandardCharsets.UTF_8));
                PhoenixResources.INSTANCE.addAsset(iiId, ii.getBytes(StandardCharsets.UTF_8));
                PhoenixResources.INSTANCE.addAsset(imId, itemMdel.getBytes(StandardCharsets.UTF_8));
            } catch (Exception _) {
                throw new RuntimeException("EROR WHIILE LOADING BLOCK; BLOCK TEXTURE NOT FOUND");
            }
        }

        Block mcb = new Block(props);

        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(id.split(":")[0], id.split(":")[1]));
        Item.Properties props = new Item.Properties().setId(key);
        BlockItem bi = new BlockItem(mcb, props);

        dev.felek.phoenix.modding.managers.block.Block phBlock = new dev.felek.phoenix.modding.managers.block.Block(id, mcb, bi);
        LootTableBuilder.dropSelf(id);
        PhoenixMC.registerBlock(id, phBlock);
        return phBlock;
    }
}
