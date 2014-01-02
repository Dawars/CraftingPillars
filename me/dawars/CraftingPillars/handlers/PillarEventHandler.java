package me.dawars.CraftingPillars.handlers;

import java.util.Calendar;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BaseBlockContainer;
import me.dawars.CraftingPillars.properties.CalendarPlayerProps2013;
import me.dawars.CraftingPillars.world.gen.ChristmasTreeGen;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PillarEventHandler
{
	@ForgeSubscribe
	public void onUseBonemeal(BonemealEvent event) {
		if (!event.world.isRemote) {
			if (event.ID == CraftingPillars.blockChristmasTreeSapling.blockID) {
				if(CraftingPillars.maxTreeState >= 4)
				{
					new ChristmasTreeGen(true, 4).generate(event.world, event.world.rand, event.X, event.Y, event.Z);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}

	@ForgeSubscribe
	public void onEntityConstructing(EntityConstructing event)
	{
		if(event.entity instanceof EntityPlayer && CalendarPlayerProps2013.get(event.entity) == null)
		{
			CalendarPlayerProps2013.register(event.entity);
		}
	}

	@ForgeSubscribe
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		Calendar c = Calendar.getInstance();
		Calendar e = Calendar.getInstance();
		e.set(Calendar.YEAR, 2013);
		e.set(Calendar.MONTH, Calendar.DECEMBER);
		e.set(Calendar.DAY_OF_MONTH, 26);
		e.set(Calendar.HOUR_OF_DAY, 0);
		e.set(Calendar.MINUTE, 0);
		e.set(Calendar.MILLISECOND, 0);

		if(c.after(e))
			return;

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
	public void onPlayerInterract(PlayerInteractEvent event)
	{
		if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && event.entity.isSneaking() && event.entityPlayer.getCurrentEquippedItem() != null)
		{
			if(Block.blocksList[event.entity.worldObj.getBlockId(event.x, event.y, event.z)] instanceof BaseBlockContainer && event.face == 1)
			{
				event.setCanceled(true);
			}
		}
	}
}
