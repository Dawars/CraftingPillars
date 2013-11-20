package me.dawars.CraftingPillars.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

//		TileEntity tile = world.getBlockTileEntity(x, y, z);
//
//		switch (id) {
//
//		case GuiIds.MIXER:
//			if (!(tile instanceof TileMixer))
//				return null;
//			return new ContainerMixer(player.inventory, (TileMixer) tile);
//
//		case GuiIds.CANDY_MAKER:
//			if (!(tile instanceof TileCandyMaker))
//				return null;
//			return new ContainerCandyMaker(player.inventory,
//					(TileCandyMaker) tile);
//
//		default:
			return null;
//		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

//		TileEntity tile = world.getBlockTileEntity(x, y, z);
//
//		switch (id) {
//
//		case GuiIds.MIXER:
//			if (!(tile instanceof TileMixer))
//				return null;
//			return new GuiMixer(player.inventory, (TileMixer) tile);
//
//		case GuiIds.CANDY_MAKER:
//			if (!(tile instanceof TileCandyMaker))
//				return null;
//			return new GuiCandyMaker(player.inventory, (TileCandyMaker) tile);
//
//		default:
			return null;
//		}
	}
}