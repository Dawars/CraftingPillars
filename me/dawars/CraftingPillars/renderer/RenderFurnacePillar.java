package me.dawars.CraftingPillars.renderer;

import static org.lwjgl.opengl.GL11.*;

import javax.swing.Renderer;

import org.lwjgl.opengl.GL11;

import com.google.common.primitives.SignedBytes;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.CustomParticle;
import me.dawars.CraftingPillars.tile.TileEntityFurnacePillar;
import me.dawars.CraftingPillars.tile.TileEntityFurnacePillar;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class RenderFurnacePillar extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	private static final ResourceLocation TEXTURE_FURNACEPILLAR = new ResourceLocation(CraftingPillars.id + ":textures/models/furnacePillar.png");
	
	public static ModelBase model = new ModelBase()
	{
		
	};
	
	private ModelRenderer CraftingBottom;
	private ModelRenderer CraftingBotSlab;
	private ModelRenderer Pillar1;
	private ModelRenderer WorkbenchSlab;
	private ModelRenderer WorkbenchTop;
	private ModelRenderer Pillar2;
	private ModelRenderer Pillar3;
	private ModelRenderer Pillar4;
	
	private ModelRenderer pillarBottom;
	
	private RenderingHelper.ItemRender itemRenderer;
	private RenderingHelper.ItemRender resultRenderer;
	
	public RenderFurnacePillar()
	{
		itemRenderer = new RenderingHelper.ItemRender(false, true);
		resultRenderer = new RenderingHelper.ItemRender(false, false);
		
		model.textureWidth = 128;
		model.textureHeight = 64;
		
		pillarBottom = new ModelRenderer(model, 0, 33);
		pillarBottom.addBox(0F, 0F, 0F, 12, 3, 12);
		pillarBottom.setRotationPoint(-6F, 21F, 6F);
		pillarBottom.setTextureSize(128, 64);
		pillarBottom.mirror = true;
		setRotation(pillarBottom, 0F, 1.570796F, 0F);
		
		CraftingBottom = new ModelRenderer(model, 0, 0);
		CraftingBottom.addBox(0F, 0F, 0F, 16, 2, 16);
		CraftingBottom.setRotationPoint(-8F, 22F, -8F);
		CraftingBottom.setTextureSize(128, 64);
		CraftingBottom.mirror = true;
		setRotation(CraftingBottom, 0F, 0F, 0F);
		CraftingBotSlab = new ModelRenderer(model, 0, 18);
		CraftingBotSlab.addBox(0F, 0F, 0F, 14, 1, 14);
		CraftingBotSlab.setRotationPoint(-7F, 21F, -7F);
		CraftingBotSlab.setTextureSize(128, 64);
		CraftingBotSlab.mirror = true;
		setRotation(CraftingBotSlab, 0F, 0F, 0F);
		Pillar1 = new ModelRenderer(model, 2, 43);
		Pillar1.addBox(0F, 0F, 0F, 2, 10, 2);
		Pillar1.setRotationPoint(-6F, 11F, -6F);
		Pillar1.setTextureSize(128, 64);
		Pillar1.mirror = true;
		setRotation(Pillar1, 0F, 0F, 0F);
		WorkbenchSlab = new ModelRenderer(model, 0, 18);
		WorkbenchSlab.addBox(0F, 0F, 0F, 14, 1, 14);
		WorkbenchSlab.setRotationPoint(-7F, 10F, -7F);
		WorkbenchSlab.setTextureSize(128, 64);
		WorkbenchSlab.mirror = true;
		setRotation(WorkbenchSlab, 0F, 0F, 0F);
		WorkbenchTop = new ModelRenderer(model, 64, 0);
		WorkbenchTop.addBox(0F, 0F, 0F, 16, 2, 16);
		WorkbenchTop.setRotationPoint(-8F, 8F, -8F);
		WorkbenchTop.setTextureSize(128, 64);
		WorkbenchTop.mirror = true;
		setRotation(WorkbenchTop, 0F, 0F, 0F);
		Pillar2 = new ModelRenderer(model, 2, 43);
		Pillar2.addBox(-2F, 0F, -2F, 2, 10, 2);
		Pillar2.setRotationPoint(6F, 11F, -6F);
		Pillar2.setTextureSize(128, 64);
		Pillar2.mirror = true;
		setRotation(Pillar2, 0F, 1.570796F, 0F);
		Pillar3 = new ModelRenderer(model, 2, 43);
		Pillar3.addBox(0F, 0F, 0F, 2, 10, 2);
		Pillar3.setRotationPoint(-6F, 11F, 6F);
		Pillar3.setTextureSize(128, 64);
		Pillar3.mirror = true;
		setRotation(Pillar3, 0F, 1.570796F, 0F);
		Pillar4 = new ModelRenderer(model, 2, 43);
		Pillar4.addBox(-2F, 0F, -2F, 2, 10, 2);
		Pillar4.setRotationPoint(6F, 11F, 6F);
		Pillar4.setTextureSize(128, 64);
		Pillar4.mirror = true;
		setRotation(Pillar4, 0F, 0F, 0F);
	}
	
	public void render(float f, boolean connected)
	{
		if(connected)
		{
			pillarBottom.render(f);
		}
		else
		{
			CraftingBottom.render(f);
			CraftingBotSlab.render(f);
		}
		Pillar1.render(f);
		WorkbenchSlab.render(f);
		WorkbenchTop.render(f);
		Pillar2.render(f);
		Pillar3.render(f);
		Pillar4.render(f);
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
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_FURNACEPILLAR);
		render(0.0625F, tile.worldObj.getBlockId(tile.xCoord, tile.yCoord-1, tile.zCoord) == CraftingPillars.blockExtendPillar.blockID);
		glPopMatrix();
		
		TileEntityFurnacePillar pillarTile = (TileEntityFurnacePillar) tile;
		EntityItem citem = new EntityItem(tile.worldObj);
		
		glPushMatrix();
		
			glTranslated(x+0.5D, y, z+0.5D);
//			glRotatef(90F * tile.blockMetadata, 0F, 1F, 0F);

			//Input
			if(pillarTile.getStackInSlot(0) != null)
			{//TODO: tilt
				glPushMatrix();
					citem.hoverStart = 0F;
					citem.setEntityItemStack(pillarTile.getStackInSlot(0));
					resultRenderer.render(citem, 0F, 1.125F, 0F, pillarTile.showNum);
				glPopMatrix();
			}
		
			//Output
			if(pillarTile.getStackInSlot(2) != null)
			{
				glPushMatrix();
					glTranslatef(0F, 1.75F, 0F);
					citem.hoverStart = 0F;
					citem.setEntityItemStack(pillarTile.getStackInSlot(2));
					resultRenderer.render(citem, 0F, 0F, 0F, pillarTile.showNum);
				glPopMatrix();
			}
			
			//processed item
			if(pillarTile.canSmelt() && pillarTile.burnTime > 0)
			{
				glPushMatrix();
					glTranslatef(0F, 1.75F - pillarTile.cookTime/150F, 0F);
					citem.hoverStart = 0F;
					citem.setEntityItemStack(FurnaceRecipes.smelting().getSmeltingResult(pillarTile.getStackInSlot(0)));
					resultRenderer.render(citem, 0.01F, 0F, 0.01F, false);
				glPopMatrix();
			}
			
			//Fuel
			if(pillarTile.getStackInSlot(1) != null)
			{
				citem.hoverStart = 0F;
				citem.setEntityItemStack(pillarTile.getStackInSlot(1));
				itemRenderer.render(citem, 0F, 0.3F, 0F, pillarTile.showNum);
			}
		glPopMatrix();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		glPushMatrix();
		glTranslated(0, 1.0D, 0);
		glRotatef(180F, 1F, 0F, 0F);
		glRotatef(90F, 0F, 1F, 0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_FURNACEPILLAR);
		render(0.0625F, false);
		glPopMatrix();
	}
	
	@Override
	// No TileEntity here can't use
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
		return CraftingPillars.furnacePillarRenderID;
	}
}
