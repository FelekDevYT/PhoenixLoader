package dev.felek.phoenix.modding.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

/**
 * @className: HUD
 * @author: Felek
 * @date: 28.07.2026 12:24
 */

public class HUD {
    private final GuiGraphicsExtractor gge;
    private final Font font;

    public HUD(GuiGraphicsExtractor gge) {
        this.gge = gge;
        this.font = Minecraft.getInstance().font;
    }

    public GuiGraphicsExtractor getGraphics() {
        return gge;
    }

    public void drawText(String s, int x, int y, Color color) {
        drawText(s, x, y, color, true);
    }

    public void drawText(String s, int x, int y, Color color, boolean shadow) {
        if (s == null || color == null)
            return;
        gge.text(font, s, x, y, color.getRGB());
    }

    public void drawRect(int x, int y, int w, int h, Color c) {
        if (c == null)
            return;

        gge.fill(x, y, x + w, y + h, c.getRGB());
    }

    public void drawGradientRect(int x, int y, int w, int h, Color c1, Color c2) {
        if (c1 == null || c2 == null)
            return;

        gge.fillGradient(x, y, w, h, c1.getRGB(), c2.getRGB());
    }

    public void drawRectOutline(int x, int y, int w, int h, Color c) {
        if (c == null)
            return;

        gge.outline(x, y, w, h, c.getRGB());
    }

    public void drawHorizontalLine(int x, int x1, int y1, Color c) {
        if (c == null)
            return;

        gge.horizontalLine(x, x1, y1, c.getRGB());
    }

    public void drawVerticalLine(int x, int x1, int y1, Color c) {
        if (c == null)
            return;

        gge.verticalLine(x, x1, y1, c.getRGB());
    }

    public void drawItem(int x, int y, String id) {
        ItemStack stack = getItemStack(id);
        gge.item(stack, x, y);
    }

    public void drawItemWithDecoration(int x, int y, String id) {
        ItemStack stack = getItemStack(id);
        gge.item(stack, x, y);
        gge.itemDecorations(font, stack, x, y);
    }

    public void drawTooltip(String text, int x, int y) {
        if (text == null) return;
        gge.setTooltipForNextFrame(Component.literal(text), x, y);
    }

    public int getWidth() {
        return gge.guiWidth();
    }

    public int getHeight() {
        return gge.guiHeight();
    }

    public int getFPS() {
        return Minecraft.getInstance().getFps();
    }

    public String getPlayerName() {
        var player = Minecraft.getInstance().player;
        return player != null ? player.getScoreboardName() : "Player";
    }

    private ItemStack getItemStack(String id) {//todo: checks
        var item = BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(id.split(":")[0], id.split(":")[1]));
        return new ItemStack(item);
    }
}
