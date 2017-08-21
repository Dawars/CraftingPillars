package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tileentity.TileSmelter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import static me.dawars.craftingpillars.inventory.InventorySmelter.*;

public class RendererSmelter extends TileEntitySpecialRenderer<TileSmelter> {
    private final EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0, 0, 0);

    // TODO create helper class
    private void doRenderItem(ItemStack itemStack) {
        entityItem.setEntityItemStack(itemStack);
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entityItem,
                0, 0, 0, 0, 0, false);
    }

    private void renderItem(ItemStack itemStack, double x, double y, double z, double scale) {
        if (itemStack == null) return;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(scale, scale, scale);
        doRenderItem(itemStack);
        GlStateManager.popMatrix();
    }

    @Override
    public void renderTileEntityAt(TileSmelter te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);


        entityItem.hoverStart = 0; // crafting ingredients stay still

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5F, y, z + 0.5F);

        renderItem(te.getStackInSlot(SLOT_RAW), 0F, 1.125F, 0F, 1);
        renderItem(te.getStackInSlot(SLOT_FUEL), 0F, 0.3F, 0F, 1);
        renderItem(te.getStackInSlot(SLOT_COOKED), 0, 0, 0, 1);


        if (te.canSmelt() && te.isBurning()) {
            renderItem(FurnaceRecipes.instance().getSmeltingResult(te.getStackInSlot(SLOT_RAW)),
                    0.01F, 1.75F - te.cookTime / 150F, 0.01F, 1);
        }

        GlStateManager.popMatrix();
    }
}
