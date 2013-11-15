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
		//System.out.println("received packet");
		if(packet.channel.equals(CraftingPillars.packetChannel))
		{
			PacketClick click = new PacketClick(packet);
			EntityPlayer entity = (EntityPlayer)player;
			
			System.out.println("Player: "+(player == null));
			System.out.println("Entity: "+(entity == null));
			System.out.println("World: "+(entity.worldObj == null));
			System.out.println("BlockId: "+entity.worldObj.getBlockId(click.x, click.y, click.z));
			System.out.println("Block: "+(Block.blocksList[entity.worldObj.getBlockId(click.x, click.y, click.z)] == null));
			System.out.println("Instance: "+(Block.blocksList[entity.worldObj.getBlockId(click.x, click.y, click.z)] instanceof BasePillar));
			//((BasePillar)Block.blocksList[entity.worldObj.getBlockId(click.x, click.y, click.z)]).handleClickEvent(click.button, click.x, click.y, click.z, entity);
		}
	}
}