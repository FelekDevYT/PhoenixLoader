package dev.felek.phoenix.hooks.event;

import dev.felek.phoenix.Phoenix;
import net.bytebuddy.asm.Advice;

/**
 * @className: EntityEventsHooks
 * @author: Felek
 * @date: 27.07.2026 19:04
 */

public class EntityEventsHooks {
    public static class EntityDeathHook {
        @Advice.OnMethodEnter
        public static void onEnter(@Advice.This Object entity, @Advice.Argument(0) Object damageSource) {
            Phoenix.getManager().fireEvent("onEntityDeath", entity, damageSource);
        }
    }

    public static class EntityDamageHook {
        @Advice.OnMethodEnter
        public static void onEnter(@Advice.This Object entity, @Advice.Argument(0) Object damageSource, @Advice.Argument(1) float amount) {
            Phoenix.getManager().fireEvent("onEntityDamage", entity, damageSource, amount);
        }
    }
}
