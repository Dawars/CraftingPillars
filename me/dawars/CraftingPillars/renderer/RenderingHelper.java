package me.dawars.CraftingPillars.renderer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Vec3;

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
		
		public void render(EntityItem item, float x, float y, float z, boolean showNum)
		{	
			this.doRenderItem(item, x, y, z, 0F, 0F);

			int number = item.getEntityItem().stackSize;
			if(number > 0 && showNum)
			{
				glDisable(GL_LIGHTING);
				renderFloatingTextWithBackground(x, y+(this.bob ? 0.6F : 0.4F), z, 0.4F, ""+number, Color.white.getRGB(), new Color(0F, 0F, 0F, 0.5F));
				glEnable(GL_LIGHTING);
			}
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
	
	public static void applyFloatingRotations()
	{
		glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
	}
	
	public static void renderFloatingText(float x, float y, float z, float scale, String text, int color)
	{
		FontRenderer fontRenderer = RenderManager.instance.getFontRenderer();
		glPushMatrix();
			glTranslatef(x, y, z);
			applyFloatingRotations();
			glRotatef(180F, 0.0F, 0.0F, 1.0F);
			glScalef(0.05F*scale, 0.05F*scale, 1F);
			fontRenderer.drawString(text, -fontRenderer.getStringWidth(text)/2, -fontRenderer.FONT_HEIGHT/2, color);
		glPopMatrix();
	}
	
	public static void renderFloatingRect(float x, float y, float z, float w, float h, Color c)
	{
		FontRenderer fontRenderer = RenderManager.instance.getFontRenderer();
		glPushMatrix();
			glTranslatef(x, y, z);
			applyFloatingRotations();
			glScalef(0.05F, 0.05F, 1F);
			
			glColor4f((float)c.getRed()/256F, (float)c.getGreen()/256F, (float)c.getBlue()/256F, (float)c.getAlpha()/256F);
			glBindTexture(GL_TEXTURE_2D, 0);
			glBegin(GL_QUADS);
				glVertex3f(-w-1, -h-1, 0.001F);
				glVertex3f(-w-1, h, 0.001F);
				glVertex3f(w, h, 0.001F);
				glVertex3f(w, -h-1, 0.001F);
			glEnd();
			glDisable(GL_BLEND);
		glPopMatrix();
	}
	
	public static void renderFloatingTextWithBackground(float x, float y, float z, float scale, String text, int color, Color bgColor)
	{
		FontRenderer fontRenderer = RenderManager.instance.getFontRenderer();
		glPushMatrix();
			glTranslatef(x, y, z);
			applyFloatingRotations();
			glRotatef(180F, 0.0F, 0.0F, 1.0F);
			glScalef(0.05F*scale, 0.05F*scale, 1F);
			
			int h = fontRenderer.FONT_HEIGHT/2;
			int w = fontRenderer.getStringWidth(text)/2;
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glColor4f((float)bgColor.getRed()/256F, (float)bgColor.getGreen()/256F, (float)bgColor.getBlue()/256F, (float)bgColor.getAlpha()/256F);
			glBindTexture(GL_TEXTURE_2D, 0);
			glBegin(GL_QUADS);
				glVertex3f(-w-1, -h-1, 0.001F);
				glVertex3f(-w-1, h, 0.001F);
				glVertex3f(w, h, 0.001F);
				glVertex3f(w, -h-1, 0.001F);
			glEnd();
			glDisable(GL_BLEND);
			
			fontRenderer.drawString(text, -fontRenderer.getStringWidth(text)/2, -fontRenderer.FONT_HEIGHT/2, color);
		glPopMatrix();
	}
}
