package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tiles.TileEntityCraftingPillar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;

public class TESRCraftingPillar extends TileEntitySpecialRenderer<TileEntityCraftingPillar> {
    @Override
    public void render(TileEntityCraftingPillar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        EntityItem entityItem = new EntityItem(getWorld(),
                0,
                0,
                0);
        entityItem.hoverStart = 0;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (!te.getStackInSlot(j*3 + i).isEmpty()) {
                    GlStateManager.pushMatrix();
                    //GlStateManager.pushAttrib();

                    entityItem.setItem(te.getStackInSlot(j * 3 + i));
                    entityItem.hoverStart = 0;

                    // TODO lightning not working?
                    GlStateManager.translate(3. / 16 + x + 5. / 16 * i, y + 1 - 2. / 16, 3. / 16 + z + 5. / 16 * j);
                    GlStateManager.scale(4. / 8, 4. / 8, 4. / 8);
                    //setLightmapDisabled(true);
                    Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem,
                            0, 0, 0, 0, 0, false);

                    /*
                    GlStateManager.translate(3./16 + x + 5./16*i, y + 1 + 2./16, 3./16 + z + 5./16*j);
                    GlStateManager.scale(4./8, 4./8, 4./8);
                    Minecraft.getMinecraft().getRenderItem().renderItem(te.getStackInSlot(j*3 + i), ItemCameraTransforms.TransformType.FIXED);
                    */

                    //GlStateManager.popAttrib();
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
        entityItem.hoverStart = -te.getItemRotation(partialTicks);
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
