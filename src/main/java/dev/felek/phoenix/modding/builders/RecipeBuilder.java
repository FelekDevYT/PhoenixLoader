package dev.felek.phoenix.modding.builders;

import dev.felek.phoenix.modding.builders.recipe.Blasting;
import dev.felek.phoenix.modding.builders.recipe.Cooking;
import dev.felek.phoenix.modding.builders.recipe.Shaped;
import dev.felek.phoenix.modding.builders.recipe.Shapeless;
import dev.felek.phoenix.modding.builders.recipe.Smelting;
import dev.felek.phoenix.modding.builders.recipe.Smoking;

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

    public static Smelting smelting(String id) {
        return new Smelting(id);
    }

    public static Blasting blasting(String id) {
        return new Blasting(id);
    }

    public static Smoking smoking(String id) {
        return new Smoking(id);
    }

    public static Cooking cooking(String id) {
        return new Cooking(id);
    }
}
