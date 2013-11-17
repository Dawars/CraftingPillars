package me.dawars.CraftingPillars.handlers;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.network.packets.PacketClick;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PillarEventHandler
{
	public boolean doClick(int button)
	{
		World world = Minecraft.getMinecraft().theWorld;
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		for(int x = (int)player.posX-5; x <= (int)player.posX+5; x++)
			for(int y = (int)player.posY-5; y <= (int)player.posY+5; y++)
				for(int z = (int)player.posZ-5; z <= (int)player.posZ+5; z++)
					if(Block.blocksList[world.getBlockId(x, y, z)] instanceof BasePillar)
					{
						int id = ((BasePillar)Block.blocksList[world.getBlockId(x, y, z)]).getClickedButtonId(world, x, y, z, button, player);
						if(id > -1)
						{
							//System.out.println("Packet sent! "+button+" "+id+" "+x+" "+y+" "+z);
							CraftingPillars.proxy.sendToServer(new PacketClick(button, id, x, y, z).pack());
							return true;
						}
					}
		return false;
	}
	
	@ForgeSubscribe
	public void onClick(PlayerInteractEvent event)
	{
		if(event.entityPlayer.worldObj.isRemote)
		{
			if(!PillarTickHandler.pright)
			{
				if(doClick(2))
				{
					event.setCanceled(true);
					PillarTickHandler.pright = true;
				}
			}
			else
			{
				event.setCanceled(true);
			}
		}
	}
}
