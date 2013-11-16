package me.dawars.CraftingPillars.handlers;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.network.packets.PacketClick;
import net.minecraft.block.Block;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EventHandler
{
	/*public boolean doClick(int button, PlayerInteractEvent event)
	{
		for(int x = event.x-5; x <= event.x+5; x++)
			for(int y = event.y-5; y <= event.y+5; y++)
				for(int z = event.z-5; z <= event.z+5; z++)
					if(Block.blocksList[event.entityPlayer.worldObj.getBlockId(x, y, z)] instanceof BasePillar)
					{
						int id = ((BasePillar)Block.blocksList[event.entityPlayer.worldObj.getBlockId(x, y, z)]).getClickedButtonId(event.entityPlayer.worldObj, x, y, z, button, event.entityPlayer);
						if(id > -1)
						{
							System.out.println("Packet sent! "+button+" "+id+" "+x+" "+y+" "+z);
							CraftingPillars.proxy.sendToServer(new PacketClick(button, id, x, y, z).pack());
							return true;
						}
					}
		return false;
	}*/
	
	@ForgeSubscribe
	public void onClick(PlayerInteractEvent event)
	{
		/*if(event.entityPlayer.worldObj.isRemote)
		{
			if(event.action == Action.LEFT_CLICK_BLOCK)
			{
				if(doClick(0, event))
					event.setCanceled(true);
			}
			else if(event.action == Action.RIGHT_CLICK_BLOCK)
			{
				System.out.println("RIGHT_CLICK_BLOCK");
				if(doClick(2, event))
					event.setCanceled(true);
			}
			else if(event.action == Action.RIGHT_CLICK_AIR)
			{
				if(doClick(2, event))
					event.setCanceled(true);
			}
		}*/
	}
}
