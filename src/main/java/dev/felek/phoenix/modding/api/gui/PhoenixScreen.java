package dev.felek.phoenix.modding.api.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * @className: PhoenixScreen
 * @author: Felek
 * @date: 30.07.2026 13:38
 */

public class PhoenixScreen extends Screen {
    private GuiDef definition;

    public PhoenixScreen(GuiDef definition) {
        super(Component.literal(definition.getTitle()));
        this.definition = definition;
    }

    @Override
    protected void init() {
        for (GuiButton btn : definition.getButtons()) {
            addRenderableWidget(
                    Button.builder(Component.literal(btn.getText()), b -> btn.getAction().execute()).bounds(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight()).build()
            );
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        extractTransparentBackground(guiGraphics);

        String title = this.title.getString();
        guiGraphics.text(this.font, title, (this.width / 2) - this.font.width(title) / 2, 20, 0xFFFFFF);
        for (GuiLabel lbl : definition.getLabels()) {
            guiGraphics.text(font, lbl.getText(), lbl.getX(), lbl.getY(), lbl.getColor().getRGB());
        }

        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
    }
}
