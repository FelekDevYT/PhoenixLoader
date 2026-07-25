package dev.felek.phoenix;

import dev.felek.phoenix.modding.ModManager;

import java.io.IOException;

/**
 * @className: Phoenix
 * @author: Felek
 * @date: 25.07.2026 15:35
 */

public class Phoenix {
    private static ModManager manager;

    public static ModManager getManager() {
        if (manager == null) {
            manager = new ModManager();
        }

        return manager;
    }
}
