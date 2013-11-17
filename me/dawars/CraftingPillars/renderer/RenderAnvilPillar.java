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
	
	private ModelRenderer Icicle1A;
	private ModelRenderer Icicle1B;
    private ModelRenderer Icicle1C;
    private ModelRenderer Icicle2A;
    private ModelRenderer Icicle2B;
    private ModelRenderer Icicle2C;
	
    private ModelRenderer Icicle3A;
    private ModelRenderer Icicle3B;
    private ModelRenderer Icicle4A;
    private ModelRenderer Icicle4B;
    private ModelRenderer Icicle5A;
    private ModelRenderer Icicle5B;
    private ModelRenderer Icicle5C;
    private ModelRenderer Icicle6A;
    private ModelRenderer Icicle6B;
    private ModelRenderer Icicle6C;
    private ModelRenderer Icicle7A;
    private ModelRenderer Icicle7B;
    private ModelRenderer Icicle7C;
    private ModelRenderer Icicle8A;
    private ModelRenderer Icicle8B;
    private ModelRenderer Icicle8C;
    private ModelRenderer Icicle8D;
    
	private ResourceLocation TEXTURE_ANVILPILLAR;
	
	public static ModelBase model = new ModelBase()
	{
		
	};
	
	private Random random;
	private RenderingHelper.ItemRender itemRenderer;
	
	public RenderAnvilPillar()
	{
		random = new Random();
		itemRenderer = new RenderingHelper.ItemRender(false, true);
		
		if(CraftingPillars.christmas)
			TEXTURE_ANVILPILLAR = new ResourceLocation(CraftingPillars.id + ":textures/models/anvilPillarFrozen.png");
		else
			TEXTURE_ANVILPILLAR = new ResourceLocation(CraftingPillars.id + ":textures/models/anvilPillar.png");
		
		
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
	      WorkbenchSlab = new ModelRenderer(model, 48, 19);
	      WorkbenchSlab.addBox(-4F, 0F, 0F, 8, 1, 14);
	      WorkbenchSlab.setRotationPoint(0F, 11F, -7F);
	      WorkbenchSlab.setTextureSize(128, 64);
	      WorkbenchSlab.mirror = true;
	      setRotation(WorkbenchSlab, 0F, 0F, 0F);
	      WorkbenchTop = new ModelRenderer(model, 68, 0);
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
	      
	    //Winter
			Icicle1A = new ModelRenderer(model, 122, 60);
			Icicle1A.addBox(0F, 0F, 0F, 1, 2, 2);
			Icicle1A.setRotationPoint(4F, 11F, -5F);
			Icicle1A.setTextureSize(128, 64);
			Icicle1A.mirror = true;
			setRotation(Icicle1A, 0F, 0F, 0F);
			Icicle1B = new ModelRenderer(model, 124, 58);
			Icicle1B.addBox(0F, 0F, 0F, 1, 1, 1);
			Icicle1B.setRotationPoint(4F, 11F, -3F);
			Icicle1B.setTextureSize(128, 64);
			Icicle1B.mirror = true;
			setRotation(Icicle1B, 0F, 0F, 0F);
			Icicle1C = new ModelRenderer(model, 124, 56);
			Icicle1C.addBox(0F, 0F, 0F, 1, 1, 1);
			Icicle1C.setRotationPoint(4F, 13F, -4F);
			Icicle1C.setTextureSize(128, 64);
			Icicle1C.mirror = true;
			setRotation(Icicle1C, 0F, 0F, 0F);
			
			Icicle2A = new ModelRenderer(model, 122, 50);
			Icicle2A.addBox(0F, 0F, 0F, 1, 2, 2);
			Icicle2A.setRotationPoint(4F, 11F, 0F);
			Icicle2A.setTextureSize(128, 64);
			Icicle2A.mirror = true;
			setRotation(Icicle2A, 0F, 0F, 0F);
			Icicle2B = new ModelRenderer(model, 124, 47);
			Icicle2B.addBox(0F, 0F, 0F, 1, 2, 1);
			Icicle2B.setRotationPoint(4F, 13F, 0F);
			Icicle2B.setTextureSize(128, 64);
			Icicle2B.mirror = true;
			setRotation(Icicle2B, 0F, 0F, 0F);
			Icicle2C = new ModelRenderer(model, 124, 54);
			Icicle2C.addBox(0F, 0F, 0F, 1, 1, 1);
			Icicle2C.setRotationPoint(4F, 11F, -1F);
			Icicle2C.setTextureSize(128, 64);
			Icicle2C.mirror = true;
			setRotation(Icicle2C, 0F, 0F, 0F);

			Icicle3A = new ModelRenderer(model, 120, 43);
			Icicle3A.addBox(0F, 0F, 0F, 1, 1, 3);
			Icicle3A.setRotationPoint(4F, 11F, 3F);
			Icicle3A.setTextureSize(128, 64);
			Icicle3A.mirror = true;
			setRotation(Icicle3A, 0F, 0F, 0F);
			Icicle3B = new ModelRenderer(model, 124, 40);
			Icicle3B.addBox(0F, 0F, 0F, 1, 2, 1);
			Icicle3B.setRotationPoint(4F, 12F, 4F);
			Icicle3B.setTextureSize(128, 64);
			Icicle3B.mirror = true;
			setRotation(Icicle3B, 0F, 0F, 0F);
			
			Icicle4A = new ModelRenderer(model, 122, 38);
			Icicle4A.addBox(0F, 0F, 0F, 2, 1, 1);
			Icicle4A.setRotationPoint(1F, 11F, 7F);
			Icicle4A.setTextureSize(128, 64);
			Icicle4A.mirror = true;
			setRotation(Icicle4A, 0F, 0F, 0F);
			Icicle4B = new ModelRenderer(model, 124, 36);
			Icicle4B.addBox(0F, 0F, 0F, 1, 1, 1);
			Icicle4B.setRotationPoint(2F, 12F, 7F);
			Icicle4B.setTextureSize(128, 64);
			Icicle4B.mirror = true;
			setRotation(Icicle4B, 0F, 0F, 0F);
			
			Icicle5A = new ModelRenderer(model, 114, 61);
			Icicle5A.addBox(0F, 0F, 0F, 3, 2, 1);
			Icicle5A.setRotationPoint(-3F, 11F, 7F);
			Icicle5A.setTextureSize(128, 64);
			Icicle5A.mirror = true;
			setRotation(Icicle5A, 0F, 0F, 0F);
			Icicle5B = new ModelRenderer(model, 116, 59);
			Icicle5B.addBox(0F, 0F, 0F, 2, 1, 1);
			Icicle5B.setRotationPoint(-3F, 13F, 7F);
			Icicle5B.setTextureSize(128, 64);
			Icicle5B.mirror = true;
			setRotation(Icicle5B, 0F, 0F, 0F);
			Icicle5C = new ModelRenderer(model, 120, 56);
			Icicle5C.addBox(0F, 0F, 0F, 1, 2, 1);
			Icicle5C.setRotationPoint(-2F, 14F, 7F);
			Icicle5C.setTextureSize(128, 64);
			Icicle5C.mirror = true;
			setRotation(Icicle5C, 0F, 0F, 0F);
			
			Icicle6A = new ModelRenderer(model, 114, 54);
			Icicle6A.addBox(0F, 0F, 0F, 4, 1, 1);
			Icicle6A.setRotationPoint(-3F, 11F, -8F);
			Icicle6A.setTextureSize(128, 64);
			Icicle6A.mirror = true;
			setRotation(Icicle6A, 0F, 0F, 0F);
			Icicle6B = new ModelRenderer(model, 116, 52);
			Icicle6B.addBox(0F, 0F, 0F, 2, 1, 1);
			Icicle6B.setRotationPoint(-2F, 12F, -8F);
			Icicle6B.setTextureSize(128, 64);
			Icicle6B.mirror = true;
			setRotation(Icicle6B, 0F, 0F, 0F);
			Icicle6C = new ModelRenderer(model, 118, 50);
			Icicle6C.addBox(0F, 0F, 0F, 1, 1, 1);
			Icicle6C.setRotationPoint(-2F, 13F, -8F);
			Icicle6C.setTextureSize(128, 64);
			Icicle6C.mirror = true;
			
			Icicle7A = new ModelRenderer(model, 122, 60);
			Icicle7A.addBox(0F, 0F, 0F, 1, 2, 2);
			Icicle7A.setRotationPoint(-5F, 11F, -5F);
			Icicle7A.setTextureSize(128, 64);
			Icicle7A.mirror = true;
			setRotation(Icicle7A, 0F, 0F, 0F);
			Icicle7B = new ModelRenderer(model, 124, 58);
			Icicle7B.addBox(0F, 0F, 0F, 1, 1, 1);
			Icicle7B.setRotationPoint(-5F, 11F, -3F);
			Icicle7B.setTextureSize(128, 64);
			Icicle7B.mirror = true;
			setRotation(Icicle7B, 0F, 0F, 0F);
			Icicle7C = new ModelRenderer(model, 124, 56);
			Icicle7C.addBox(0F, 0F, 0F, 1, 1, 1);
			Icicle7C.setRotationPoint(-5F, 13F, -4F);
			Icicle7C.setTextureSize(128, 64);
			Icicle7C.mirror = true;
			setRotation(Icicle7C, 0F, 0F, 0F);
			
			Icicle8A = new ModelRenderer(model, 122, 50);
			Icicle8A.addBox(0F, 0F, 0F, 1, 2, 2);
			Icicle8A.setRotationPoint(-5F, 11F, 2F);
			Icicle8A.setTextureSize(128, 64);
			Icicle8A.mirror = true;
			setRotation(Icicle8A, 0F, 0F, 0F);
			Icicle8B = new ModelRenderer(model, 124, 47);
			Icicle8B.addBox(0F, 0F, 0F, 1, 2, 1);
			Icicle8B.setRotationPoint(-5F, 13F, 2F);
			Icicle8B.setTextureSize(128, 64);
			Icicle8B.mirror = true;
			setRotation(Icicle8B, 0F, 0F, 0F);
			Icicle8C = new ModelRenderer(model, 124, 54);
			Icicle8C.addBox(0F, 0F, 0F, 1, 1, 1);
			Icicle8C.setRotationPoint(-5F, 11F, 1F);
			Icicle8C.setTextureSize(128, 64);
			Icicle8C.mirror = true;
			setRotation(Icicle8C, 0F, 0F, 0F);
	}
	
	public void render(TileEntity tileentity, float f)
	{
		CraftingBottom.render(f);
		CraftingBotSlab.render(f);
		WorkbenchSlab.render(f);
		WorkbenchTop.render(f);
		Pillar2.render(f);
		if(CraftingPillars.christmas)
		{
			Icicle1A.render(f);
		    Icicle1B.render(f);
		    Icicle1C.render(f);
		    Icicle2A.render(f);
		    Icicle2B.render(f);
		    Icicle2C.render(f);
		    Icicle3A.render(f);
		    Icicle3B.render(f);
		    Icicle4A.render(f);
		    Icicle4B.render(f);
		    Icicle5A.render(f);
		    Icicle5B.render(f);
		    Icicle5C.render(f);
		    Icicle6A.render(f);
		    Icicle6B.render(f);
		    Icicle6C.render(f);
		    Icicle7A.render(f);
		    Icicle7B.render(f);
		    Icicle7C.render(f);
		    Icicle8A.render(f);
		    Icicle8B.render(f);
		    Icicle8C.render(f);
		}
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
