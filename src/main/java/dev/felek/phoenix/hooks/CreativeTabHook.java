package dev.felek.phoenix.hooks;

import dev.felek.phoenix.Phoenix;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class CreativeTabHook {
    @Advice.OnMethodExit
    public static void onExit(@Advice.This Object tab) {
        try {
            Object reg = Class.forName("net.minecraft.core.registries.BuiltInRegistries")
                    .getField("CREATIVE_MODE_TAB").get(null);
            Object obj = reg.getClass().getMethod("getKey", Object.class).invoke(reg, tab);
            List<?> items = Phoenix.getManager().tabManager.getItemsForTab(obj == null ? "null" : obj.toString());

            append(tab, "displayItems", items);
            append(tab, "displayItemsSearchTab", items);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void append(Object tab, String fieldName, List<?> items) throws Exception {
        Field f = tab.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        Set<Object> set = (Set<Object>) f.get(tab);

        Class<?> isc = Class.forName("net.minecraft.world.item.ItemStack");
        Class<?> ilc = Class.forName("net.minecraft.world.level.ItemLike");
        var ctor = isc.getConstructor(ilc);

        for (Object vanillaItem : items) {
            set.add(ctor.newInstance(vanillaItem));
        }
    }
}