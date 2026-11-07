package com.ezvisual.gui;

import com.ezvisual.modules.VisualModules;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ClickGuiScreen extends Screen {
    private static final Identifier ANIME_GIRL = Identifier.of("ezvisual", "textures/gui/anime_girl.png");
    private static final int TEX_W = 446;
    private static final int TEX_H = 715;
    private static final int PANEL_W = 150;
    private static final int ROW_H = 18;

    private interface Getter { boolean get(); }
    private interface Setter { void set(boolean v); }
    private record Entry(String name, Getter get, Setter set) {}

    private final Entry[] entries = {
            new Entry("NoFire",         () -> VisualModules.noFire,            v -> VisualModules.noFire = v),
            new Entry("NoShake",        () -> VisualModules.noShake,           v -> VisualModules.noShake = v),
            new Entry("FT Helper",      () -> VisualModules.ftHelper,          v -> VisualModules.ftHelper = v),
            new Entry("Shaders",        () -> VisualModules.customShaders,     v -> VisualModules.customShaders = v),
            new Entry("Low PC",         () -> VisualModules.lowPcOptimization, v -> VisualModules.lowPcOptimization = v),
            new Entry("Armor %",        () -> VisualModules.armorPercent,      v -> VisualModules.armorPercent = v),
            new Entry("Item Highlight", () -> VisualModules.itemHighlight,     v -> VisualModules.itemHighlight = v),
            new Entry("Swimming",       () -> VisualModules.swimmingMod,       v -> VisualModules.swimmingMod = v),
    };

    public ClickGuiScreen() {
        super(Text.literal("EzVisual"));
    }

    private int panelX() { return width / 2 - PANEL_W / 2 - 60; }
    private int panelY() { return height / 2 - (entries.length * ROW_H + 20) / 2; }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, width, height, 0x88000000);

        int imgH = Math.min(height - 20, 320);
        int imgW = imgH * TEX_W / TEX_H;
        int imgX = panelX() + PANEL_W + 10;
        int imgY = height / 2 - imgH / 2;
        ctx.drawTexture(RenderLayer::getGuiTextured, ANIME_GIRL, imgX, imgY,
                0f, 0f, imgW, imgH, TEX_W, TEX_H, TEX_W, TEX_H);

        int x = panelX();
        int y = panelY();
        int h = entries.length * ROW_H + 20;
        ctx.fill(x, y, x + PANEL_W, y + h, 0xC0101010);
        ctx.fill(x, y, x + PANEL_W, y + 16, 0xFF00B8A0);
        ctx.drawText(textRenderer, "★ EzVisual", x + 6, y + 4, 0xFFFFFFFF, true);

        int ry = y + 18;
        for (Entry e : entries) {
            boolean on = e.get().get();
            boolean hover = mouseX >= x && mouseX <= x + PANEL_W && mouseY >= ry && mouseY < ry + ROW_H;
            if (hover) ctx.fill(x, ry, x + PANEL_W, ry + ROW_H, 0x30FFFFFF);
            ctx.drawText(textRenderer, e.name(), x + 6, ry + 5, on ? 0xFF00FFD5 : 0xFF888888, true);
            ctx.drawText(textRenderer, on ? "ON" : "OFF", x + PANEL_W - 26, ry + 5, on ? 0xFF55FF55 : 0xFFFF5555, true);
            ry += ROW_H;
        }
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = panelX();
        int ry = panelY() + 18;
        for (Entry e : entries) {
            if (mouseX >= x && mouseX <= x + PANEL_W && mouseY >= ry && mouseY < ry + ROW_H) {
                e.set().set(!e.get().get());
                return true;
            }
            ry += ROW_H;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }
}
