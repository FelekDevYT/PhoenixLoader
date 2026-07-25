package dev.felek.phoenix.modding.event;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @className: Listener
 * @author: Felek
 * @date: 25.07.2026 15:24
 */

public class Listener {
    private String fileName;
    private ScriptEngine engine;

    public Listener(String fileName) {
        this.fileName = fileName;
    }

    public Listener compile(ScriptEngineManager manager) throws ScriptException, IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new RuntimeException("File not found.");
        }

        this.engine = manager.getEngineByName("groovy");
        String content = Files.readString(file.toPath());

        this.engine.eval(content);

        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void invokeFunction(String funcName, Object... args) {
        if (engine == null) return;

        if (engine instanceof Invocable inv) {
            try {
                inv.invokeFunction(funcName, args);
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
