package me.dawars.CraftingPillars.renderer;

import static org.lwjgl.opengl.GL11.*;
import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderExtendPillar extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	private static final ResourceLocation TEXTURE_WORKPILLAR = new ResourceLocation(CraftingPillars.id + ":textures/models/extendPillar.png");
	
	public static ModelBase model = new ModelBase()
	{
		
	};
	
	// fields
	ModelRenderer bottom;
	ModelRenderer bottoms;
	ModelRenderer tops;
	ModelRenderer top;
	ModelRenderer pillar;
	ModelRenderer pillarTop;
	ModelRenderer pillarBottom;
	ModelRenderer pillarEast;
	ModelRenderer pillarWest;
	ModelRenderer pillarNorth;
	ModelRenderer pillarSouth;
	
	public RenderExtendPillar()
	{
		model.textureWidth = 128;
		model.textureHeight = 64;
		
		bottom = new ModelRenderer(model, 0, 0);
		bottom.addBox(0F, 0F, 0F, 16, 2, 16);
		bottom.setRotationPoint(-8F, 22F, -8F);
		bottom.setTextureSize(128, 64);
		bottom.mirror = true;
		setRotation(bottom, 0F, 0F, 0F);
		bottoms = new ModelRenderer(model, 0, 18);
		bottoms.addBox(0F, 0F, 0F, 14, 1, 14);
		bottoms.setRotationPoint(-7F, 21F, -7F);
		bottoms.setTextureSize(128, 64);
		bottoms.mirror = true;
		setRotation(bottoms, 0F, 0F, 0F);
		tops = new ModelRenderer(model, 0, 18);
		tops.addBox(0F, 0F, 0F, 14, 1, 14);
		tops.setRotationPoint(-7F, 10F, -7F);
		tops.setTextureSize(128, 64);
		tops.mirror = true;
		setRotation(tops, 0F, 0F, 0F);
		top = new ModelRenderer(model, 64, 0);
		top.addBox(0F, 0F, 0F, 16, 2, 16);
		top.setRotationPoint(-8F, 8F, -8F);
		top.setTextureSize(128, 64);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		pillar = new ModelRenderer(model, 0, 33);
		pillar.addBox(0F, 0F, 0F, 12, 10, 12);
		pillar.setRotationPoint(-6F, 11F, 6F);
		pillar.setTextureSize(128, 64);
		pillar.mirror = true;
		setRotation(pillar, 0F, 1.570796F, 0F);
		pillarTop = new ModelRenderer(model, 0, 33);
		pillarTop.addBox(0F, 0F, 0F, 12, 3, 12);
		pillarTop.setRotationPoint(-6F, 8F, 6F);
		pillarTop.setTextureSize(128, 64);
		pillarTop.mirror = true;
		setRotation(pillarTop, 0F, 1.570796F, 0F);
		pillarBottom = new ModelRenderer(model, 0, 33);
		pillarBottom.addBox(0F, 0F, 0F, 12, 3, 12);
		pillarBottom.setRotationPoint(-6F, 21F, 6F);
		pillarBottom.setTextureSize(128, 64);
		pillarBottom.mirror = true;
		setRotation(pillarBottom, 0F, 1.570796F, 0F);
		pillarEast = new ModelRenderer(model, 0, 33);
		pillarEast.addBox(0F, 0F, 0F, 12, 2, 12);
		pillarEast.setRotationPoint(-8F, 22F, 6F);
		pillarEast.setTextureSize(128, 64);
		pillarEast.mirror = true;
		setRotation(pillarEast, 1.570796F, 1.570796F, 0F);
		pillarWest = new ModelRenderer(model, 0, 33);
		pillarWest.addBox(0F, 0F, 0F, 12, 2, 12);
		pillarWest.setRotationPoint(8F, 10F, 6F);
		pillarWest.setTextureSize(128, 64);
		pillarWest.mirror = true;
		setRotation(pillarWest, -1.570796F, 1.570796F, 0F);
		pillarNorth = new ModelRenderer(model, 0, 33);
		pillarNorth.addBox(0F, 0F, 0F, 12, 2, 12);
		pillarNorth.setRotationPoint(-6F, 10F, -8F);
		pillarNorth.setTextureSize(128, 64);
		pillarNorth.mirror = true;
		setRotation(pillarNorth, 1.570796F, 0F, 1.570796F);
		pillarSouth = new ModelRenderer(model, 0, 33);
		pillarSouth.addBox(0F, 0F, 0F, 12, 2, 12);
		pillarSouth.setRotationPoint(-6F, 22F, 8F);
		pillarSouth.setTextureSize(128, 64);
		pillarSouth.mirror = true;
		setRotation(pillarSouth, -1.570796F, 0F, -1.570796F);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void render(float f, boolean bt, boolean bb, boolean be, boolean bw, boolean bn, boolean bs)
	{
		if(bt)
		{
			pillarTop.render(f);
		}
		else
		{
			top.render(f);
			tops.render(f);
		}
		pillar.render(f);
		if(bb)
		{
			pillarBottom.render(f);
		}
		else
		{
			bottom.render(f);
			bottoms.render(f);
		}
		
		if(bw)
			pillarEast.render(f);
		if(be)
			pillarWest.render(f);
		if(bs)
			pillarNorth.render(f);
		if(bn)
			pillarSouth.render(f);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		glPushMatrix();
		glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
		int idTop, idBottom, idEast, idWest, idNorth, idSouth;
		int meta = tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);
		if(meta == 0)
		{
			idTop = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord+1, tile.zCoord);
			idBottom = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord-1, tile.zCoord);
			idEast = tile.worldObj.getBlockId(tile.xCoord+1, tile.yCoord, tile.zCoord);
			idWest = tile.worldObj.getBlockId(tile.xCoord-1, tile.yCoord, tile.zCoord);
			idNorth = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord, tile.zCoord-1);
			idSouth = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord, tile.zCoord+1);
		}
		else if(meta == 1)
		{
			glRotatef(90F, 1F, 0F, 0F);
			idTop = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord, tile.zCoord+1);
			idBottom = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord, tile.zCoord-1);
			idEast = tile.worldObj.getBlockId(tile.xCoord+1, tile.yCoord, tile.zCoord);
			idWest = tile.worldObj.getBlockId(tile.xCoord-1, tile.yCoord, tile.zCoord);
			idNorth = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord+1, tile.zCoord);
			idSouth = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord-1, tile.zCoord);
		}
		else
		{
			glRotatef(90F, 0F, 0F, 1F);
			idTop = tile.worldObj.getBlockId(tile.xCoord-1, tile.yCoord, tile.zCoord);
			idBottom = tile.worldObj.getBlockId(tile.xCoord+1, tile.yCoord, tile.zCoord);
			idEast = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord+1, tile.zCoord);
			idWest = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord-1, tile.zCoord);
			idNorth = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord, tile.zCoord-1);
			idSouth = tile.worldObj.getBlockId(tile.xCoord, tile.yCoord, tile.zCoord+1);
		}
		boolean top = (idTop == CraftingPillars.blockExtendPillar.blockID)
				|| (idTop == CraftingPillars.blockShowOffPillar.blockID)
				|| (idTop == CraftingPillars.blockCraftingPillar.blockID)
				|| (idTop == CraftingPillars.blockFurnacePillar.blockID);
		boolean bottom = (idBottom == CraftingPillars.blockExtendPillar.blockID)
				|| (idBottom == CraftingPillars.blockShowOffPillar.blockID)
				|| (idBottom == CraftingPillars.blockCraftingPillar.blockID)
				|| (idBottom == CraftingPillars.blockFurnacePillar.blockID);
		boolean east = (idEast == CraftingPillars.blockExtendPillar.blockID)
				|| (idEast == CraftingPillars.blockShowOffPillar.blockID)
				|| (idEast == CraftingPillars.blockCraftingPillar.blockID)
				|| (idEast == CraftingPillars.blockFurnacePillar.blockID);
		boolean west = (idWest == CraftingPillars.blockExtendPillar.blockID)
				|| (idWest == CraftingPillars.blockShowOffPillar.blockID)
				|| (idWest == CraftingPillars.blockCraftingPillar.blockID)
				|| (idWest == CraftingPillars.blockFurnacePillar.blockID);
		boolean north = (idNorth == CraftingPillars.blockExtendPillar.blockID)
				|| (idNorth == CraftingPillars.blockShowOffPillar.blockID)
				|| (idNorth == CraftingPillars.blockCraftingPillar.blockID)
				|| (idNorth == CraftingPillars.blockFurnacePillar.blockID);
		boolean south = (idSouth == CraftingPillars.blockExtendPillar.blockID)
				|| (idSouth == CraftingPillars.blockShowOffPillar.blockID)
				|| (idSouth == CraftingPillars.blockCraftingPillar.blockID)
				|| (idSouth == CraftingPillars.blockFurnacePillar.blockID);
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_WORKPILLAR);
		glTranslatef(0.0F, 1.0F, 0.0F);
		glRotatef(180F, 1F, 0F, 0F);
		/*
		System.out.println("====== Meta "+meta+" ======");
		System.out.println("Top: "+top);
		System.out.println("Bottom: "+bottom);
		System.out.println("East: "+east);
		System.out.println("West: "+west);
		System.out.println("North: "+north);
		System.out.println("South: "+south);
		*/
		render(0.0625F, top, bottom, east, west, north, south);
		glPopMatrix();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		glPushMatrix();
		glTranslated(0, 1.0D, 0);
		glRotatef(180F, 1F, 0F, 0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_WORKPILLAR);
		render(0.0625F, false, false, false, false, false, false);
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
		return CraftingPillars.extendPillarRenderID;
	}
}
