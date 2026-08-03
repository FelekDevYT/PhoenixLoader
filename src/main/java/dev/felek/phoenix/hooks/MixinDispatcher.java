package dev.felek.phoenix.hooks;

import dev.felek.phoenix.modding.managers.mixin.MixinAction;
import dev.felek.phoenix.modding.managers.mixin.MixinManager;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @className: MixinDispatcher
 * @author: Felek
 * @date: 02.08.2026 19:10
 */

public class MixinDispatcher {
    @Advice.OnMethodEnter
    public static void intercept(@Advice.Origin Method origin) throws Exception {
        List<MixinAction> injs = MixinManager.getInjections(origin.getDeclaringClass().getName() + "#" + origin.getName());
        if (injs != null) {
            for (MixinAction act : injs) {
                act.execute();
            }
        }
    }
}
