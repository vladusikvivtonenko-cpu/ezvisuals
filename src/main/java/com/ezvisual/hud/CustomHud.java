package com.ezvisual.hud;

import com.ezvisual.modules.VisualModules;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CustomHud implements HudRenderCallback {
    private static final Identifier ANIME_ICON = Identifier.of("ezvisual", "textures/gui/anime_girl.png");

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;

        // 1. Вотермарка
        String watermark = "★ ezvisual | основатель ezbumhikvisual ★";
        context.fill(2, 2, mc.textRenderer.getWidth(watermark) + 10, 16, 0x90000000);
        context.drawText(mc.textRenderer, watermark, 6, 5, 0xFF00FFD5, true);

        // 2. Ефекти
        int effectY = 22;
        for (StatusEffectInstance effect : mc.player.getStatusEffects()) {
            int totalSec = effect.getDuration() / 20;
            String effectText = effect.getEffectType().value().getName().getString()
                    + " -> " + String.format("%02d:%02d", totalSec / 60, totalSec % 60);
            context.fill(2, effectY - 1, mc.textRenderer.getWidth(effectText) + 8, effectY + 10, 0x70000000);
            context.drawText(mc.textRenderer, effectText, 6, effectY, 0xFFFFAA00, true);
            effectY += 12;
        }

        // 3. Міцність шолома
        if (VisualModules.armorPercent) {
            int armorY = context.getScaledWindowHeight() - 50;
            int armorX = context.getScaledWindowWidth() / 2 + 10;
            ItemStack helmet = mc.player.getInventory().getArmorStack(3);
            if (!helmet.isEmpty()) {
                int max = helmet.getMaxDamage();
                int percent = max > 0 ? ((max - helmet.getDamage()) * 100) / max : 100;
                context.drawItem(helmet, armorX, armorY);
                context.drawText(mc.textRenderer, percent + "%", armorX + 18, armorY + 4, 0xFF00FF00, true);
            }
        }

        // 4. FT Helper
        if (VisualModules.ftHelper) {
            context.drawText(mc.textRenderer, "[FT Helper: ACTIVE]", 6, effectY + 4, 0xFF00FFD5, true);
            if (mc.player.hurtTime > 0) {
                context.drawText(mc.textRenderer, "⚠ РАДІУС ДЕЗОРІЄНТАЦІЇ / ЗАМОРОЗКИ ⚠", 10, 100, 0xFFFF0000, true);
            }
        }

        // 5. Аніме-дівчина в кутку
        int h = 90;
        int w = h * 446 / 715;
        context.drawTexture(RenderLayer::getGuiTextured, ANIME_ICON,
                context.getScaledWindowWidth() - w - 4,
                context.getScaledWindowHeight() - h - 4,
                0f, 0f, w, h, 446, 715, 446, 715);
    }
}
