package me.dawars.craftingpillars.client.render;

import me.dawars.craftingpillars.tileentity.TileTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class RendererTank extends TileEntitySpecialRenderer<TileTank> {

    @Override
    public void renderTileEntityAt(TileTank te, double x, double y, double z, float partialTicks, int destroyStage) {
        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);


        if (te.isShowNum() && te.getTankFluidAmount() > 0) {
            /*GlStateManager.pushMatrix(); // TODO draw text
            GlStateManager.translate(x + 0.5D, y + 1, z + 0.5D);

            GlStateManager.disableLighting();
            RenderingHelper.renderFloatingTextWithBackground(0, 0.35F, 0, 0.3F, tank.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.getFluid().getLocalizedName(),
                    Color.WHITE.getRGB(), new Color(0F, 0F, 0F, 0.5F));
            RenderingHelper.renderFloatingTextWithBackground(0, 0.2F, 0, 0.2F, tank.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.amount + " Unit", Color.WHITE.getRGB(), new Color(
                    0F, 0F, 0F, 0.5F));
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();*/

        }

        if (te.getTankCapacity() <= 0)
            return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
//        if (player.getDistanceSq(x, y, z) < 128) { // FIXME distance calc

        GlStateManager.pushMatrix();
//        GlStateManager.disableLighting();
//        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();

//        GlStateManager.translate(0.05F, 0.05F, 0.05F);
//        GlStateManager.scale(.99,.99,.99); // FIXME dont overlap with glass pane
        int capacity = te.getTankCapacity();
        FluidStack fluid = te.getFluidTank().getFluid();
        if (fluid != null) {
            Tessellator tess = Tessellator.getInstance();
            VertexBuffer buffer = tess.getBuffer();

            buffer.setTranslation(x, y, z);

            bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            TextureAtlasSprite still = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill().toString());
            TextureAtlasSprite flow = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getFlowing().toString());
            // TODO gas opacity

            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);


            float[][][] field = Blobs.fieldStrength(te.getBlobs());
            for (int i = 0; i < 16; i++)
                for (int j = 0; j < 16; j++)
                    for (int k = 0; k < 16; k++)
                        if ((int) field[i][j][k] > 0
                                && (i != 0 && (int) field[i - 1][j][k] != 0 && i != 15 && (int) field[i + 1][j][k] != 0 && j != 0 && (int) field[i][j - 1][k] != 0 && j != 15
                                && (int) field[i][j + 1][k] != 0 && k != 0 && (int) field[i][j][k - 1] != 0 && k != 15 && (int) field[i][j][k + 1] != 0)) {
                            field[i][j][k] = 0F;
                        }

            float xMax = still.getMaxU();
            float xMin = still.getMinU();
            float yMax = still.getMaxV();
            float yMin = still.getMinV();

            float iconSize = (xMax - xMin) / 16;

            for (int i = 0; i < 16; i++)
                for (int j = 0; j < 16; j++)
                    for (int k = 0; k < 16; k++)
                        if ((int) field[i][j][k] > 0) {
                            if (j == 15 || (int) field[i][j + 1][k] == 0) {

                                buffer.pos((i) / 16F, (j + 1) / 16F, (k) / 16F).tex(xMin + i * iconSize, yMin + k * iconSize).endVertex();
                                buffer.pos((i) / 16F, (j + 1) / 16F, (k + 1) / 16F).tex(xMin + i * iconSize, yMin + (k + 1) * iconSize).endVertex();
                                buffer.pos((i + 1) / 16F, (j + 1) / 16F, (k + 1) / 16F).tex(xMin + (i + 1) * iconSize, yMin + (k + 1) * iconSize).endVertex();
                                buffer.pos((i + 1) / 16F, (j + 1) / 16F, (k) / 16F).tex(xMin + (i + 1) * iconSize, yMin + k * iconSize).endVertex();

                            }

                            if (j == 0 || (int) field[i][j - 1][k] == 0) {
                                buffer.pos((i) / 16F, (j) / 16F, (k + 1) / 16F).tex(xMin + i * iconSize, yMin + k * iconSize).endVertex();
                                buffer.pos((i) / 16F, (j) / 16F, (k) / 16F).tex(xMin + i * iconSize, yMin + (k + 1) * iconSize).endVertex();
                                buffer.pos((i + 1) / 16F, (j) / 16F, (k) / 16F).tex(xMin + (i + 1) * iconSize, yMin + (k + 1) * iconSize).endVertex();
                                buffer.pos((i + 1) / 16F, (j) / 16F, (k + 1) / 16F).tex(xMin + (i + 1) * iconSize, yMin + k * iconSize).endVertex();
                            }

                            if (k == 15 || (int) field[i][j][k + 1] == 0) {// FIXME tex coords
                                buffer.pos((i) / 16F, (j + 1) / 16F, (k + 1) / 16F).tex(xMin + i * iconSize, yMin + (j + 1) * iconSize).endVertex();
                                buffer.pos((i) / 16F, (j) / 16F, (k + 1) / 16F).tex(xMin + i * iconSize, yMin + (k + 1) * iconSize).endVertex();
                                buffer.pos((i + 1) / 16F, (j) / 16F, (k + 1) / 16F).tex(xMin + (i + 1) * iconSize, yMin + (j + 1) * iconSize).endVertex();
                                buffer.pos((i + 1) / 16F, (j + 1) / 16F, (k + 1) / 16F).tex(xMin + (i + 1) * iconSize, yMin + j * iconSize).endVertex();

                            }
                            if (k == 0 || (int) field[i][j][k - 1] == 0) {
                                buffer.pos((i + 1) / 16F, (j + 1) / 16F, (k) / 16F).tex(xMin + i * iconSize, yMin + j * iconSize).endVertex();
                                buffer.pos((i + 1) / 16F, (j) / 16F, (k) / 16F).tex(xMin + i * iconSize, yMin + (j + 1) * iconSize).endVertex();
                                buffer.pos((i) / 16F, (j) / 16F, (k) / 16F).tex(xMin + (i + 1) * iconSize, yMin + (j + 1) * iconSize).endVertex();
                                buffer.pos((i) / 16F, (j + 1) / 16F, (k) / 16F).tex(xMin + (i + 1) * iconSize, yMin + j * iconSize).endVertex();
                            }

                            if (i == 15 || (int) field[i + 1][j][k] == 0) {
                                buffer.pos((i + 1) / 16F, (j + 1) / 16F, (k + 1) / 16F).tex(xMin + k * iconSize, yMin + j * iconSize).endVertex();
                                buffer.pos((i + 1) / 16F, (j) / 16F, (k + 1) / 16F).tex(xMin + k * iconSize, yMin + (j + 1) * iconSize).endVertex();
                                buffer.pos((i + 1) / 16F, (j) / 16F, (k) / 16F).tex(xMin + (k + 1) * iconSize, yMin + (j + 1) * iconSize).endVertex();
                                buffer.pos((i + 1) / 16F, (j + 1) / 16F, (k) / 16F).tex(xMin + (k + 1) * iconSize, yMin + j * iconSize).endVertex();

                            }

                            if (i == 0 || (int) field[i - 1][j][k] == 0) {
                                buffer.pos((i) / 16F, (j) / 16F, (k + 1) / 16F).tex(xMin + j * iconSize, yMin + k * iconSize).endVertex();
                                buffer.pos((i) / 16F, (j + 1) / 16F, (k + 1) / 16F).tex(xMin + j * iconSize, yMin + (k + 1) * iconSize).endVertex();
                                buffer.pos((i) / 16F, (j + 1) / 16F, (k) / 16F).tex(xMin + (j + 1) * iconSize, yMin + (k + 1) * iconSize).endVertex();
                                buffer.pos((i) / 16F, (j) / 16F, (k) / 16F).tex(xMin + (j + 1) * iconSize, yMin + k * iconSize).endVertex();
                            }
                        }


            tess.draw();

            buffer.setTranslation(0, 0, 0);

        }
        GlStateManager.disableBlend();
//        GlStateManager.disableAlpha();
//        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        /*} else {
            // TODO render block liquid
        }*/
    }
}