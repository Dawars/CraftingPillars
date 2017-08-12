package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tileentity.TileEntityCraftingPillar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class TESRCraftingPillar extends TileEntitySpecialRenderer<TileEntityCraftingPillar> {
    private final EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0, 0, 0);

    private void doRenderItem(ItemStack itemStack) {
        entityItem.setEntityItemStack(itemStack);
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem,
                0, 0, 0, 0, 0, false);
    }

    private void renderItem(ItemStack itemStack, double x, double y, double z, double scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(scale, scale, scale);
        doRenderItem(itemStack);
        GlStateManager.popMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntityCraftingPillar te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

        entityItem.hoverStart = 0; // crafting ingredients stay still

        for (int i = 0; i < 9; ++i) {
            ItemStack stack = te.getStackInSlot(i);
            if (stack != null) {
                renderItem(stack,
                        x + te.getSlotX(i),
                        y + 1 - 2./16,
                        z + te.getSlotZ(i),
                        4./8);
            }
        }

        if (te.getResultStack() != null) {
            entityItem.hoverStart = te.getItemRotation(partialTicks); // crafting result spins and bobs
            renderItem(te.getResultStack(), x + 0.5, y + 1.25, z + 0.5, 1);
        }
    }
}
