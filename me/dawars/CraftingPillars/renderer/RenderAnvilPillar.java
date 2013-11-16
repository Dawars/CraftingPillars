package me.dawars.CraftingPillars.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.blocks.BasePillar.CollisionBox;
import me.dawars.CraftingPillars.tiles.TileEntityAnvilPillar;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

public class RenderAnvilPillar extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	ModelRenderer CraftingBottom;
	ModelRenderer CraftingBotSlab;
	ModelRenderer WorkbenchSlab;
	ModelRenderer WorkbenchTop;
	ModelRenderer Pillar2;
	
	private static final ResourceLocation TEXTURE_ANVILPILLAR = new ResourceLocation(CraftingPillars.id + ":textures/models/anvilPillar.png");
	
	public static ModelBase model = new ModelBase()
	{
		
	};
	
	private Random random;
	private RenderingHelper.ItemRender itemRenderer;
	
	public RenderAnvilPillar()
	{
		random = new Random();
		itemRenderer = new RenderingHelper.ItemRender(false, true);
		
		model.textureWidth = 128;
		model.textureHeight = 64;
		
		CraftingBottom = new ModelRenderer(model, 0, 0);
		CraftingBottom.addBox(-6F, 0F, 0F, 12, 2, 16);
		CraftingBottom.setRotationPoint(0F, 22F, -8F);
		CraftingBottom.setTextureSize(128, 64);
		CraftingBottom.mirror = true;
		setRotation(CraftingBottom, 0F, 0F, 0F);
		CraftingBotSlab = new ModelRenderer(model, 0, 18);
		CraftingBotSlab.addBox(-5F, 0F, 0F, 10, 1, 14);
		CraftingBotSlab.setRotationPoint(0F, 21F, -7F);
		CraftingBotSlab.setTextureSize(128, 64);
		CraftingBotSlab.mirror = true;
		setRotation(CraftingBotSlab, 0F, 0F, 0F);
		WorkbenchSlab = new ModelRenderer(model, 0, 18);
		WorkbenchSlab.addBox(-4F, 0F, 0F, 8, 1, 14);
		WorkbenchSlab.setRotationPoint(0F, 11F, -7F);
		WorkbenchSlab.setTextureSize(128, 64);
		WorkbenchSlab.mirror = true;
		setRotation(WorkbenchSlab, 0F, 0F, 0F);
		WorkbenchTop = new ModelRenderer(model, 14, 0);
		WorkbenchTop.addBox(-5F, 0F, 0F, 10, 3, 16);
		WorkbenchTop.setRotationPoint(0F, 8F, -8F);
		WorkbenchTop.setTextureSize(128, 64);
		WorkbenchTop.mirror = true;
		setRotation(WorkbenchTop, 0F, 0F, 0F);
		Pillar2 = new ModelRenderer(model, 0, 40);
		Pillar2.addBox(-4F, 0F, -3F, 8, 9, 6);
		Pillar2.setRotationPoint(0F, 12F, 0F);
		Pillar2.setTextureSize(128, 64);
		Pillar2.mirror = true;
		setRotation(Pillar2, 0F, 1.570796F, 0F);
	}
	
	public void render(TileEntity tileentity, float f)
	{
		CraftingBottom.render(f);
		CraftingBotSlab.render(f);
		WorkbenchSlab.render(f);
		WorkbenchTop.render(f);
		Pillar2.render(f);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		glPushMatrix();
			glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
			glRotatef(180F, 1F, 0F, 0F);
			glRotatef(90F * (tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord) - 1), 0F, 1F, 0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_ANVILPILLAR);
			render(tile, 0.0625F);
		glPopMatrix();
		
		TileEntityAnvilPillar anvil = (TileEntityAnvilPillar)tile;
		EntityItem citem = new EntityItem(tile.worldObj);
		citem.hoverStart = anvil.rot;
		
		glPushMatrix();
			glTranslated(x, y, z);
			
			/*glDisable(GL_LIGHTING);
			glBindTexture(GL_TEXTURE_2D, 0);
			glColor3f(1F, 0F, 0F);
			glBegin(GL_LINES);
			glVertex3f(BasePillar.x1-tile.xCoord, BasePillar.y1-tile.yCoord, BasePillar.z1-tile.zCoord);
			glVertex3f(BasePillar.x2-tile.xCoord, BasePillar.y2-tile.yCoord, BasePillar.z2-tile.zCoord);
			glEnd();
			glEnable(GL_LIGHTING);*/
			
			if(anvil.getStackInSlot(0) != null)
			{
				citem.setEntityItemStack(anvil.getStackInSlot(0));
				this.itemRenderer.render(citem, 0.25F+1F/16F, 1.25F, 0.5F, true);
			}
			if(anvil.getStackInSlot(1) != null)
			{
				citem.setEntityItemStack(anvil.getStackInSlot(1));
				this.itemRenderer.render(citem, 0.75F-1F/16F, 1.25F, 0.5F, true);
			}
			
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glBindTexture(GL_TEXTURE_2D, 0);
			glColor4f(1F, 1F, 1F, 0.5F);
			for(CollisionBox box : ((BasePillar)CraftingPillars.blockAnvilPillar).buttons)
			{
				glPushMatrix();
				glTranslatef(0.5F, 0F, 0.5F);
				glRotatef(-90F * (tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)+2), 0F, 1F, 0F);
				glTranslatef(-0.5F, 0F, -0.5F);
				box.render();
				glPopMatrix();
			}
		glPopMatrix();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		glPushMatrix();
		glTranslated(0, 1.0D, 0);
		glRotatef(180F, 1F, 0F, 0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_ANVILPILLAR);
		render(null, 0.0625F);
		glPopMatrix();
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
		return CraftingPillars.anvilPillarRenderID;
	}
}
