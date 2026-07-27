package dev.felek.phoenix.modding.builders;

import dev.felek.phoenix.modding.builders.recipe.Shaped;

/**
 * @className: RecipeBuilder
 * @author: Felek
 * @date: 27.07.2026 13:28
 */

public class RecipeBuilder {
    public static Shaped shaped(String id) {
        return new Shaped(id);
    }
}
