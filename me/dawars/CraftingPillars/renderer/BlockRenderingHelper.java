package me.dawars.CraftingPillars.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public class BlockRenderingHelper {
	public static void drawBlock(Tessellator t, RenderBlocks renderblocks, Block block, Icon icon) {
		drawFaces(t, renderblocks, block, icon, icon, icon, icon, icon, icon);
	}
	
	public static void drawBlock(RenderBlocks renderblocks, Block block, Icon icon) {
		drawFaces(renderblocks, block, icon, icon, icon, icon, icon, icon);
	}

	public static void drawSideBlockFaces(RenderBlocks renderblocks, Block block, Icon icon, Icon top) {
		drawFaces(renderblocks, block, top, top, icon, icon, icon, icon);
	}

	public static void drawFaces(RenderBlocks renderblocks, Block block, Icon i1, Icon i2, Icon i3, Icon i4, Icon i5, Icon i6) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, i1);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, i2);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, i3);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, i4);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, i5);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, i6);
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
	
	public static void drawFaces(Tessellator tessellator, RenderBlocks renderblocks, Block block, Icon i1, Icon i2, Icon i3, Icon i4, Icon i5, Icon i6) {
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, i1);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, i2);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, i3);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, i4);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, i5);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, i6);
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	protected static int setBrightness(IBlockAccess blockAccess, int i, int j, int k, Block block) {
		int brightness = block.getMixedBrightnessForBlock(blockAccess, i, j, k);

		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess,
				i, j, k));

		float f = 1.0F;

		int l = block.colorMultiplier(blockAccess, i, j, k);
		float f1 = (float) (l >> 16 & 255) / 255.0F;
		float f2 = (float) (l >> 8 & 255) / 255.0F;
		float f3 = (float) (l & 255) / 255.0F;
		float f4;

		if (EntityRenderer.anaglyphEnable) {
			float f5 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			f4 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f5;
			f2 = f4;
			f3 = f6;
		}

		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		return brightness;
	}

	protected static void renderSides(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer, Icon tex) {
		renderSides(world, x, y, z, block, renderer, tex, false);
	}

	protected static void renderSides(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer, Icon tex, boolean allsides) {
		if ((allsides) || (block.shouldSideBeRendered(world, x + 1, y, z, 6)))
			renderer.renderFaceXPos(block, x, y, z, tex);
		if ((allsides) || (block.shouldSideBeRendered(world, x - 1, y, z, 6)))
			renderer.renderFaceXNeg(block, x, y, z, tex);
		if ((allsides) || (block.shouldSideBeRendered(world, x, y, z + 1, 6)))
			renderer.renderFaceZPos(block, x, y, z, tex);
		if ((allsides) || (block.shouldSideBeRendered(world, x, y, z - 1, 6)))
			renderer.renderFaceZNeg(block, x, y, z, tex);
	}

	protected static void renderAllSides(int x, int y, int z, Block block, RenderBlocks renderer, Icon tex) {
		renderer.renderFaceXPos(block, x - 1, y, z, tex);
		renderer.renderFaceXNeg(block, x + 1, y, z, tex);
		renderer.renderFaceZPos(block, x, y, z - 1, tex);
		renderer.renderFaceZNeg(block, x, y, z + 1, tex);
		renderer.renderFaceYPos(block, x, y - 1, z, tex);
		renderer.renderFaceYNeg(block, x, y + 1, z, tex);
	}

	protected static void renderSides(int x, int y, int z, Block block, RenderBlocks renderer, Icon tex) {
		renderer.renderFaceXPos(block, x - 1, y, z, tex);
		renderer.renderFaceXNeg(block, x + 1, y, z, tex);
		renderer.renderFaceZPos(block, x, y, z - 1, tex);
		renderer.renderFaceZNeg(block, x, y, z + 1, tex);
	}
}
