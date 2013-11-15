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
			for(int x = (int)entity.posX-5; x <= (int)entity.posX+5; x++)
				for(int y = (int)entity.posY-5; y <= (int)entity.posY+5; y++)
					for(int z = (int)entity.posZ-5; z <= (int)entity.posZ+5; z++)
						if(Block.blocksList[entity.worldObj.getBlockId(x, y, z)] instanceof BasePillar)
							((BasePillar)Block.blocksList[entity.worldObj.getBlockId(x, y, z)]).handleClickEvent(click.button, x, y, z, entity);
		}
	}
}