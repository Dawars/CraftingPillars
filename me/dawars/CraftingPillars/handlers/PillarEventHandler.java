package me.dawars.CraftingPillars.handlers;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BaseBlock;
import me.dawars.CraftingPillars.blocks.BaseBlockContainer;
import me.dawars.CraftingPillars.properties.CalendarPlayerProps2013;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.world.BlockEvent;

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
			{
				player.inventory.addItemStackToInventory(new ItemStack(CraftingPillars.itemCalendar2013.itemID, 1, 0));
				player.addStat(CraftingPillars.achievementChristmas, 1);
			}
		}
	}
	
	@ForgeSubscribe
	public void BreakEvent(net.minecraftforge.event.world.BlockEvent.BreakEvent event)
    {
		if(event.block instanceof BaseBlockContainer && event.getPlayer().isSneaking())
		{
			event.setCanceled(true);
			event.block.onBlockClicked(event.world, event.x, event.y, event.z, event.getPlayer());
		}
	}
}
