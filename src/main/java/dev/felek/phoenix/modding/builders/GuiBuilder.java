package dev.felek.phoenix.modding.builders;

import dev.felek.phoenix.Phoenix;
import dev.felek.phoenix.modding.api.gui.GuiAction;
import dev.felek.phoenix.modding.api.gui.GuiButton;
import dev.felek.phoenix.modding.api.gui.GuiDef;
import dev.felek.phoenix.modding.api.gui.GuiLabel;

import java.awt.*;

/**
 * @className: GuiBuilder
 * @author: Felek
 * @date: 30.07.2026 13:19
 */

public class GuiBuilder {
    private String id;
    private GuiDef gui = new GuiDef();

    private GuiBuilder(String id) {
        this.id = id;
    }

    public static GuiBuilder create(String id) {
        return new GuiBuilder(id);
    }

    public GuiBuilder id(String id) {
        this.id = id;
        return this;
    }

    public GuiBuilder gui(GuiDef gui) {
        this.gui = gui;
        return this;
    }

    public GuiBuilder title(String title) {
        gui.setTitle(title);
        return this;
    }

    public GuiBuilder button(int x, int y, int width, int height, String text, GuiAction action) {
        gui.getButtons().add(new GuiButton(x, y, width, height, text, action));
        return this;
    }

    public GuiBuilder label(int x, int y, String text) {
        gui.getLabels().add(new GuiLabel(x, y, text, Color.WHITE));
        return this;
    }

    public GuiBuilder label(int x, int y, String text, Color color) {
        gui.getLabels().add(new GuiLabel(x, y, text, color));
        return this;
    }

    public void register() {
        Phoenix.getManager().guiManager.registerGui(id, gui);
    }
}
