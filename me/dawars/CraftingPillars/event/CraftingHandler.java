package me.dawars.CraftingPillars.event;

import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler 
{
        @Override
        public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) 
        {
        	if (item.itemID == CraftingPillars.blockExtendPillar.blockID)
            {
                    player.addStat(CraftingPillars.achievementGettingStarted, 1);
            }
        }

        @Override
        public void onSmelting(EntityPlayer player, ItemStack item) 
        {

        }
}