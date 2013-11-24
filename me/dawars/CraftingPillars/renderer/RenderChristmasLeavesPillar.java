package me.dawars.CraftingPillars.renderer;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.ChristmasLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderChristmasLeavesPillar extends BlockRenderingHelper implements ISimpleBlockRenderingHandler
{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
//		 block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//         renderer.setRenderBoundsFromBlock(block);
//         
//         drawSideBlockFaces(renderer, block, ((ChristmasLeavesBlock) block).icon[1], ((ChristmasLeavesBlock) block).icon[0]);//FIXME:Block glowing
//         drawSideBlockFaces(renderer, block, ((ChristmasLeavesBlock) block).icon[2], ((ChristmasLeavesBlock) block).icon[0]);

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		int bb = setBrightness(world, x, y, z, block);

        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);

        Tessellator t = Tessellator.instance;
        t.setBrightness(160);

        renderAllSides(x, y, z, block, renderer, ((ChristmasLeavesBlock) block).glowing);

        renderer.clearOverrideBlockTexture();
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        
        return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return CraftingPillars.christmasLeavesRenderID;
	}

}
