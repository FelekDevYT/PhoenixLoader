package dev.felek.phoenix.hooks.event;

import dev.felek.phoenix.Phoenix;
import net.bytebuddy.asm.Advice;

/**
 * @className: BlockEventsHooks
 * @author: Felek
 * @date: 27.07.2026 19:03
 */

public class BlockEventsHooks {
    public static class BlockBreakHook {
        @Advice.OnMethodExit
        public static void onExit(@Advice.This Object gameMode, @Advice.Argument(0) Object pos, @Advice.Return boolean success) {
            if (success) {
                Phoenix.getManager().fireEvent("onBlockBreak", gameMode, pos);
            }
        }
    }
}
