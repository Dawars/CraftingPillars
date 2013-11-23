package me.dawars.CraftingPillars.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.container.ContainerAdventCalendar;
import me.dawars.CraftingPillars.network.packets.PacketInGuiClick;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
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
	public void mouseClicked(int x, int y, int btn)
	{
		super.mouseClicked(x, y, btn);
		for(Slot slot : (ArrayList<Slot>)this.inventorySlots.inventorySlots)
			if(this.isMouseOverSlot(slot, x, y) && !this.isSlotDiscovered(slot) && this.isSlotDiscoverable(slot))
				CraftingPillars.proxy.sendToServer(new PacketInGuiClick(slot.slotNumber).pack());
	}
	
	public boolean isMouseOverSlot(Slot slot, int x, int y)
	{
		return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, x, y);
	}
	
	public boolean isSlotDiscovered(Slot slot)
	{
		return true; // TODO
	}
	
	public boolean isSlotDiscoverable(Slot slot)
	{
		return false; // TODO
	}
	
	@Override
	protected void drawSlotInventory(Slot slot)
	{
		if(this.isSlotDiscovered(slot))
			super.drawSlotInventory(slot);
		else
		{
			// TODO
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		String s = I18n.getString(/*CraftingPillars.itemCalendar.getUnlocalizedName() + */".name");
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 35, 4210752);
        //this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE_ADVENT_CALENDAR);
        int k = (this.width - this.xSize)/2;
        int l = (this.height - this.ySize)/2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
