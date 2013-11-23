package me.dawars.CraftingPillars.handlers;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.container.ContainerAdventCalendar;
import me.dawars.CraftingPillars.network.packets.PacketInGameClick;
import me.dawars.CraftingPillars.network.packets.PacketInGuiClick;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
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
			ContainerAdventCalendar calendar = (ContainerAdventCalendar)entity.openContainer;
			ItemStack stack = (ItemStack)calendar.inventoryItemStacks.get(click.slot);
			if(stack != null)
			{
				entity.inventory.addItemStackToInventory(stack);
				calendar.setInventorySlotContents(click.slot, null);
				entity.inventory.onInventoryChanged();
			}
		}
	}
}