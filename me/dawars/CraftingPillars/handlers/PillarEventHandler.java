package me.dawars.CraftingPillars.handlers;

import me.dawars.CraftingPillars.properties.CalendarPlayerProps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class PillarEventHandler
{
	@ForgeSubscribe
	public void onEntityConstructing(EntityConstructing event)
	{
		if(event.entity instanceof EntityPlayer && CalendarPlayerProps.get((EntityPlayer)event.entity) == null)
		{
			CalendarPlayerProps.register((EntityPlayer)event.entity);
		}
	}
}
