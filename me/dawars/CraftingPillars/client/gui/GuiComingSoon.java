package me.dawars.CraftingPillars.client.gui;

import org.lwjgl.opengl.GL11;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.container.ContainerAdventCalendar2013;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiComingSoon extends GuiScreen
{
	static ResourceLocation IMAGE = new ResourceLocation("textures/gui/book.png");

    private int bookImageWidth = 192;
    private int bookImageHeight = 192;

	private int xSize;
	private int ySize;

    public GuiComingSoon()
	{
		super();
		this.xSize = 256;
		this.ySize = 256;
	}
    
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		this.mc.renderEngine.bindTexture(IMAGE);
		int k = (this.width - this.bookImageWidth) / 2;
		byte b0 = 2;
		this.drawTexturedModalRect(k, b0, 0, 0, this.bookImageWidth, this.bookImageHeight);
		
		String s = "The Lore of Elysium";
		this.fontRenderer.drawStringWithShadow(s, k + this.fontRenderer.getStringWidth(s)/2, 35, 0xbbbbbb);

		s = "Coming Soon...";
		this.fontRenderer.drawStringWithShadow(s, k + this.fontRenderer.getStringWidth(s), 80, 0xbbbbbb);
		
		super.drawScreen(mouseX, mouseY, partialTick);
	}
}
