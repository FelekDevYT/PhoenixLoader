package dev.felek.phoenix.modding.managers.keybind;

import dev.felek.phoenix.Phoenix;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: KeybindManager
 * @author: Felek
 * @date: 28.07.2026 18:27
 */

public class KeybindManager {
    public List<CustomKeybind> keybinds = new ArrayList<>();
    private boolean isReg = false;

    public void registerKeybind(KeyMapping keyMapping, String eventName) {
        keybinds.add(new CustomKeybind(keyMapping, eventName));
    }

    public void registerAllOptions() {
        KeyMapping[] current = Minecraft.getInstance().options.keyMappings;
        KeyMapping[] newMaps = new KeyMapping[current.length + keybinds.size()];
        System.arraycopy(current, 0, newMaps, 0, current.length);

        for (int i = 0; i < keybinds.size(); i++) {
            newMaps[current.length + i] = keybinds.get(i).getMapping();
        }

        try {
            Field keyMappingsField = Options.class.getDeclaredField("keyMappings");
            keyMappingsField.setAccessible(true);
            keyMappingsField.set(Minecraft.getInstance().options, newMaps);

            isReg = true;
            System.out.println("Registered " + newMaps + " keybinds from PhoenixMC");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onTick() {
        if (!isReg)
            return;

        for (CustomKeybind ck : keybinds) {
            while (ck.getMapping().consumeClick()) {
                Phoenix.getManager().fireEvent(ck.getEventName());
            }
        }
    }
}
