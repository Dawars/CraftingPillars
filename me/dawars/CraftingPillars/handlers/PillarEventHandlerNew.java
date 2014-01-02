package me.dawars.CraftingPillars.handlers;

import me.dawars.CraftingPillars.blocks.BaseBlockContainer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class PillarEventHandlerNew
{
	@ForgeSubscribe
	public void onBreakBlock(BreakEvent event)
	{
		if(event.block instanceof BaseBlockContainer && event.getPlayer().isSneaking())
		{
			event.setCanceled(true);
			event.block.onBlockClicked(event.world, event.x, event.y, event.z, event.getPlayer());
		}
	}
}