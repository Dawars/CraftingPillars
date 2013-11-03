package me.dawars.CraftingPillars.renderer;

import static org.lwjgl.opengl.GL11.*;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tile.TileEntityTankPillar;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderTankPillar extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	private static final ResourceLocation TEXTURE_FANCY_TANK = new ResourceLocation(CraftingPillars.id + ":textures/models/tankPillar.png");
	
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
	
	public RenderTankPillar()
	{
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
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_FANCY_TANK);
		render(null, 1F);
		Valve2.render(0.0625F);
		glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		/*glPushMatrix();
			glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
			glRotatef(180F, 1F, 0F, 0F);
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_FANCY_TANK);
			render(tile, 0.0625F);
		glPopMatrix();*/
		
		TileEntityTankPillar tank = ((TileEntityTankPillar) tile);
		
		glPushMatrix();
		glPushAttrib(GL_ENABLE_BIT);
		glDisable(GL_LIGHTING);
		
		glTranslated(x, y, z);
		
		/*glColor3f(1, 0, 0);
		
		glBegin(GL_LINES);
		for(int i = 0; i < tank.blobs.size(); i++)
		{
			glVertex3f((float)tank.blobs.get(i).x/16F, (float)tank.blobs.get(i).y/16F + 0.1F, (float)tank.blobs.get(i).z/16F);
			glVertex3f((float)tank.blobs.get(i).x/16F, (float)tank.blobs.get(i).y/16F - 0.1F, (float)tank.blobs.get(i).z/16F);
			
			glVertex3f((float)tank.blobs.get(i).x/16F + 0.1F, (float)tank.blobs.get(i).y/16F, (float)tank.blobs.get(i).z/16F);
			glVertex3f((float)tank.blobs.get(i).x/16F - 0.1F, (float)tank.blobs.get(i).y/16F, (float)tank.blobs.get(i).z/16F);
			
			glVertex3f((float)tank.blobs.get(i).x/16F, (float)tank.blobs.get(i).y/16F, (float)tank.blobs.get(i).z/16F + 0.1F);
			glVertex3f((float)tank.blobs.get(i).x/16F, (float)tank.blobs.get(i).y/16F, (float)tank.blobs.get(i).z/16F - 0.1F);
		}
		glEnd();*/
		
		glColor3f(1, 1, 1);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_FANCY_TANK);
		for(int i = 0; i < tank.blobs.size(); i++)
		{
			glPushMatrix();
			glTranslatef((float)tank.blobs.get(i).x/16F, (float)tank.blobs.get(i).y/16F, (float)tank.blobs.get(i).z/16F);
			glRotatef(180F, 1F, 0F, 0F);
			render(tile, 0.0625F/16F);
			glPopMatrix();
		}
		
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