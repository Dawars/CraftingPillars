package me.dawars.CraftingPillars.renderer;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslated;
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

public class RenderPresent extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{

	private ResourceLocation TEXTURE_PRESENT = new ResourceLocation(CraftingPillars.id + ":textures/models/present.png");
	
	public static ModelBase model = new ModelBase()
	{
		
	};

	private ModelRenderer PresentTop;
	private ModelRenderer PresentBottom;
	
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
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void render(float f)
	{
		PresentBottom.render(f);
		PresentTop.render(f);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		glPushMatrix();
		glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
		glRotatef(180F, 1F, 0F, 0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_PRESENT);
			render(0.0625F);
		glPopMatrix();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		glPushMatrix();
		glTranslated(0, 1.0D, 0);
		glRotatef(180F, 1F, 0F, 0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_PRESENT);
		render(0.0625F);
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
