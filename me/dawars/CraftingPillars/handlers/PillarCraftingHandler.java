package me.dawars.CraftingPillars.handlers;

import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

public class PillarCraftingHandler implements ICraftingHandler
{
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix)
	{
		if(item.itemID == CraftingPillars.blockBasePillar.blockID)
			player.addStat(CraftingPillars.achievementGettingStarted, 1);
		if(item.itemID == CraftingPillars.blockFreezerPillar.blockID)
			player.addStat(CraftingPillars.achievementCompressingLiquids, 1);

		if(item.itemID == Item.diamond.itemID)
		{
			for(int i = 0; i < craftMatrix.getSizeInventory(); i++)
			{
				if(craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(i).itemID == CraftingPillars.itemRibbonDiamond.itemID){
					player.addStat(CraftingPillars.achievementDiamond, 1);
					break;
				}
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item)
	{

	}
}