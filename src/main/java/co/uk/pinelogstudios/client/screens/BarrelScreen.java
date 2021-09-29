package co.uk.pinelogstudios.client.screens;

import co.uk.pinelogstudios.client.screens.containers.BarrelContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

/**
 * Author: Mr. Pineapple
 */
public class BarrelScreen extends AbstractContainerScreen<BarrelContainer> {
    private static final ResourceLocation CONTAINER_TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");

    public BarrelScreen(BarrelContainer container, Inventory playerInventory, Component textComponent) {
        super(container, playerInventory, textComponent);
    }

    @Override
    public void render(PoseStack poses, int mouseX, int mouseY, float delta) {
        this.renderBackground(poses);
        super.render(poses, mouseX, mouseY, delta);
        this.renderTooltip(poses, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poses, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(poses, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
