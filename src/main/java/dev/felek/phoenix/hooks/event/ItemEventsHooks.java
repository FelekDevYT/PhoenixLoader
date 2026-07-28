package dev.felek.phoenix.hooks.event;

import dev.felek.phoenix.Phoenix;
import net.bytebuddy.asm.Advice;

/**
 * @className: ItemEvensHooks
 * @author: Felek
 * @date: 27.07.2026 19:04
 */

public class ItemEventsHooks {
    public static class ItemUseHook {
        @Advice.OnMethodEnter
        public static void onEnter(@Advice.Argument(0) Object player, @Advice.Argument(1) Object level, @Advice.Argument(2) Object hand) {
            Phoenix.getManager().fireEvent("onItemUse", player, level, hand);
        }
    }
}
