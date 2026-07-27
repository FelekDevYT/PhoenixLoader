package dev.felek.phoenix.modding.builders;

import dev.felek.phoenix.modding.builders.recipe.FurnaceBase;
import dev.felek.phoenix.modding.builders.recipe.Shaped;
import dev.felek.phoenix.modding.builders.recipe.Shapeless;

/**
 * @className: RecipeBuilder
 * @author: Felek
 * @date: 27.07.2026 13:28
 */

public class RecipeBuilder {
    public static Shaped shaped(String id) {
        return new Shaped(id);
    }

    public static Shapeless shapeless(String id) {
        return new Shapeless(id);
    }

    public static FurnaceBase smelting(String id) {
        return new FurnaceBase(id, "minecraft:smelting");
    }

    public static FurnaceBase blasting(String id) {
        return new FurnaceBase(id, "minecraft::blasting");
    }

    public static FurnaceBase smoking(String id) {
        return new FurnaceBase(id, "minecraft:smoking");
    }

    public static FurnaceBase cooking(String id) {
        return new FurnaceBase(id, "minecraft:campfire_cooking");
    }
}
