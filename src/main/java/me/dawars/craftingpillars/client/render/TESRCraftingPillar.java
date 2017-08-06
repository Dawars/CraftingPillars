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
                if (!te.getStackInSlot(j*3 + i).isEmpty()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.pushAttrib();

                    GlStateManager.translate(x + 1./6 + 1./3*i, y + 1, z + 1./6 + 1./3*j);
                    GlStateManager.scale(0.5, 0.5, 0.5);

                    RenderHelper.enableStandardItemLighting();
                    entityItem.setItem(te.getStackInSlot(j*3 + i));
                    Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem,
                            0, 0, 0, 0, 0, false);
                    RenderHelper.disableStandardItemLighting();

                    GlStateManager.popAttrib();
                    GlStateManager.popMatrix();
                }
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();

        GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
        //GlStateManager.scale(0.5, 0.5, 0.5);

        RenderHelper.enableStandardItemLighting();
        entityItem.setItem(te.getStackInSlot(9));
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem,
                0, 0, 0, 0, 0, false);
        RenderHelper.disableStandardItemLighting();

        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    @Override
    public boolean isGlobalRenderer(TileEntityCraftingPillar te) {
        return true;
    }
}
