package dev.felek.phoenix.modding.api.gui;

/**
 * @className: GuiButton
 * @author: Felek
 * @date: 30.07.2026 13:15
 */

public class GuiButton {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final String text;
    private final GuiAction action;

    public GuiButton(int x, int y, int width, int height, String text, GuiAction action) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.action = action;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getText() {
        return text;
    }

    public GuiAction getAction() {
        return action;
    }
}
