package me.dawars.CraftingPillars.renderer;

import java.awt.Color;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;

import static org.lwjgl.opengl.GL11.*;

public class RenderingHelper
{
	public static class ItemRender extends RenderItem
	{
		private boolean bob, spread;
		
		public ItemRender(boolean bob, boolean spread)
		{
			super();
			this.bob = bob;
			this.spread = spread;
			this.setRenderManager(RenderManager.instance);
		}
		
		@Override
		public boolean shouldBob()
		{
			return this.bob;
		}
		
		@Override
		public boolean shouldSpreadItems()
		{
			return this.spread;
		}
	}
	
	public static void renderFloatingRect(float x, float y, float z, String text, Color color)
	{
		
	}
	
	public static void renderFloatingText(float x, float y, float z, float scale, String text, int color)
	{
		FontRenderer fontRenderer = RenderManager.instance.getFontRenderer();
		glPushMatrix();
			glTranslatef(x, y, z);
			glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
			glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
			glRotatef(180F, 0.0F, 0.0F, 1.0F);
			glScalef(0.05F*scale, 0.05F*scale, 1F);
			fontRenderer.drawString(text, -fontRenderer.getStringWidth(text)/2, -fontRenderer.FONT_HEIGHT/2, color);
		glPopMatrix();
	}
}
