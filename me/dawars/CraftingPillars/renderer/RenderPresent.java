package me.dawars.CraftingPillars.renderer;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslated;

import java.awt.Color;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityChristmasPresent;
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

public class RenderPresent extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	private ResourceLocation TEXTURE = new ResourceLocation(CraftingPillars.id + ":textures/models/present.png");
	private ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(CraftingPillars.id + ":textures/models/presentOverlay.png");
	
	public static int[] colors = new int[]{
		Color.green.getRGB(),
		Color.red.getRGB(),
		Color.blue.getRGB(),
		Color.yellow.getRGB()
	};
	
	public static ModelBase model = new ModelBase()
	{
		
	};

	private ModelRenderer PresentTop;
	private ModelRenderer PresentBottom;
	
	private ModelRenderer PresentTop2;
	private ModelRenderer PresentBottom2;
	
	public RenderPresent(){
		model.textureWidth = 64;
		model.textureHeight = 64;

		PresentTop = new ModelRenderer(model, 0, 0);
		PresentTop.addBox(0F, 0F, 0F, 16, 5, 16);
		PresentTop.setRotationPoint(-8F, 8F, -8F);
		PresentTop.setTextureSize(64, 64);
		PresentTop.mirror = true;
		setRotation(PresentTop, 0F, 0F, 0F);
		PresentBottom = new ModelRenderer(model, 5, 34);
		PresentBottom.addBox(0F, 0F, 0F, 14, 11, 14);
		PresentBottom.setRotationPoint(-7F, 13F, -7F);
		PresentBottom.setTextureSize(64, 64);
		PresentBottom.mirror = true;
		setRotation(PresentBottom, 0F, 0F, 0F);
		
		PresentTop2 = new ModelRenderer(model, 1, 0);
		PresentTop2.addBox(0F, 0F, 0F, 14, 2, 16);
		PresentTop2.setRotationPoint(-7F, 17F, -8F);
		PresentTop2.setTextureSize(64, 64);
		PresentTop2.mirror = true;
		setRotation(PresentTop2, 0F, 0F, 0F);
		PresentBottom2 = new ModelRenderer(model, 7, 34);
		PresentBottom2.addBox(0F, 0F, 0F, 12, 5, 14);
		PresentBottom2.setRotationPoint(-6F, 19F, -7F);
		PresentBottom2.setTextureSize(64, 64);
		PresentBottom2.mirror = true;
		setRotation(PresentBottom2, 0F, 0F, 0F);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void render(float f, Color color1, Color color2)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		glColor3f((float)color1.getRed()/255F, (float)color1.getGreen()/255F, (float)color1.getBlue()/255F);
		PresentBottom.render(f);
		PresentTop.render(f);
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_OVERLAY);
		glColor3f((float)color2.getRed()/255F, (float)color2.getGreen()/255F, (float)color2.getBlue()/255F);
		PresentBottom.render(f);
		PresentTop.render(f);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		TileEntityChristmasPresent present = (TileEntityChristmasPresent)tile;
		glPushMatrix();
		glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
		glRotatef(180F, 1F, 0F, 0F);
		render(0.0625F, new Color(colors[present.color*2]), new Color(colors[present.color*2+1]));
		glPopMatrix();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		glPushMatrix();
		glTranslated(0, 1.0D, 0);
		glRotatef(180F, 1F, 0F, 0F);
		render(0.0625F, Color.green, Color.red);
		glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return CraftingPillars.PresentRenderID;
	}
}
