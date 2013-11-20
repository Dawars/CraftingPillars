package me.dawars.CraftingPillars.gui;

import me.dawars.CraftingPillars.container.ContainerAdventCalendar;
import me.dawars.CraftingPillars.tiles.TileAdventCalendar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile = world.getBlockTileEntity(x, y, z);

		switch (id) {

		case GuiIds.ADVENT_CALENDAR:
			if (!(tile instanceof TileAdventCalendar))
				return null;
			return new ContainerAdventCalendar(player.inventory, (TileAdventCalendar) tile);

		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile = world.getBlockTileEntity(x, y, z);

		switch (id) {

		case GuiIds.ADVENT_CALENDAR:
			if (!(tile instanceof TileAdventCalendar))
				return null;
			return new GuiAdventCalendar(player.inventory, (TileAdventCalendar) tile);

		default:
			return null;
		}
	}
}