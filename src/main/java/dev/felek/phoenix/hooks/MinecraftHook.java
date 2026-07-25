package dev.felek.phoenix.hooks;

import dev.felek.phoenix.Phoenix;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.minecraft.client.Minecraft;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @className: MinecraftHook
 * @author: Felek
 * @date: 25.07.2026 13:43
 */

public class MinecraftHook {
    public static void intercept(@SuperCall Callable<?> original, @Origin Method method) throws Exception {
        System.out.println("GAME STARTED");

        System.out.println("Initializing mods...");
        Phoenix.getManager().loadMods();
        System.out.println("Mods initialized.");

        System.out.println("Enabling mods");
        Phoenix.getManager().onEnable(Minecraft.getInstance());
        System.out.println("All mods successfully enabled");

        original.call();

        System.out.println("Disabling mods");
        Phoenix.getManager().onDisable(Minecraft.getInstance());
        System.out.println("All mods successfully disabled.");

        System.out.println("GAME CLOSED");
    }
}
