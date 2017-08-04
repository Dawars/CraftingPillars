package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tiles.TileEntityCraftingPillar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class TESRCraftingPillar extends TileEntitySpecialRenderer<TileEntityCraftingPillar> {
    @Override
    public void render(TileEntityCraftingPillar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (te.getItemStackAt(i, j) != null && !te.getItemStackAt(i, j).isEmpty()) {
                    GlStateManager.pushMatrix();

                    GlStateManager.translate(x + 1./6 + 1./3*i, y + 1.1, z + 1./6 + 1./3*j);
                    GlStateManager.scale(0.33, 0.33, 0.33);

                    GlStateManager.pushAttrib();
                    RenderHelper.enableStandardItemLighting();

                    Minecraft.getMinecraft().getRenderItem().renderItem(te.getItemStackAt(i, j), ItemCameraTransforms.TransformType.FIXED);

                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.popAttrib();

                    GlStateManager.popMatrix();
                }
            }
        }
    }

    @Override
    public boolean isGlobalRenderer(TileEntityCraftingPillar te) {
        return true;
    }
}
