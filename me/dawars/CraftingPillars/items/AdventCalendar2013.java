package me.dawars.CraftingPillars.items;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.gui.GuiIds;
import me.dawars.CraftingPillars.network.packets.PacketCalendarProps;
import me.dawars.CraftingPillars.properties.CalendarPlayerProps2013;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AdventCalendar2013 extends BaseItem
{
	public AdventCalendar2013(int id)
	{
		super(id);
	}
	
	@Override
	/**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
		if(!world.isRemote)
			CraftingPillars.proxy.sendToPlayer((EntityPlayerMP)player, new PacketCalendarProps(CalendarPlayerProps2013.get(player).data).pack());
		player.openGui(CraftingPillars.getInstance(), GuiIds.ADVENT_CALENDAR, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return item;
    }
}
