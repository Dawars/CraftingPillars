package me.dawars.CraftingPillars.renderer;

import java.awt.Color;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityLight;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import static org.lwjgl.opengl.GL11.*;

public class RenderLight extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	public int cubeDisplay;
	
	public RenderLight()
	{
		this.cubeDisplay = glGenLists(1);
		glNewList(this.cubeDisplay, GL_COMPILE);
			glBegin(GL_QUADS);
				glVertex3f(0F, 1F, 1F);
				glVertex3f(0F, 0F, 1F);
				glVertex3f(1F, 0F, 1F);
				glVertex3f(1F, 1F, 1F);
				
				glVertex3f(1F, 1F, 0F);
				glVertex3f(1F, 0F, 0F);
				glVertex3f(0F, 0F, 0F);
				glVertex3f(0F, 1F, 0F);
				
				glVertex3f(0F, 1F, 0F);
				glVertex3f(0F, 0F, 0F);
				glVertex3f(0F, 0F, 1F);
				glVertex3f(0F, 1F, 1F);
				
				glVertex3f(1F, 1F, 1F);
				glVertex3f(1F, 0F, 1F);
				glVertex3f(1F, 0F, 0F);
				glVertex3f(1F, 1F, 0F);
				
				glVertex3f(0F, 1F, 0F);
				glVertex3f(0F, 1F, 1F);
				glVertex3f(1F, 1F, 1F);
				glVertex3f(1F, 1F, 0F);
				
				glVertex3f(0F, 0F, 1F);
				glVertex3f(0F, 0F, 0F);
				glVertex3f(1F, 0F, 0F);
				glVertex3f(1F, 0F, 1F);
			glEnd();
		glEndList();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return CraftingPillars.lightRenderID;
	}
	
	public void render(int meta, Color color)
	{
		glPushMatrix();
			glTranslatef(0.5F, 0.5F, 0.5F);
			if(meta == 1)
				glRotatef(180F, 1F, 0F, 0F);
			if(meta == 2)
				glRotatef(90F, 1F, 0F, 0F);
			if(meta == 3)
				glRotatef(-90F, 1F, 0F, 0F);
			if(meta == 4)
				glRotatef(-90F, 0F, 0F, 1F);
			if(meta == 5)
				glRotatef(90F, 0F, 0F, 1F);
			glTranslatef(-0.5F, -0.5F, -0.5F);
			
			glBindTexture(GL_TEXTURE_2D, 0);
			
			glColor3f(1F, 1F, 0F);
			glPushMatrix();
				glTranslatef(6F/16F, 14F/16F, 6F/16F);
				glScalef(4F/16F, 2F/16F, 4F/16F);
				glCallList(this.cubeDisplay);
			glPopMatrix();
			
			glDisable(GL_LIGHTING);
			glColor3f((float)color.getRed()/255F, (float)color.getGreen()/255F, (float)color.getBlue()/255F);
			glPushMatrix();
				glTranslatef(4F/16F, 6F/16F, 4F/16F);
				glScalef(8F/16F, 8F/16F, 8F/16F);
				glCallList(this.cubeDisplay);
			glPopMatrix();
			glEnable(GL_LIGHTING);
		glPopMatrix();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{
		glPushMatrix();
			glTranslatef(-0.5F, -0.5F, -0.5F);
			this.render(meta, Color.red);
		glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick)
	{
		glPushMatrix();
			glTranslated(x, y, z);
			this.render(tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord), new Color(((TileEntityLight)tile).color));
		glPopMatrix();
	}
}
