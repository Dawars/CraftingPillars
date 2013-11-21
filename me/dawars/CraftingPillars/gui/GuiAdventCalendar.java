package me.dawars.CraftingPillars.gui;

import org.lwjgl.opengl.GL11;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.container.ContainerAdventCalendar;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiAdventCalendar extends BaseGui
{
	ResourceLocation TEXTURE_ADVENT_CALENDAR = new ResourceLocation(CraftingPillars.id + ":textures/gui/advent_calendar_bg.png");
	
	public GuiAdventCalendar(InventoryPlayer inventoryplayer, EntityPlayer player)
	{
        super(new ContainerAdventCalendar(inventoryplayer, player));
        this.xSize = 256;
        this.ySize = 256;
    }

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
		String s = I18n.getString(CraftingPillars.itemCalendar.getUnlocalizedName() + ".name");
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE_ADVENT_CALENDAR);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height -this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
