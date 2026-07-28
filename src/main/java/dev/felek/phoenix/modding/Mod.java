package dev.felek.phoenix.modding;

import dev.felek.phoenix.modding.event.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: Mod
 * @author: Felek
 * @date: 25.07.2026 15:22
 */

public class Mod {
    private String modName;
    private String modVersion;
    private String credits;
    private String description;
    private String mainFile;
    private String[] authors;

    private List<Listener> listeners;

    public Mod(String modName, String modVersion, String credits, String description, String[] authors, String mainFile) {
        this.modName = modName;
        this.modVersion = modVersion;
        this.credits = credits;
        this.description = description;
        this.authors = authors;
        this.mainFile = mainFile;
        this.listeners = new ArrayList<>();
    }

    public Mod(String modName, String modVersion, String description, String mainFile) {
        this.modName = modName;
        this.modVersion = modVersion;
        this.description = description;
        this.credits = "Special credits for PhoenixMC team.";
        this.authors = new String[]{"FelekDevYT"};
        this.mainFile = mainFile;
        this.listeners = new ArrayList<>();
    }

    public String getModName() {
        return modName;
    }

    public String getMainFile() {
        return "mods/" + modName + "/" + mainFile;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public String getModVersion() {
        return modVersion;
    }

    public void setModVersion(String modVersion) {
        this.modVersion = modVersion;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public List<Listener> getListeners() {
        return listeners;
    }

    public void setListeners(List<Listener> listeners) {
        this.listeners = listeners;
    }
}
