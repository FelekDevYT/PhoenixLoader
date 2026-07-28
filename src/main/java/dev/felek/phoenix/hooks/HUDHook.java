package dev.felek.phoenix.hooks;

import dev.felek.phoenix.Phoenix;
import dev.felek.phoenix.modding.api.gui.HUD;
import net.bytebuddy.asm.Advice;
import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * @className: HUDHook
 * @author: Felek
 * @date: 28.07.2026 13:15
 */

public class HUDHook {
    @Advice.OnMethodExit
    public static void onExit(@Advice.Argument(0) GuiGraphicsExtractor extractor) {
        if (extractor != null) {
            HUD hud = new HUD(extractor);
            Phoenix.getManager().fireEvent("onRenderHUD", hud);
        }
    }
}
