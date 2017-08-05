package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tiles.TileEntityCraftingPillar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;

public class TESRCraftingPillar extends TileEntitySpecialRenderer<TileEntityCraftingPillar> {
    @Override
    public void render(TileEntityCraftingPillar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        EntityItem entityItem = new EntityItem(getWorld());
        entityItem.hoverStart = 0;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (!te.getItemStackAt(i, j).isEmpty()) {
                    GlStateManager.pushMatrix();

                    GlStateManager.translate(x + 1./6 + 1./3*i, y + 1, z + 1./6 + 1./3*j);
                    //GlStateManager.translate(-x, -y, -z);
                    GlStateManager.scale(0.33, 0.33, 0.33);

                    GlStateManager.pushAttrib();
                    RenderHelper.enableStandardItemLighting();

                    entityItem.setItem(te.getItemStackAt(i, j));
                    Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem,
                            0, 0, 0, 0, 0, false);
                    //RenderEntityItem
                    //Minecraft.getMinecraft().getRenderItem().renderItem(te.getItemStackAt(i, j), ItemCameraTransforms.TransformType.FIXED);

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
