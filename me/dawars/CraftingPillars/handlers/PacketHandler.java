package me.dawars.CraftingPillars.handlers;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.network.packets.PacketClick;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if(packet.channel.equals(CraftingPillars.packetChannel))
		{
			PacketClick click = new PacketClick(packet);
			EntityPlayer entity = (EntityPlayer)player;
			System.out.println("Packet received! "+click.mouseButton+" "+click.btnId+" "+click.x+" "+click.y+" "+click.z);
			((BasePillar)Block.blocksList[entity.worldObj.getBlockId(click.x, click.y, click.z)]).onActionPerformed(entity.worldObj, click.x, click.y, click.z, click.btnId, click.mouseButton, entity);
		}
	}
}