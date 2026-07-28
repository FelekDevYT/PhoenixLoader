package dev.felek.phoenix.modding.managers.keybind;

import net.minecraft.client.KeyMapping;

/**
 * @className: CustomKeybind
 * @author: Felek
 * @date: 28.07.2026 18:27
 */

public class CustomKeybind {
    private KeyMapping mapping;
    private String eventName;

    public CustomKeybind(KeyMapping mapping, String eventName) {
        this.mapping = mapping;
        this.eventName = eventName;
    }

    public CustomKeybind mapping(KeyMapping mapping) {
        this.mapping = mapping;
        return this;
    }

    public CustomKeybind eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public KeyMapping getMapping() {
        return mapping;
    }

    public String getEventName() {
        return eventName;
    }
}
