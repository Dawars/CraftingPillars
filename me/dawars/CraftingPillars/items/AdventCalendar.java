package me.dawars.CraftingPillars.items;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.gui.GuiIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AdventCalendar extends BaseItem
{
	public AdventCalendar(int id)
	{
		super(id);
	}
	
	/**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
    	player.openGui(CraftingPillars.getInstance(), GuiIds.ADVENT_CALENDAR, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return item;
    }
}
