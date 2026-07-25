package dev.felek.phoenix.hooks;

import dev.felek.phoenix.Phoenix;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.minecraft.client.Minecraft;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @className: TickHook
 * @author: Felek
 * @date: 25.07.2026 18:15
 */

public class TickHook {
    public static void intercept(@SuperCall Callable<?> original, @Origin Method method) throws Exception {
        Phoenix.getManager().onTick(Minecraft.getInstance());

        original.call();
    }
}
