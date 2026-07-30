package dev.felek.phoenix.modding.api.gui;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: GuiDef
 * @author: Felek
 * @date: 30.07.2026 13:19
 */

public class GuiDef {
    private String title = "";
    private final List<GuiButton> btns = new ArrayList<>();
    private final List<GuiLabel> lbls = new ArrayList<>();

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<GuiButton> getButtons() { return btns; }
    public List<GuiLabel> getLabels() { return lbls; }
}
