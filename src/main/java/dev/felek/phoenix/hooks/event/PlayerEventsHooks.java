package dev.felek.phoenix.hooks.event;

import dev.felek.phoenix.Phoenix;
import net.bytebuddy.asm.Advice;

/**
 * @className: PlayerEventsHooks
 * @author: Felek
 * @date: 27.07.2026 19:03
 */

public class PlayerEventsHooks {
    public static class PlayerJoinHook {
        @Advice.OnMethodExit
        public static void onExit(@Advice.Argument(1) Object player) {
            Phoenix.getManager().fireEvent("onPlayerJoin", player);
        }
    }

    public static class PlayerLeaveHook {
        @Advice.OnMethodEnter
        public static void onEnter(@Advice.Argument(0) Object player) {
            Phoenix.getManager().fireEvent("onPlayerLeave", player);
        }
    }

    public static class PlayerChatHook {
        @Advice.OnMethodEnter
        public static void onEnter(@Advice.Argument(0) Object player, @Advice.Argument(1) Object message) {
            Phoenix.getManager().fireEvent("onPlayerChat", player, message);
        }
    }
}
