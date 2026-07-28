package dev.felek.phoenix.modding;

import dev.felek.phoenix.modding.event.Listener;
import dev.felek.phoenix.modding.managers.BlockManager;
import dev.felek.phoenix.modding.managers.command.CommandManager;
import dev.felek.phoenix.modding.managers.ItemManager;
import dev.felek.phoenix.modding.managers.LanguageManager;
import org.json.JSONObject;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: ModManager
 * @author: Felek
 * @date: 25.07.2026 15:21
 */

public class ModManager {
    private List<Mod> loadedMods = new ArrayList<>();
    private final ScriptEngineManager scriptEngineManager;
    public ItemManager itemManager;
    public BlockManager blockManager;
    public CommandManager commandManager;

    public ModManager() {
        this.scriptEngineManager = new ScriptEngineManager();
        this.itemManager = new ItemManager();
        this.blockManager = new BlockManager();
        this.commandManager = new CommandManager();
    }

    public void loadMods() throws IOException, ScriptException {
        if (!new File("mods/").exists()) {
            new File("mods/").mkdirs();
        }
        for (File file : new File("mods/").listFiles()) {
            //mod's folder:
            //- mod.json
            //- scripts/
            //- assets/
            //- assets/textures

            String content = Files.readString(Paths.get(new File(file, "mod.json").toURI()));

            JSONObject object = new JSONObject(content);
            Mod mod = new Mod(object.getString("name"), object.getString("version"), object.getString("credits"), object.getString("description"), object.getString("authors").split(","), object.getString("mainFile"));
            loadedMods.add(mod);
            Listener listener = new Listener(mod.getMainFile());
            listener.compile(scriptEngineManager);
            mod.getListeners().add(listener);

            LanguageManager.loadLanguages(file.getName(), mod.getModName().toLowerCase());
            System.out.println("Loaded mod: " + mod.getModName() + " v" + mod.getModVersion());
        }
    }

    public void fireEvent(String name, Object... args) {
        for (Mod mod : loadedMods) {
            for (Listener listener : mod.getListeners()) {
                listener.invokeFunction(name, args);
            }
        }
    }

    public void onEnable(Object minecraftInstance) {
        for (Mod mod : loadedMods) {
            for (Listener l : mod.getListeners()) {
                l.invokeFunction("onEnable", minecraftInstance);
            }
        }
    }

    public void onDisable(Object minecraftInstance) {
        for (Mod mod : loadedMods) {
            for (Listener l : mod.getListeners()) {
                l.invokeFunction("onDisable", minecraftInstance);
            }
        }
    }

    public void onTick(Object minecraftInstance) {
        for (Mod mod : loadedMods) {
            for (Listener l : mod.getListeners()) {
                l.invokeFunction("onTick", minecraftInstance);
            }
        }
    }

    public void onItemRegister() {
        for (Mod mod : loadedMods) {
            for (Listener l : mod.getListeners()) {
                l.invokeFunction("onItemRegister");
            }
        }

        registerItems();
    }

    public void registerItems() {
        itemManager.registerAll();
        blockManager.registerAll();
    }
}
