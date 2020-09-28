package co.uk.pinelogstudios.barrelsplus.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;

import co.uk.pinelogstudios.barrelsplus.core.BetterBarrels;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 *	Author:	Mr. Pineapple
 */
@Environment(EnvType.CLIENT)
public class BarrelScreen extends HandledScreen<BarrelScreenHandler>{
	public static final Identifier TEXTURE = new Identifier(BetterBarrels.MOD_ID, "textures/gui/better_barrel.png");
	
	public BarrelScreen(BarrelScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		++this.backgroundHeight;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}
	
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.client.getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
	    this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
	}
}
