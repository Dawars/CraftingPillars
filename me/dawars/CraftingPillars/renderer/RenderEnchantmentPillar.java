package me.dawars.CraftingPillars.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityShowOffPillar;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.item.EntityItem;

public class RenderEnchantmentPillar extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	private static final ResourceLocation TEXTURE_SHOWOFFPILLAR = new ResourceLocation(CraftingPillars.id + ":textures/models/showoffPillar.png");

	public static ModelBase model = new ModelBase()
	{

	};

	private ModelRenderer bottom;
	private ModelRenderer pillarbottom;
	private ModelRenderer pillar;
	private ModelRenderer pillartop;
	private ModelRenderer top;

	private ModelRenderer pillarBottom;

	private Random random;
	private RenderingHelper.ItemRender itemRenderer;
	private RenderingHelper.ItemRender resultRenderer;

	public RenderEnchantmentPillar()
	{
		this.random = new Random();
		this.itemRenderer = new RenderingHelper.ItemRender(false, true);
		this.resultRenderer = new RenderingHelper.ItemRender(true, true);

		model.textureWidth = 128;
		model.textureHeight = 64;

		this.pillarBottom = new ModelRenderer(model, 0, 33);
		this.pillarBottom.addBox(0F, 0F, 0F, 12, 3, 12);
		this.pillarBottom.setRotationPoint(-6F, 21F, 6F);
		this.pillarBottom.setTextureSize(128, 64);
		this.pillarBottom.mirror = true;
		this.setRotation(this.pillarBottom, 0F, 1.570796F, 0F);

		this.bottom = new ModelRenderer(model, 0, 0);
		this.bottom.addBox(-8F, -1F, -8F, 16, 2, 16);
		this.bottom.setRotationPoint(0F, 23F, 0F);
		this.bottom.setTextureSize(128, 64);
		this.bottom.mirror = true;
		this.setRotation(this.bottom, 0F, 0F, 0F);
		this.pillarbottom = new ModelRenderer(model, 0, 18);
		this.pillarbottom.addBox(-7F, 0F, -7F, 14, 1, 14);
		this.pillarbottom.setRotationPoint(0F, 21F, 0F);
		this.pillarbottom.setTextureSize(128, 64);
		this.pillarbottom.mirror = true;
		this.setRotation(this.pillarbottom, 0F, 0F, 0F);
		this.pillar = new ModelRenderer(model, 0, 33);
		this.pillar.addBox(-6F, 0F, -6F, 12, 10, 12);
		this.pillar.setRotationPoint(0F, 11F, 0F);
		this.pillar.setTextureSize(128, 64);
		this.pillar.mirror = true;
		this.setRotation(this.pillar, 0F, 0F, 0F);
		this.pillartop = new ModelRenderer(model, 0, 18);
		this.pillartop.addBox(-7F, 0F, -7F, 14, 1, 14);
		this.pillartop.setRotationPoint(0F, 10F, 0F);
		this.pillartop.setTextureSize(128, 64);
		this.pillartop.mirror = true;
		this.setRotation(this.pillartop, 0F, 0F, 0F);
		this.top = new ModelRenderer(model, 64, 0);
		this.top.addBox(-8F, -1F, -8F, 16, 2, 16);
		this.top.setRotationPoint(0F, 9F, 0F);
		this.top.setTextureSize(128, 64);
		this.top.mirror = true;
		this.setRotation(this.top, 0F, 0F, 0F);
	}

	public void render(float f, boolean connected)
	{
		if(connected)
		{
			this.pillarBottom.render(f);
		}
		else
		{
			this.bottom.render(f);
			this.pillarbottom.render(f);
		}
		this.pillar.render(f);
		this.pillartop.render(f);
		this.top.render(f);
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
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_SHOWOFFPILLAR);
		this.render(0.0625F, tile.worldObj.getBlockId(tile.xCoord, tile.yCoord-1, tile.zCoord) == CraftingPillars.blockBasePillar.blockID);
		glPopMatrix();

		TileEntityShowOffPillar workTile = (TileEntityShowOffPillar) tile;
		EntityItem citem = new EntityItem(tile.worldObj);
		citem.hoverStart = CraftingPillars.floatingItems ? workTile.rot : 0F;

		glPushMatrix();
		glRotatef(90F * (tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord) - 2), 0F, 1F, 0F);

		glTranslated(x, y, z);

		if(workTile.getStackInSlot(0) != null)
		{
			glPushMatrix();
			citem.hoverStart = -workTile.rot;
			citem.setEntityItemStack(workTile.getStackInSlot(0));
			this.resultRenderer.render(citem, 0.5F, 1.3F, 0.5F, workTile.showNum);
			glPopMatrix();
		}
		glPopMatrix();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		glPushMatrix();
		glPushAttrib(GL_ENABLE_BIT);
		glEnable(GL_DEPTH_TEST);
		glTranslated(0, 1.0D, 0);
		glRotatef(180F, 1F, 0F, 0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_SHOWOFFPILLAR);
		this.render(0.0625F, false);
		glPopAttrib();
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
		return CraftingPillars.showOffPillarRenderID;
	}
}
