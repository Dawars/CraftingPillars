package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tiles.TileEntityCraftingPillar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class TESRCraftingPillar extends TileEntitySpecialRenderer<TileEntityCraftingPillar> {
    @Override
    public void render(TileEntityCraftingPillar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        GlStateManager.pushMatrix();

        GlStateManager.translate(x+0.5, y+0.75, z+0.5);

        EntityItem entityItem = new EntityItem(
                te.getWorld(),
                te.getPos().getX()+0.5,
                te.getPos().getY()+0.75,
                te.getPos().getZ()+0.5,
                new ItemStack(Blocks.DIRT));
        entityItem.hoverStart = te.getItemRotation();
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem, 0, 0, 0, 0, partialTicks, true);

        GlStateManager.popMatrix();
    }

    @Override
    public boolean isGlobalRenderer(TileEntityCraftingPillar te) {
        return true;
    }
}
