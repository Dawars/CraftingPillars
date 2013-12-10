package me.dawars.CraftingPillars.handlers;

import java.util.Random;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BaseBlock;
import me.dawars.CraftingPillars.blocks.BaseBlockContainer;
import me.dawars.CraftingPillars.properties.CalendarPlayerProps2013;
import me.dawars.CraftingPillars.world.gen.ChristmasTreeGen;
import mods.elysium.Elysium;
import mods.elysium.block.ElysianBlockSaplingFostimber;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class PillarEventHandler
{
	@ForgeSubscribe
	public void onUseBonemeal(BonemealEvent event) {
		if (!event.world.isRemote) {
			if (event.ID == CraftingPillars.blockChristmasTreeSapling.blockID) {
				if(CraftingPillars.maxTreeState >= 4)
				{
					((ChristmasTreeGen) new ChristmasTreeGen(true, 4)).generate(event.world, event.world.rand, event.X, event.Y, event.Z);
					event.setResult(Result.ALLOW);
				}
			}
		}
	}
	
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
	public void onBreakBlock(BreakEvent event)
	{
		if(event.block instanceof BaseBlockContainer && event.getPlayer().isSneaking())
		{
			event.setCanceled(true);
			event.block.onBlockClicked(event.world, event.x, event.y, event.z, event.getPlayer());
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
