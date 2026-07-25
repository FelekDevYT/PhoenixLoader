package dev.felek.phoenix.hooks;

import dev.felek.phoenix.Phoenix;
import dev.felek.phoenix.modding.utils.RegistryUtils;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.minecraft.core.registries.BuiltInRegistries;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @className: BootstrapHook
 * @author: Felek
 * @date: 25.07.2026 18:41
 */

public class BootstrapHook {
    public static void intercept(@SuperCall Callable<?> original, @Origin Method method) throws Exception {
        original.call();

        System.out.println("Unfreezing registries...");
        RegistryUtils.unfreeze(BuiltInRegistries.ITEM);
        System.out.println("Unfreeze success!");

        System.out.println("Bootstrapping...");
        Phoenix.getManager().onItemRegister();
        System.out.println("Bootstrapping complete!");

        System.out.println("Refreezing registries...");
        RegistryUtils.refreeze(BuiltInRegistries.ITEM);
        System.out.println("Refreezing success!");
    }
}
