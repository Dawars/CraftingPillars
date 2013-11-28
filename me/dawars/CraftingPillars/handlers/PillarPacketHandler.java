package me.dawars.CraftingPillars.handlers;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.container.ContainerAdventCalendar2013;
import me.dawars.CraftingPillars.network.packets.PacketCalendarProps;
import me.dawars.CraftingPillars.network.packets.PacketInGameClick;
import me.dawars.CraftingPillars.network.packets.PacketInGuiClick;
import me.dawars.CraftingPillars.properties.CalendarPlayerProps2013;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PillarPacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if(packet.channel.equals(CraftingPillars.channelGame))
		{
			PacketInGameClick click = new PacketInGameClick(packet);
			EntityPlayer entity = (EntityPlayer)player;
			//System.out.println("Packet received! "+click.mouseButton+" "+click.btnId+" "+click.x+" "+click.y+" "+click.z);
			BasePillar block = ((BasePillar)Block.blocksList[entity.worldObj.getBlockId(click.x, click.y, click.z)]);
			if(block.buttons.get(click.btnId).slot > -1)
				block.onSlotClicked(entity.worldObj, click.x, click.y, click.z, block.buttons.get(click.btnId).slot, click.mouseButton, entity);
			block.onActionPerformed(entity.worldObj, click.x, click.y, click.z, click.btnId, click.mouseButton, entity);
		}
		else if(packet.channel.equals(CraftingPillars.channelGui))
		{
			PacketInGuiClick click = new PacketInGuiClick(packet);
			EntityPlayer entity = (EntityPlayer)player;
			ContainerAdventCalendar2013 calendar = (ContainerAdventCalendar2013)entity.openContainer;
			ItemStack stack = ContainerAdventCalendar2013.getStackForDrop(click.slot);
			if(stack != null)
			{
				entity.inventory.addItemStackToInventory(stack);
				CalendarPlayerProps2013.get(entity).setDiscovered(click.slot);
			}
		}
		else if(packet.channel.equals(CraftingPillars.channelProps))
		{
			PacketCalendarProps props = new PacketCalendarProps(packet);
			EntityPlayer entity = (EntityPlayer)player;
			//System.out.println("Client Received: "+props.data);
			((CalendarPlayerProps2013)entity.getExtendedProperties(CalendarPlayerProps2013.name)).setData(props.data);
		}
	}
}