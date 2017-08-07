package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tiles.TileEntityCraftingPillar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TESRCraftingPillar extends TileEntitySpecialRenderer<TileEntityCraftingPillar> {
    private static final EntityItem ITEM =
            new EntityItem(Minecraft.getMinecraft().theWorld, 0, 0, 0, new ItemStack(Items.APPLE));

    @Override
    public void renderTileEntityAt(TileEntityCraftingPillar te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

        ITEM.hoverStart = 0;

        Minecraft.getMinecraft().getRenderManager().doRenderEntity(ITEM, x, y, z, 0, 0, false);
        EntityItem entityItem = new EntityItem(getWorld(),
                0,
                0,
                0);
        entityItem.hoverStart = 0;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (te.getStackInSlot(j*3 + i) != null && te.getStackInSlot(j*3 + i).stackSize > 0) {
                    entityItem.setEntityItemStack(te.getStackInSlot(j * 3 + i));
                    entityItem.hoverStart = 0;

                    // TODO lightning not working?
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(3. / 16 + x + 5. / 16 * i, y + 1 - 2. / 16, 3. / 16 + z + 5. / 16 * j);
                    GlStateManager.scale(4. / 8, 4. / 8, 4. / 8);
                    Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem,
                            0, 0, 0, 0, 0, false);
                    GlStateManager.popMatrix();
                }
            }
        }

        if (te.getResultStack() != null) {
            entityItem.setEntityItemStack(te.getResultStack());
            entityItem.hoverStart = -te.getItemRotation(partialTicks);

            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
            Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem,
                    0, 0, 0, 0, 0, false);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean isGlobalRenderer(TileEntityCraftingPillar te) {
        return false;
    }
}
