package dev.felek.phoenix.modding.api.gui;

import java.awt.*;

/**
 * @className: GuiLabel
 * @author: Felek
 * @date: 30.07.2026 13:18
 */

public class GuiLabel {
    private final int x;
    private final int y;
    private final String text;
    private final Color color;

    public GuiLabel(int x, int y, String text, Color color) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }
}
