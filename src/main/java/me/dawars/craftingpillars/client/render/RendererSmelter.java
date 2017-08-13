package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tileentity.TileSmelter;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RendererSmelter extends TileEntitySpecialRenderer<TileSmelter> {

    @Override
    public void renderTileEntityAt(TileSmelter te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);


    }
}
