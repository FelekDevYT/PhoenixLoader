package dev.felek.phoenix.modding.builders;

import com.mojang.blaze3d.platform.InputConstants;
import dev.felek.phoenix.Phoenix;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.python.jline.console.KeyMap;

/**
 * @className: KeybindBuilder
 * @author: Felek
 * @date: 28.07.2026 18:32
 */

public class KeybindBuilder {
    private String name;
    private String category;
    private int keyCode;
    private String eventName;

    public KeybindBuilder(String name, int keyCode) {
        this.name = name;
        this.keyCode = keyCode;
    }

    public static KeybindBuilder create(String name, int keyCode) {
        return new KeybindBuilder(name, keyCode);
    }

    public KeybindBuilder eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public KeybindBuilder category(String category) {
        this.category = category;
        return this;
    }

    public void buildAndRegister() {
        if (eventName == null) {
            throw new RuntimeException("NULL EVENT NAME!");
        }

        KeyMapping km = new KeyMapping(name, InputConstants.Type.KEYSYM, keyCode, get(category));
        Phoenix.getManager().keybindManager.registerKeybind(km, eventName);
    }

    private KeyMapping.Category get(String name) {
        return switch (name.toLowerCase()) {
            case "misc" -> KeyMapping.Category.MISC;
            case "creative" -> KeyMapping.Category.CREATIVE;
            case "debug" -> KeyMapping.Category.DEBUG;
            case "gameplay" -> KeyMapping.Category.GAMEPLAY;
            case "multiplayer" -> KeyMapping.Category.MULTIPLAYER;
            case "spectator" -> KeyMapping.Category.SPECTATOR;
            case "movement" -> KeyMapping.Category.MOVEMENT;
            default -> KeyMapping.Category.register(Identifier.fromNamespaceAndPath(name.split(":")[0], name.split(":")[1]));
        };
    }
}
