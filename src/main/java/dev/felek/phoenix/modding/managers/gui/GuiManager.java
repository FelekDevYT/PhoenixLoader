package dev.felek.phoenix.modding.managers.gui;

import dev.felek.phoenix.modding.api.gui.GuiDef;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: GuiManager
 * @author: Felek
 * @date: 30.07.2026 13:48
 */

public class GuiManager {
    public Map<String, GuiDef> guis = new HashMap<>();

    public void registerGui(String id, GuiDef definition) {
        guis.put(id, definition);
    }

    public GuiDef getGui(String id) {
        return guis.get(id);
    }
}
