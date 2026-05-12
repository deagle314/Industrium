package com.industrium.core.client.screen;

import com.industrium.core.Industrium;
import com.industrium.core.common.menu.BoilerMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BoilerScreen extends AbstractContainerScreen<BoilerMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Industrium.MOD_ID, "textures/gui/machine_gui.png");

    public BoilerScreen(BoilerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.getBurnTime() > 0) {
            int k = getBurnLeftScaled();
            graphics.blit(TEXTURE, x + 80, y + 35 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        // Render water and steam bars (placeholders for now)
        int water = (int) (menu.getWaterAmount() / 10000.0 * 50);
        graphics.fill(x + 20, y + 70 - water, x + 30, y + 70, 0xFF0000FF);

        int steam = (int) (menu.getSteamAmount() / 10000.0 * 50);
        graphics.fill(x + 140, y + 70 - steam, x + 150, y + 70, 0xFFAAAAAA);
    }

    private int getBurnLeftScaled() {
        int i = menu.getMaxBurnTime();
        if (i == 0) i = 200;
        return menu.getBurnTime() * 13 / i;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
