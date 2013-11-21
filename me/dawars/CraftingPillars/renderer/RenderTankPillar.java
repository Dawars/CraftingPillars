package me.dawars.CraftingPillars.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.util.Random;

import me.dawars.CraftingPillars.Blobs;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityTankPillar;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderTankPillar extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	private ResourceLocation TEXTURE_TANKPILLAR;
	
	public static ModelBase model = new ModelBase()
	{
		
	};
	
	private ModelRenderer pillarbottom;
	private ModelRenderer pillartop;
	private ModelRenderer Corner1;
	private ModelRenderer Corner2;
	private ModelRenderer Corner3;
	private ModelRenderer Corner4;
	private ModelRenderer BottomTank;
	private ModelRenderer TopTank;
	private ModelRenderer GlassPane1;
	private ModelRenderer GlassPane2;
	private ModelRenderer GlassPane4;
	private ModelRenderer GlassPane3;
	
	private ModelRenderer Valve1;
	private ModelRenderer Valve2;
	private ModelRenderer Valve3;
	private ModelRenderer Valve4;
	
	private ModelRenderer Valve;
	

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
	
	public RenderTankPillar()
	{
		if(CraftingPillars.winter)
			TEXTURE_TANKPILLAR = new ResourceLocation(CraftingPillars.id + ":textures/models/tankPillarFrozen.png");
		else
			TEXTURE_TANKPILLAR = new ResourceLocation(CraftingPillars.id + ":textures/models/tankPillar.png");
		
		model.textureWidth = 128;
		model.textureHeight = 64;
		
		pillarbottom = new ModelRenderer(model, 0, 18);
		pillarbottom.addBox(-7F, 0F, -7F, 14, 1, 14);
		pillarbottom.setRotationPoint(0F, 21F, 0F);
		pillarbottom.setTextureSize(128, 64);
		pillarbottom.mirror = true;
		setRotation(pillarbottom, 0F, 0F, 0F);
		pillartop = new ModelRenderer(model, 0, 18);
		pillartop.addBox(-7F, 0F, -7F, 14, 1, 14);
		pillartop.setRotationPoint(0F, 10F, 0F);
		pillartop.setTextureSize(128, 64);
		pillartop.mirror = true;
		setRotation(pillartop, 0F, 0F, 0F);
		
		Corner1 = new ModelRenderer(model, 64, -2);
		Corner1.addBox(5F, 0F, 5F, 2, 10, 2);
		Corner1.setRotationPoint(0F, 11F, 0F);
		Corner1.setTextureSize(128, 64);
		Corner1.mirror = false;
		setRotation(Corner1, 0F, 0F, 0F);
		Corner2 = new ModelRenderer(model, 48, 8);
		Corner2.addBox(-7F, 0F, -7F, 2, 10, 2);
		Corner2.setRotationPoint(0F, 11F, 0F);
		Corner2.setTextureSize(128, 64);
		setRotation(Corner2, 0F, 0F, 0F);
		Corner2.mirror = false;
		Corner3 = new ModelRenderer(model, 56, -2);
		Corner3.addBox(5F, 0F, -7F, 2, 10, 2);
		Corner3.setRotationPoint(0F, 11F, 0F);
		Corner3.setTextureSize(128, 64);
		Corner3.mirror = false;
		setRotation(Corner3, 0F, 0F, 0F);
		Corner4 = new ModelRenderer(model, 48, -2);
		Corner4.addBox(-7F, 0F, 5F, 2, 10, 2);
		Corner4.setRotationPoint(0F, 11F, 0F);
		Corner4.setTextureSize(128, 64);
		Corner4.mirror = false;
		setRotation(Corner4, 0F, 0F, 0F);
		BottomTank = new ModelRenderer(model, 64, 36);
		BottomTank.addBox(-8F, 0F, -8F, 16, 2, 16);
		BottomTank.setRotationPoint(0F, 22F, 0F);
		BottomTank.setTextureSize(128, 64);
		setRotation(BottomTank, 0F, 0F, 0F);
		BottomTank.mirror = false;
		TopTank = new ModelRenderer(model, 64, 18);
		TopTank.addBox(-8F, 0F, -8F, 16, 2, 16);
		TopTank.setRotationPoint(0F, 8F, 0F);
		TopTank.setTextureSize(128, 64);
		TopTank.mirror = true;
		setRotation(TopTank, 0F, 0F, 0F);
		GlassPane1 = new ModelRenderer(model, 108, 54);
		GlassPane1.addBox(-5F, -4F, -6F, 10, 10, 0);
		GlassPane1.setRotationPoint(0F, 15F, 0F);
		GlassPane1.setTextureSize(128, 64);
		GlassPane1.mirror = true;
		setRotation(GlassPane1, 0F, 0F, 0F);
		GlassPane2 = new ModelRenderer(model, 108, 54);
		GlassPane2.addBox(-5F, -4F, 6F, 10, 10, 0);
		GlassPane2.setRotationPoint(0F, 15F, 0F);
		GlassPane2.setTextureSize(128, 64);
		GlassPane2.mirror = true;
		setRotation(GlassPane2, 0F, 0F, 0F);
		GlassPane4 = new ModelRenderer(model, 108, 54);
		GlassPane4.addBox(-5F, -4F, 6F, 10, 10, 0);
		GlassPane4.setRotationPoint(0F, 15F, 0F);
		GlassPane4.setTextureSize(128, 64);
		GlassPane4.mirror = true;
		setRotation(GlassPane4, 0F, 1.570796F, 0F);
		GlassPane3 = new ModelRenderer(model, 108, 54);
		GlassPane3.addBox(-5F, -4F, -6F, 10, 10, 0);
		GlassPane3.setRotationPoint(0F, 15F, 0F);
		GlassPane3.setTextureSize(128, 64);
		GlassPane3.mirror = true;
		setRotation(GlassPane3, 0F, 1.570796F, 0F);
		
		Valve1 = new ModelRenderer(model, 0, 0);
		Valve1.addBox(5F, -3F, -3F, 3, 6, 6);
		Valve1.setRotationPoint(0F, 16F, 0F);
		Valve1.setTextureSize(128, 64);
		Valve1.mirror = true;
		setRotation(Valve1, 0F, 3.141593F, 0F);
		
		Valve2 = new ModelRenderer(model, 0, 0);
		Valve2.addBox(5F, -3F, -3F, 3, 6, 6);
		Valve2.setRotationPoint(0F, 16F, 0F);
		Valve2.setTextureSize(128, 64);
		Valve2.mirror = true;
		setRotation(Valve2, 0F, 1.570796F, 0F);
		
		Valve3 = new ModelRenderer(model, 0, 0);
		Valve3.addBox(5F, -3F, -3F, 3, 6, 6);
		Valve3.setRotationPoint(0F, 16F, 0F);
		Valve3.setTextureSize(128, 64);
		Valve3.mirror = true;
		setRotation(Valve3, 0F, 0F, 0F);
		
		Valve4 = new ModelRenderer(model, 0, 0);
		Valve4.addBox(5F, -3F, -3F, 3, 6, 6);
		Valve4.setRotationPoint(0F, 16F, 0F);
		Valve4.setTextureSize(128, 64);
		Valve4.mirror = true;
		setRotation(Valve4, 0F, -1.570796F, 0F);
		
		Icicle1A = new ModelRenderer(model, 122, 38);
		Icicle1A.addBox(0F, 0F, 0F, 2, 1, 1);
		Icicle1A.setRotationPoint(6F, 10F, 7F);
		Icicle1A.setTextureSize(128, 64);
		Icicle1A.mirror = true;
		setRotation(Icicle1A, 0F, 0F, 0F);
		Icicle1B = new ModelRenderer(model, 122, 40);
		Icicle1B.addBox(0F, 0F, 0F, 1, 2, 1);
		Icicle1B.setRotationPoint(7F, 11F, 7F);
		Icicle1B.setTextureSize(128, 64);
		Icicle1B.mirror = true;
		setRotation(Icicle1B, 0F, 0F, 0F);
		Icicle1C = new ModelRenderer(model, 116, 52);
		Icicle1C.addBox(0F, 0F, 0F, 1, 1, 2);
		Icicle1C.setRotationPoint(7F, 10F, 5F);
		Icicle1C.setTextureSize(128, 64);
		Icicle1C.mirror = true;
		setRotation(Icicle1C, 0F, 0F, 0F);
		Icicle2A = new ModelRenderer(model, 122, 60);
		Icicle2A.addBox(0F, 0F, 0F, 1, 2, 2);
		Icicle2A.setRotationPoint(7F, 10F, -7F);
		Icicle2A.setTextureSize(128, 64);
		Icicle2A.mirror = true;
		setRotation(Icicle2A, 0F, 0F, 0F);
		Icicle2B = new ModelRenderer(model, 122, 38);
		Icicle2B.addBox(0F, 0F, 0F, 2, 1, 1);
		Icicle2B.setRotationPoint(6F, 10F, -8F);
		Icicle2B.setTextureSize(128, 64);
		Icicle2B.mirror = true;
		setRotation(Icicle2B, 0F, 0F, 0F);
		Icicle2C = new ModelRenderer(model, 122, 44);
		Icicle2C.addBox(0F, 0F, 0F, 1, 1, 1);
		Icicle2C.setRotationPoint(7F, 12F, -7F);
		Icicle2C.setTextureSize(128, 64);
		Icicle2C.mirror = true;
		setRotation(Icicle2C, 0F, 0F, 0F);
		Icicle3A = new ModelRenderer(model, 106, 50);
		Icicle3A.addBox(0F, 0F, 0F, 1, 1, 3);
		Icicle3A.setRotationPoint(-8F, 10F, -8F);
		Icicle3A.setTextureSize(128, 64);
		Icicle3A.mirror = true;
		setRotation(Icicle3A, 0F, 0F, 0F);
		Icicle3B = new ModelRenderer(model, 101, 50);
		Icicle3B.addBox(0F, 0F, 0F, 1, 1, 2);
		Icicle3B.setRotationPoint(-8F, 11F, -8F);
		Icicle3B.setTextureSize(128, 64);
		Icicle3B.mirror = true;
		setRotation(Icicle3B, 0F, 0F, 0F);
		Icicle3C = new ModelRenderer(model, 106, 50);
		Icicle3C.addBox(0F, 0F, 0F, 1, 1, 1);
		Icicle3C.setRotationPoint(-7F, 10F, -8F);
		Icicle3C.setTextureSize(128, 64);
		Icicle3C.mirror = true;
		setRotation(Icicle3C, 0F, 0F, 0F);
		Icicle3D = new ModelRenderer(model, 106, 46);
		Icicle3D.addBox(0F, 0F, 0F, 1, 1, 1);
		Icicle3D.setRotationPoint(-8F, 12F, -8F);
		Icicle3D.setTextureSize(128, 64);
		Icicle3D.mirror = true;
		setRotation(Icicle3D, 0F, 0F, 0F);
		Icicle4A = new ModelRenderer(model, 122, 35);
		Icicle4A.addBox(0F, 0F, 0F, 1, 1, 1);
		Icicle4A.setRotationPoint(-8F, 10F, 7F);
		Icicle4A.setTextureSize(128, 64);
		Icicle4A.mirror = true;
		setRotation(Icicle4A, 0F, 0F, 0F);
		Icicle4B = new ModelRenderer(model, 117, 43);
		Icicle4B.addBox(0F, 0F, 0F, 1, 2, 1);
		Icicle4B.setRotationPoint(-7F, 10F, 7F);
		Icicle4B.setTextureSize(128, 64);
		Icicle4B.mirror = true;
		setRotation(Icicle4B, 0F, 0F, 0F);
	}
	
	public void render(TileEntity tile, float f)
	{
		pillarbottom.render(f);
		pillartop.render(f);
		Corner1.render(f);
		Corner2.render(f);
		Corner3.render(f);
		Corner4.render(f);
		BottomTank.render(f);
		TopTank.render(f);
		GlassPane1.render(f);
		GlassPane2.render(f);
		GlassPane4.render(f);
		GlassPane3.render(f);
		
		
		if(CraftingPillars.winter)
		{
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(CraftingPillars.id + ":textures/models/furnacePillarFrozen.png"));
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
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_TANKPILLAR);

		}
		
		if(tile == null)
			return;
		
		if(tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord + 1) != null &&
		// (
		tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord + 1) instanceof IFluidTank /*
																										 * ||
																										 * tile
																										 * .
																										 * worldObj
																										 * .
																										 * getBlockTileEntity
																										 * (
																										 * tile
																										 * .
																										 * xCoord
																										 * ,
																										 * tile
																										 * .
																										 * yCoord
																										 * ,
																										 * tile
																										 * .
																										 * zCoord
																										 * +
																										 * 1
																										 * )
																										 * instanceof
																										 * ITankContainer
																										 * )
																										 */)
		{
			Valve2.render(1.0F);
		}
		
		if(tile.worldObj.getBlockTileEntity(tile.xCoord + 1, tile.yCoord, tile.zCoord) != null && /*
																								 * (
																								 * tile
																								 * .
																								 * worldObj
																								 * .
																								 * getBlockTileEntity
																								 * (
																								 * tile
																								 * .
																								 * xCoord
																								 * +
																								 * 1
																								 * ,
																								 * tile
																								 * .
																								 * yCoord
																								 * ,
																								 * tile
																								 * .
																								 * zCoord
																								 * )
																								 * instanceof
																								 * ITankContainer
																								 * ||
																								 */tile.worldObj.getBlockTileEntity(tile.xCoord + 1, tile.yCoord, tile.zCoord) instanceof IFluidTank
		// )
		)
		{
			Valve3.render(1.0F);
		}
		
		if(tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord - 1) != null && /*
																								 * (
																								 * tile
																								 * .
																								 * worldObj
																								 * .
																								 * getBlockTileEntity
																								 * (
																								 * tile
																								 * .
																								 * xCoord
																								 * ,
																								 * tile
																								 * .
																								 * yCoord
																								 * ,
																								 * tile
																								 * .
																								 * zCoord
																								 * -
																								 * 1
																								 * )
																								 * instanceof
																								 * ITankContainer
																								 * ||
																								 */
		tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord - 1) instanceof IFluidTank)
		// )
		{
			Valve4.render(1.0F);
		}
		
		if(tile.worldObj.getBlockTileEntity(tile.xCoord - 1, tile.yCoord, tile.zCoord) != null && /*
																								 * (
																								 * tile
																								 * .
																								 * worldObj
																								 * .
																								 * getBlockTileEntity
																								 * (
																								 * tile
																								 * .
																								 * xCoord
																								 * -
																								 * 1
																								 * ,
																								 * tile
																								 * .
																								 * yCoord
																								 * ,
																								 * tile
																								 * .
																								 * zCoord
																								 * )
																								 * instanceof
																								 * ITankContainer
																								 * ||
																								 */tile.worldObj.getBlockTileEntity(tile.xCoord - 1, tile.yCoord, tile.zCoord) instanceof IFluidTank
		// )
		)
		{
			Valve1.render(1.0F);
		}
		
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		glPushMatrix();
			glTranslatef(0, 1, 0);
			glRotatef(180F, 1F, 0F, 0F);
			
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_TANKPILLAR);
			glPushMatrix();
				glScalef(1F/16F, 1F/16F, 1F/16F);
				render(null, 1F);
			glPopMatrix();
			Valve2.render(0.0625F);
		glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		glPushMatrix();
		glTranslated(x, y, z);
		glTranslatef(0.5F, 1.5F, 0.5F);
		glScalef(0.0625F, 0.0625F, 0.0625F);
		glRotatef(180F, 1F, 0F, 0F);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_TANKPILLAR);
		render(tile, 1F);
		glPopMatrix();
		
		TileEntityTankPillar tank = ((TileEntityTankPillar) tile);

		if(tank.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid == null)
			return;
		
//		ResourceLocation TEXTURE_LIQUID = new ResourceLocation(tank.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.getFluid().getFlowingIcon().);

		
		glPushMatrix();
		glTranslated(x, y, z);
		glPushAttrib(GL_ENABLE_BIT);
		glDisable(GL_LIGHTING);
		glTranslatef(0.005F, 0.005F, 0.005F);
		glScalef(0.99F, 0.99F, 0.99F);
		
		float[][][] field = Blobs.fieldStrength(tank.blobs);
		for(int i = 0; i < 16; i++)
			for(int j = 0; j < 16; j++)
				for(int k = 0; k < 16; k++)
					if((int)field[i][j][k] > 0 &&
						(i != 0 && (int)field[i-1][j][k] != 0
							&& i != 15 && (int)field[i+1][j][k] != 0
							&& j != 0 && (int)field[i][j-1][k] != 0
							&& j != 15 && (int)field[i][j+1][k] != 0
							&& k != 0 && (int)field[i][j][k-1] != 0
							&& k != 15 && (int)field[i][j][k+1] != 0))
					{
						field[i][j][k] = 0F;
					}
		
		ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(BLOCK_TEXTURE);
		
		//change texture coord according to icon coords
		
		glBegin(GL_QUADS);
		
		/*glNormal3f(0F, 1F, 0F);
		glTexCoord2f(0F, 0F);
		glVertex3f(0F, 1.25F, 0F);
		glTexCoord2f(0F, 1F);
		glVertex3f(0F, 1.25F, 1F);
		glTexCoord2f(1F, 1F);
		glVertex3f(1F, 1.25F, 1F);
		glTexCoord2f(1F, 0F);
		glVertex3f(1F, 1.25F, 0F);*/
		
		for(int i = 0; i < 16; i++)
			for(int j = 0; j < 16; j++)
				for(int k = 0; k < 16; k++)
					if((int)field[i][j][k] > 0)
					{
						Icon icon = tank.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.getFluid().getStillIcon();
						int tx = tank.texIndieces[i][j][k]%16;
						int ty = tank.texIndieces[i][j][k]/16;
						if(tx < 0 || ty < 0 || tx >= 16 || ty >= 16)
							System.out.println(tx+" "+ty);
						
						//System.out.println(16*icon.getMinU()+" "+16*icon.getMinV());
						
						float minX = (int)(tx+icon.getMinU()*512F)/512F;
						float maxX = (int)(tx+1+icon.getMinU()*512F)/512F;
						float minY = (int)(ty+icon.getMinV()*256F)/256F;
						float maxY = (int)(ty+1+icon.getMinV()*256F)/256F;
						
						if(j == 15 || (int)field[i][j+1][k] == 0)
						{
							glTexCoord2f(minX, minY);
							glVertex3f((i)/16F, (j+1)/16F, (k)/16F);
							glTexCoord2f(minX, maxY);
							glVertex3f((i)/16F, (j+1)/16F, (k+1)/16F);
							glTexCoord2f(maxX, maxY);
							glVertex3f((i+1)/16F, (j+1)/16F, (k+1)/16F);
							glTexCoord2f(maxX, minY);
							glVertex3f((i+1)/16F, (j+1)/16F, (k)/16F);
						}
						if(j == 0 || (int)field[i][j-1][k] == 0)
						{
							glTexCoord2f(minX, minY);
							glVertex3f((i)/16F, (j)/16F, (k+1)/16F);
							glTexCoord2f(minX, maxY);
							glVertex3f((i)/16F, (j)/16F, (k)/16F);
							glTexCoord2f(maxX, maxY);
							glVertex3f((i+1)/16F, (j)/16F, (k)/16F);
							glTexCoord2f(maxX, minY);
							glVertex3f((i+1)/16F, (j)/16F, (k+1)/16F);
						}
						if(k == 15 || (int)field[i][j][k+1] == 0)
						{
							glTexCoord2f(minX, minY);
							glVertex3f((i)/16F, (j+1)/16F, (k+1)/16F);
							glTexCoord2f(minX, maxY);
							glVertex3f((i)/16F, (j)/16F, (k+1)/16F);
							glTexCoord2f(maxX, maxY);
							glVertex3f((i+1)/16F, (j)/16F, (k+1)/16F);
							glTexCoord2f(maxX, minY);
							glVertex3f((i+1)/16F, (j+1)/16F, (k+1)/16F);
						}
						if(k == 0 || (int)field[i][j][k-1] == 0)
						{
							glTexCoord2f(minX, minY);
							glVertex3f((i+1)/16F, (j+1)/16F, (k)/16F);
							glTexCoord2f(minX, maxY);
							glVertex3f((i+1)/16F, (j)/16F, (k)/16F);
							glTexCoord2f(maxX, maxY);
							glVertex3f((i)/16F, (j)/16F, (k)/16F);
							glTexCoord2f(maxX, minY);
							glVertex3f((i)/16F, (j+1)/16F, (k)/16F);
						}
						if(i == 15 || (int)field[i+1][j][k] == 0)
						{
							glTexCoord2f(minX, minY);
							glVertex3f((i+1)/16F, (j+1)/16F, (k+1)/16F);
							glTexCoord2f(minX, maxY);
							glVertex3f((i+1)/16F, (j)/16F, (k+1)/16F);
							glTexCoord2f(maxX, maxY);
							glVertex3f((i+1)/16F, (j)/16F, (k)/16F);
							glTexCoord2f(maxX, minY);
							glVertex3f((i+1)/16F, (j+1)/16F, (k)/16F);
						}
						if(i == 0 || (int)field[i-1][j][k] == 0)
						{
							glTexCoord2f(minX, minY);
							glVertex3f((i)/16F, (j)/16F, (k+1)/16F);
							glTexCoord2f(minX, maxY);
							glVertex3f((i)/16F, (j+1)/16F, (k+1)/16F);
							glTexCoord2f(maxX, maxY);
							glVertex3f((i)/16F, (j+1)/16F, (k)/16F);
							glTexCoord2f(maxX, minY);
							glVertex3f((i)/16F, (j)/16F, (k)/16F);
						}
					}
		
		glEnd();
		glPopAttrib();
		glPopMatrix();
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
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
		return CraftingPillars.tankPillarRenderID;
	}
}