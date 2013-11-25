package me.dawars.CraftingPillars.renderer;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.ChristmasLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderChristmasLeavesPillar extends BlockRenderingHelper implements ISimpleBlockRenderingHandler
{
	@Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        
        drawBlock(renderer, block, ((ChristmasLeavesBlock) block).iconArray[1]);
        drawBlock(renderer, block, ((ChristmasLeavesBlock) block).glowing);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
	
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);
    	int bb = setBrightness(world, x, y, z, block);

        Tessellator t = Tessellator.instance;
//        t.setColorOpaque_I(thaumcraft.common.world.BlockCustomOreItem.colors[metadata]);
        t.setBrightness(160);


        Icon tex = ((ChristmasLeavesBlock) block).glowing;
        
        renderer.renderFaceXPos(block, x, y, z, tex);
		renderer.renderFaceXNeg(block, x, y, z, tex);
		renderer.renderFaceZPos(block, x, y, z, tex);
		renderer.renderFaceZNeg(block, x, y, z, tex);
		renderer.renderFaceYPos(block, x, y, z, tex);
		renderer.renderFaceYNeg(block, x, y, z, tex);
		
        renderer.clearOverrideBlockTexture();
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
	        
        return true;
    }

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
	
	@Override
	public int getRenderId() {
		return CraftingPillars.christmasLeavesRenderID;
	}


}
