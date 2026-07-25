package dev.felek.phoenix.hooks;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

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

        original.call();

        System.out.println("GAME CLOSED");
    }
}
