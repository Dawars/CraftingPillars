package me.dawars.craftingpillars.util.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class RenderingHelper {
    private static final EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0, 0, 0);

    static {
        entityItem.hoverStart = 0; // crafting ingredients stay still

    }

    private static void doRenderItem(ItemStack itemStack) {
        entityItem.setEntityItemStack(itemStack);
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem,
                0, 0, 0, 0, 0, false);
    }

    public static void renderItem(ItemStack itemStack, double x, double y, double z, double scale, float rotation) {
        if (itemStack == null) return;

        entityItem.hoverStart = rotation;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(scale, scale, scale);
        doRenderItem(itemStack);
        GlStateManager.popMatrix();
    }

    public static void renderItem(ItemStack itemStack, double x, double y, double z, double scale) {

        renderItem(itemStack, x, y, z, scale, 0);

    }
}
