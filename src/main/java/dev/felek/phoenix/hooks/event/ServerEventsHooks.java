package dev.felek.phoenix.hooks.event;

import dev.felek.phoenix.Phoenix;
import net.bytebuddy.asm.Advice;

/**
 * @className: ServerEventsHooks
 * @author: Felek
 * @date: 27.07.2026 18:59
 */

public class ServerEventsHooks {
    public static class ServerStartingHook {
        @Advice.OnMethodEnter
        public static void onEnter(@Advice.This Object server) {
            Phoenix.getManager().fireEvent("onServerStarting", server);
        }
    }

    public static class ServerStoppingHook {
        @Advice.OnMethodEnter
        public static void onEnter(@Advice.This Object server) {
            Phoenix.getManager().fireEvent("onServerStopping", server);
        }
    }

    public static class ServerTickHook {
        @Advice.OnMethodExit
        public static void onExit(@Advice.This Object server) {
            Phoenix.getManager().fireEvent("onServerTick", server);
        }
    }
}
