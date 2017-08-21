package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tileentity.TileShowcase;
import me.dawars.craftingpillars.util.helpers.RenderingHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RendererShowcase extends TileEntitySpecialRenderer<TileShowcase> {

    @Override
    public void renderTileEntityAt(TileShowcase te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);


        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5F, y, z + 0.5F);

        RenderingHelper.renderItem(te.getStackInSlot(0), 0F, 1, 0F, 1);


        GlStateManager.popMatrix();
    }
}
