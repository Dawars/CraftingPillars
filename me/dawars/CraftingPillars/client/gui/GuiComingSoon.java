package me.dawars.CraftingPillars.client.gui;

import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiComingSoon extends GuiScreen
{
	static ResourceLocation IMAGE = new ResourceLocation(CraftingPillars.id + ":textures/gui/comingsoon.png");
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		this.mc.renderEngine.bindTexture(IMAGE);
		drawTexturedModalRect(0, 0, 0, 0, 256, 256);
		super.drawScreen(mouseX, mouseY, partialTick);
	}
}
