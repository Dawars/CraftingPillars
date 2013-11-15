package me.dawars.CraftingPillars.handlers;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.network.packets.PacketClick;
import net.minecraft.block.Block;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EventHandler
{
	@ForgeSubscribe
	public void onClick(PlayerInteractEvent event)
	{
		if(event.action == Action.LEFT_CLICK_BLOCK)
		{
			if(!event.entityPlayer.worldObj.isRemote)
				for(int x = event.x-5; x <= event.x+5; x++)
					for(int y = event.y-5; y <= event.y+5; y++)
						for(int z = event.z-5; z <= event.z+5; z++)
							if(Block.blocksList[event.entityPlayer.worldObj.getBlockId(x, y, z)] instanceof BasePillar)
							{
								if(x == event.x && y == event.y && z == event.z)
								{
									if(((BasePillar)Block.blocksList[event.entityPlayer.worldObj.getBlockId(x, y, z)]).handleClickEvent(x, y, z, 0, event.entityPlayer)
										&& event.isCancelable())
											event.setCanceled(true);
								}
								else
								{
									((BasePillar)Block.blocksList[event.entityPlayer.worldObj.getBlockId(x, y, z)]).handleClickEvent(x, y, z, 0, event.entityPlayer);
								}
							}
		}
		else if(event.action == Action.RIGHT_CLICK_BLOCK)
		{
			if(!event.entityPlayer.worldObj.isRemote)
				for(int x = event.x-5; x <= event.x+5; x++)
					for(int y = event.y-5; y <= event.y+5; y++)
						for(int z = event.z-5; z <= event.z+5; z++)
							if(Block.blocksList[event.entityPlayer.worldObj.getBlockId(x, y, z)] instanceof BasePillar)
							{
								if(x == event.x && y == event.y && z == event.z)
								{
									if(((BasePillar)Block.blocksList[event.entityPlayer.worldObj.getBlockId(x, y, z)]).handleClickEvent(x, y, z, 2, event.entityPlayer)
										&& event.isCancelable())
											event.setCanceled(true);
								}
								else
								{
									((BasePillar)Block.blocksList[event.entityPlayer.worldObj.getBlockId(x, y, z)]).handleClickEvent(x, y, z, 2, event.entityPlayer);
								}
							}
		}
		else if(event.action == Action.RIGHT_CLICK_AIR)
		{
			CraftingPillars.proxy.sendToServer(new PacketClick(2).send());
		}
	}
}
