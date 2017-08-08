package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tileentity.TileEntityCraftingPillar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TESRCraftingPillar extends TileEntitySpecialRenderer<TileEntityCraftingPillar> {
    private final EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0, 0, 0);

    @Override
    public void renderTileEntityAt(TileEntityCraftingPillar te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

        entityItem.hoverStart = 0; // crafting ingredients stay still

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack stackInSlot = te.getStackInSlot(j * 3 + i);
                if (stackInSlot != null && stackInSlot.stackSize > 0) {
                    entityItem.setEntityItemStack(stackInSlot);

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
            entityItem.hoverStart = te.getItemRotation(partialTicks); // crafting result spins

            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
            Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem,
                    0, 0, 0, 0, 0, false);
            GlStateManager.popMatrix();
        }
    }
}
