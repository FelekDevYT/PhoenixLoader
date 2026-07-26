package dev.felek.phoenix.modding.managers;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.minecraft.resources.Identifier;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @className: LanguageManager
 * @author: Felek
 * @date: 25.07.2026 20:45
 */

public class LanguageManager {
    public static void loadLanguages(String modName, String namespace) {
        File lang = new File("mods/" + modName + "/" + "/assets/lang/");
        if (!lang.exists() && !lang.isDirectory()) {
            return;
        }

        for (File f : lang.listFiles((d, f) -> f.endsWith(".properties"))) {
            try {
                Properties props = new Properties();
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    props.load(br);
                }

                Map<String, String> translations = new HashMap<>();
                for (String key : props.stringPropertyNames()) {
                    translations.put(key, props.getProperty(key));
                }

                JSONObject obj = new JSONObject(translations);
                String json = obj.toString();
                String langC = f.getName().replace(".properties", "").toLowerCase();

                Identifier langIdentifier = Identifier.fromNamespaceAndPath(namespace, "lang/" + langC + ".json");
                PhoenixResources.INSTANCE.addAsset(langIdentifier, json.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
