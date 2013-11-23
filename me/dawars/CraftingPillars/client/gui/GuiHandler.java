package me.dawars.CraftingPillars.client.gui;

import me.dawars.CraftingPillars.container.ContainerAdventCalendar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(id)
		{
			case GuiIds.ADVENT_CALENDAR:
				return new ContainerAdventCalendar(player.inventory, player);
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(id)
		{
			case GuiIds.ADVENT_CALENDAR:
				return new GuiAdventCalendar(player.inventory, player);
			default:
				return null;
		}
	}
}