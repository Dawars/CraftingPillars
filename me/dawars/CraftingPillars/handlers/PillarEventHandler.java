package me.dawars.CraftingPillars.handlers;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.properties.CalendarPlayerProps2013;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class PillarEventHandler
{
	@ForgeSubscribe
	public void onEntityConstructing(EntityConstructing event)
	{
		if(event.entity instanceof EntityPlayer && CalendarPlayerProps2013.get((EntityPlayer)event.entity) == null)
		{
			CalendarPlayerProps2013.register((EntityPlayer)event.entity);
		}
	}
	
	@ForgeSubscribe
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			if(CraftingPillars.winter && !player.inventory.hasItem(CraftingPillars.itemCalendar2013.itemID))
				player.inventory.addItemStackToInventory(new ItemStack(CraftingPillars.itemCalendar2013.itemID, 1, 0));
		}
	}
}
