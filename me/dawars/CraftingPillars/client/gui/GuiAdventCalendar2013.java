package me.dawars.CraftingPillars.client.gui;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.container.ContainerAdventCalendar2013;
import me.dawars.CraftingPillars.network.packets.PacketInGuiClick;
import me.dawars.CraftingPillars.properties.CalendarPlayerProps2013;
import me.dawars.CraftingPillars.renderer.RenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiAdventCalendar2013 extends BaseGui
{
	ResourceLocation TEXTURE_ADVENT_CALENDAR = new ResourceLocation(CraftingPillars.id + ":textures/gui/advent_calendar_bg.png");
	
	public GuiAdventCalendar2013(InventoryPlayer inventoryplayer, EntityPlayer player)
	{
		super(new ContainerAdventCalendar2013(inventoryplayer, player));
		this.xSize = 256;
		this.ySize = 256;
	}
	
	@Override
	public void mouseClicked(int x, int y, int btn)
	{
		super.mouseClicked(x, y, btn);
		for(Slot slot : (ArrayList<Slot>)this.inventorySlots.inventorySlots)
			if(this.isMouseOverSlot(slot, x, y) && !this.isSlotDiscovered(slot) && CraftingPillars.getWinterDay(2013) > slot.slotNumber)
			{
				((ContainerAdventCalendar2013)this.inventorySlots).setDiscovered(slot.slotNumber);
				CraftingPillars.proxy.sendToServer(new PacketInGuiClick(slot.slotNumber).pack());
			}
	}
	
	public boolean isMouseOverSlot(Slot slot, int x, int y)
	{
		return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, x, y);
	}
	
	public boolean isSlotDiscovered(Slot slot)
	{
		return ((ContainerAdventCalendar2013)this.inventorySlots).isDiscovered(slot.slotNumber);
	}
	
	@Override
	protected void drawSlotInventory(Slot slot)
	{
		if(this.isSlotDiscovered(slot))
		{
			glDisable(GL_LIGHTING);
			glBindTexture(GL_TEXTURE_2D, 0);
			glColor4f(0.5F, 0.5F, 0.5F, 1F);
			glBegin(GL_QUADS);
				glVertex2i(slot.xDisplayPosition, slot.yDisplayPosition);
				glVertex2i(slot.xDisplayPosition, slot.yDisplayPosition+16);
				glVertex2i(slot.xDisplayPosition+16, slot.yDisplayPosition+16);
				glVertex2i(slot.xDisplayPosition+16, slot.yDisplayPosition);
			glEnd();
			//super.drawSlotInventory(slot);
			glEnable(GL_LIGHTING);
			this.itemRenderer.zLevel = 100F;
			this.itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), slot.getStack(), slot.xDisplayPosition, slot.yDisplayPosition);
			glDisable(GL_LIGHTING);
			
			glBindTexture(GL_TEXTURE_2D, 0);
			this.itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.getTextureManager(), slot.getStack(), slot.xDisplayPosition, slot.yDisplayPosition);
			glEnable(GL_LIGHTING);
		}
		else
		{
			//if(CraftingPillars.getWinterDay(2013) <= slot.slotNumber)
				// TODO lock
			glDisable(GL_LIGHTING);
			this.fontRenderer.drawStringWithShadow(""+(slot.slotNumber+1), slot.xDisplayPosition+8-this.fontRenderer.getStringWidth(""+(slot.slotNumber+1))/2, slot.yDisplayPosition+8-this.fontRenderer.FONT_HEIGHT/2, 16777215);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = I18n.getString(CraftingPillars.itemCalendar2013.getUnlocalizedName() + ".name");
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 35, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY)
	{
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE_ADVENT_CALENDAR);
		int k = (this.width - this.xSize)/2;
		int l = (this.height - this.ySize)/2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		this.drawDefaultBackground();
		int k = this.guiLeft;
		int l = this.guiTop;
		this.drawGuiContainerBackgroundLayer(partialTick, mouseX, mouseY);
		glDisable(GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		glDisable(GL_LIGHTING);
		glDisable(GL_DEPTH_TEST);
		RenderHelper.enableGUIStandardItemLighting();
		glPushMatrix();
		glTranslatef((float)k, (float)l, 0.0F);
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		glEnable(GL_RESCALE_NORMAL);
		short short1 = 240;
		short short2 = 240;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int i1;
		
		for(int j1 = 0; j1 < this.inventorySlots.inventorySlots.size(); ++j1)
		{
			Slot slot = (Slot)this.inventorySlots.inventorySlots.get(j1);
			this.drawSlotInventory(slot);
			
			if(this.isMouseOverSlot(slot, mouseX, mouseY) && slot.func_111238_b())
			{
				int k1 = slot.xDisplayPosition;
				i1 = slot.yDisplayPosition;
				this.drawGradientRect(k1, i1, k1 + 16, i1 + 16, -2130706433, -2130706433);
			}
		}
		
		//Forge: Force lighting to be disabled as there are some issue where lighting would
		//incorrectly be applied based on items that are in the inventory.
		glDisable(GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		glEnable(GL_LIGHTING);
		glPopMatrix();
		glEnable(GL_LIGHTING);
		glEnable(GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}
}
