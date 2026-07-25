package dev.felek.phoenix.modding.utils;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @className: RegistryUtils
 * @author: Felek
 * @date: 25.07.2026 19:31
 */

public class RegistryUtils {
    public static void unfreeze(Registry<?> registry) {
        if (registry instanceof MappedRegistry<?> mapped) {
            try {
                for (Field field : MappedRegistry.class.getDeclaredFields()) {
                    if (field.getType() == boolean.class) {
                        field.setAccessible(true);
                        field.set(mapped, false);
                    }
                }

                for (Field field : MappedRegistry.class.getDeclaredFields()) {
                    if (Map.class.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        if (field.get(mapped) == null) {
                            field.set(mapped, new IdentityHashMap<>());
                        }
                    }
                }
//
//                for (Field field : MappedRegistry.class.getDeclaredFields()) {
//                    if (field.getName().toLowerCase().contains("tag")) {
//                        field.setAccessible(true);
//                        field.set(mapped, null);
//                    }
//                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void refreeze(Registry<?> registry) {
        if (registry instanceof MappedRegistry<?> mapped) {
            try {
                for (Field field : MappedRegistry.class.getDeclaredFields()) {
                    if (field.getType() == boolean.class) {
                        field.setAccessible(true);
                        field.set(mapped, true);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
