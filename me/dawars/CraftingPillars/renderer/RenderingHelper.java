package me.dawars.CraftingPillars.renderer;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
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
		FontRenderer fontrenderer = RenderManager.instance.getFontRenderer();
        float f1 = 0.016666668F * scale;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.0F, (float)y, (float)z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-f1, -f1, f1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Tessellator tessellator = Tessellator.instance;
        byte b0 = 0;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        tessellator.startDrawingQuads();
        int j = fontrenderer.getStringWidth(text) / 2;
//        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
//        tessellator.addVertex((double)(-j - 1), (double)(-1 + b0), 0.0D);
//        tessellator.addVertex((double)(-j - 1), (double)(8 + b0), 0.0D);
//        tessellator.addVertex((double)(j + 1), (double)(8 + b0), 0.0D);
//        tessellator.addVertex((double)(j + 1), (double)(-1 + b0), 0.0D);
//        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, b0, color);//553648127
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, b0, color);//-1
        GL11.glEnable(GL11.GL_LIGHTING);
       
//        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
		
//		FontRenderer fontRenderer = RenderManager.instance.getFontRenderer();
//		glPushMatrix();
//			glTranslatef(x, y, z);
//			glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
//			glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
//			glRotatef(180F, 0.0F, 0.0F, 1.0F);
//			glScalef(0.05F*scale, 0.05F*scale, 1F);
//			fontRenderer.drawString(text, -fontRenderer.getStringWidth(text)/2, -fontRenderer.FONT_HEIGHT/2, color);
////			fontRenderer.drawString(text, -fontRenderer.getStringWidth(text)/2, -fontRenderer.FONT_HEIGHT/2, color);
//		glPopMatrix();
	}
}
