package me.dawars.CraftingPillars.renderer;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;

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
			int number = item.getEntityItem().stackSize;
			if(number > 0 && showNum)
			{
				glDisable(GL_LIGHTING);
				renderFloatingText(x, y+(this.bob ? 0.5F : 0.4F), z, 0.4F, ""+number, Color.white.getRGB());
				glEnable(GL_LIGHTING);
			}
			this.doRenderItem(item, x, y, z, 0F, 0F);
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
	
	/*public static void renderFloatingRect(float x, float y, float z, float width, float height)
	{
		FontRenderer fontRenderer = RenderManager.instance.getFontRenderer();
		glPushMatrix();
			glTranslatef(x, y, z);
			glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
			glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
			glRotatef(180F, 0.0F, 0.0F, 1.0F);
			glBegin(GL_QUADS);
				glVertex2f(-width, height);
				glVertex2f(-width, -height);
				glVertex2f(width, -height);
				glVertex2f(width, height);
			glEnd();
		glPopMatrix();
	}*/
	
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
