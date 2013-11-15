package me.dawars.CraftingPillars.event;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import me.dawars.CraftingPillars.blocks.BasePillar;
import net.minecraft.block.Block;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EventHandler
{
	@ForgeSubscribe
	public void onClick(PlayerInteractEvent event)
	{
		if(Block.blocksList[event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z)] instanceof BasePillar)
		{
			if(event.action == Action.RIGHT_CLICK_BLOCK)
			{
				if(event.face == 1 && event.entityPlayer.isSneaking() && event.entityPlayer.inventory.getCurrentItem() != null && event.isCancelable())
					event.setCanceled(true);
			}
			else if(event.action == Action.RIGHT_CLICK_AIR)
			{
				// TODO send packet
			}
			else if(event.action == Action.LEFT_CLICK_BLOCK)
			{
				// TODO send packet
			}
		}
	}
}
