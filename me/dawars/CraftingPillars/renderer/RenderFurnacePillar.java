package me.dawars.CraftingPillars.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import javax.swing.Renderer;

import org.lwjgl.opengl.GL11;

import com.google.common.primitives.SignedBytes;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.CustomParticle;
import me.dawars.CraftingPillars.tiles.TileEntityFurnacePillar;
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
	private ResourceLocation TEXTURE_FURNACEPILLAR;
	
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
	private ModelRenderer Icicle1A;
	private ModelRenderer Icicle1B;
	private ModelRenderer Icicle1C;
	private ModelRenderer Icicle2A;
	private ModelRenderer Icicle2C;
	private ModelRenderer Icicle2B;
	private ModelRenderer Icicle3A;
	private ModelRenderer Icicle3B;
	private ModelRenderer Icicle3C;
	private ModelRenderer Icicle3D;
	private ModelRenderer Icicle4A;
	private ModelRenderer Icicle4B;
	
	
	private RenderingHelper.ItemRender itemRenderer;
	private RenderingHelper.ItemRender resultRenderer;
	
	public RenderFurnacePillar()
	{
		if(CraftingPillars.winter)
			TEXTURE_FURNACEPILLAR = new ResourceLocation(CraftingPillars.id + ":textures/models/furnacePillarFrozen.png");
		else
			TEXTURE_FURNACEPILLAR = new ResourceLocation(CraftingPillars.id + ":textures/models/furnacePillar.png");
		
		
		itemRenderer = new RenderingHelper.ItemRender(false, true);
		resultRenderer = new RenderingHelper.ItemRender(false, false);
		
		model.textureWidth = 128;
		model.textureHeight = 64;
		
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
		
		Icicle1A = new ModelRenderer(model, 122, 38);
		Icicle1A.addBox(0F, 0F, 0F, 2, 1, 1);
		Icicle1A.setRotationPoint(5F, 11F, 6F);
		Icicle1A.setTextureSize(128, 64);
		Icicle1A.mirror = true;
		setRotation(Icicle1A, 0F, 0F, 0F);
		Icicle1B = new ModelRenderer(model, 122, 40);
		Icicle1B.addBox(0F, 0F, 0F, 1, 2, 1);
		Icicle1B.setRotationPoint(6F, 12F, 6F);
		Icicle1B.setTextureSize(128, 64);
		Icicle1B.mirror = true;
		setRotation(Icicle1B, 0F, 0F, 0F);
		Icicle1C = new ModelRenderer(model, 116, 52);
		Icicle1C.addBox(0F, 0F, 0F, 1, 1, 2);
		Icicle1C.setRotationPoint(6F, 11F, 4F);
		Icicle1C.setTextureSize(128, 64);
		Icicle1C.mirror = true;
		setRotation(Icicle1C, 0F, 0F, 0F);
		Icicle2A = new ModelRenderer(model, 122, 60);
		Icicle2A.addBox(0F, 0F, 0F, 1, 2, 2);
		Icicle2A.setRotationPoint(6F, 11F, -6F);
		Icicle2A.setTextureSize(128, 64);
		Icicle2A.mirror = true;
		setRotation(Icicle2A, 0F, 0F, 0F);
		Icicle2B = new ModelRenderer(model, 122, 38);
		Icicle2B.addBox(0F, 0F, 0F, 2, 1, 1);
		Icicle2B.setRotationPoint(5F, 11F, -7F);
		Icicle2B.setTextureSize(128, 64);
		Icicle2B.mirror = true;
		setRotation(Icicle2B, 0F, 0F, 0F);
		Icicle2C = new ModelRenderer(model, 122, 44);
		Icicle2C.addBox(0F, 0F, 0F, 1, 1, 1);
		Icicle2C.setRotationPoint(6F, 13F, -6F);
		Icicle2C.setTextureSize(128, 64);
		Icicle2C.mirror = true;
		setRotation(Icicle2C, 0F, 0F, 0F);
		Icicle3A = new ModelRenderer(model, 106, 50);
		Icicle3A.addBox(0F, 0F, 0F, 1, 1, 3);
		Icicle3A.setRotationPoint(-7F, 11F, -7F);
		Icicle3A.setTextureSize(128, 64);
		Icicle3A.mirror = true;
		setRotation(Icicle3A, 0F, 0F, 0F);
		Icicle3B = new ModelRenderer(model, 101, 50);
		Icicle3B.addBox(0F, 0F, 0F, 1, 1, 2);
		Icicle3B.setRotationPoint(-7F, 12F, -7F);
		Icicle3B.setTextureSize(128, 64);
		Icicle3B.mirror = true;
		setRotation(Icicle3B, 0F, 0F, 0F);
		Icicle3C = new ModelRenderer(model, 106, 50);
		Icicle3C.addBox(0F, 0F, 0F, 1, 1, 1);
		Icicle3C.setRotationPoint(-6F, 11F, -7F);
		Icicle3C.setTextureSize(128, 64);
		Icicle3C.mirror = true;
		setRotation(Icicle3C, 0F, 0F, 0F);
		Icicle3D = new ModelRenderer(model, 106, 46);
		Icicle3D.addBox(0F, 0F, 0F, 1, 1, 1);
		Icicle3D.setRotationPoint(-7F, 13F, -7F);
		Icicle3D.setTextureSize(128, 64);
		Icicle3D.mirror = true;
		setRotation(Icicle3D, 0F, 0F, 0F);
		Icicle4A = new ModelRenderer(model, 122, 35);
		Icicle4A.addBox(0F, 0F, 0F, 1, 1, 1);
		Icicle4A.setRotationPoint(-7F, 11F, 6F);
		Icicle4A.setTextureSize(128, 64);
		Icicle4A.mirror = true;
		setRotation(Icicle4A, 0F, 0F, 0F);
		Icicle4B = new ModelRenderer(model, 117, 43);
		Icicle4B.addBox(0F, 0F, 0F, 1, 2, 1);
		Icicle4B.setRotationPoint(-6F, 11F, 6F);
		Icicle4B.setTextureSize(128, 64);
		Icicle4B.mirror = true;
		setRotation(Icicle4B, 0F, 0F, 0F);
	}
	
	public void render(float f)
	{
		if(CraftingPillars.winter)
		{
		    Icicle1A.render(f);
		    Icicle1B.render(f);
		    Icicle1C.render(f);
		    Icicle2A.render(f);
		    Icicle2C.render(f);
		    Icicle2B.render(f);
		    Icicle3A.render(f);
		    Icicle3B.render(f);
		    Icicle3C.render(f);
		    Icicle3D.render(f);
		    Icicle4A.render(f);
		    Icicle4B.render(f);
		}
		
		CraftingBottom.render(f);
		CraftingBotSlab.render(f);
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
		render(0.0625F);
		glPopMatrix();
		
		TileEntityFurnacePillar pillarTile = (TileEntityFurnacePillar) tile;
		EntityItem citem = new EntityItem(tile.worldObj);
		
		glPushMatrix();
		
			glTranslated(x+0.5D, y, z+0.5D);
			glRotatef(90F * (tile.worldObj.getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord) - 2), 0F, 1F, 0F);
			
			//Input
			if(pillarTile.getStackInSlot(0) != null)
			{
				glPushMatrix();
					citem.hoverStart = 0F;
					citem.setEntityItemStack(pillarTile.getStackInSlot(0));
					resultRenderer.render(citem, 0F, 1.125F, 0F, false);
				glPopMatrix();
			}
		
			//Output
			if(pillarTile.getStackInSlot(2) != null)
			{
				glPushMatrix();
					glTranslatef(0F, 1.75F, 0F);
					citem.hoverStart = 0F;
					citem.setEntityItemStack(pillarTile.getStackInSlot(2));
					resultRenderer.render(citem, 0F, 0F, 0F, false);
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
				itemRenderer.render(citem, 0F, 0.3F, 0F, false);
			}
		glPopMatrix();
		
		if(pillarTile.showNum)
		{
			glPushMatrix();
			glTranslated(x + 0.5D, y, z + 0.5D);

			if(pillarTile.getStackInSlot(0) != null)
			{
				glDisable(GL_LIGHTING);
				RenderingHelper.renderFloatingTextWithBackground(0, 1.425F, 0, 0.4F, ""+pillarTile.getStackInSlot(0).stackSize, Color.white.getRGB(), new Color(0F, 0F, 0F, 0.5F));
				glEnable(GL_LIGHTING);
			}
			if(pillarTile.getStackInSlot(1) != null)
			{
				glDisable(GL_LIGHTING);
				RenderingHelper.renderFloatingTextWithBackground(0, 0.7F, 0, 0.4F, ""+pillarTile.getStackInSlot(1).stackSize, Color.white.getRGB(), new Color(0F, 0F, 0F, 0.5F));
				glEnable(GL_LIGHTING);
			}
			if(pillarTile.getStackInSlot(2) != null)
			{
				glDisable(GL_LIGHTING);
				RenderingHelper.renderFloatingTextWithBackground(0, 2.15F, 0, 0.4F, ""+pillarTile.getStackInSlot(2).stackSize, Color.white.getRGB(), new Color(0F, 0F, 0F, 0.5F));
				glEnable(GL_LIGHTING);
			}
			glPopMatrix();
		}
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		glPushMatrix();
		glPushAttrib(GL_ENABLE_BIT);
		glEnable(GL_DEPTH_TEST);
		glTranslated(0, 1.0D, 0);
		glRotatef(180F, 1F, 0F, 0F);
		glRotatef(90F, 0F, 1F, 0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_FURNACEPILLAR);
		render(0.0625F);
		glPopAttrib();
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
