package me.dawars.CraftingPillars.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

public class PacketInGuiClick extends PillarPacket
{
	public int slot;
	
	public PacketInGuiClick(Packet250CustomPayload packet)
	{
		super(CraftingPillars.channelGui);
		this.receive(packet);
	}
	
	public PacketInGuiClick(int slot)
	{
		super(CraftingPillars.channelGui);
		this.slot = slot;
	}

	@Override
	public void writePacketData(DataOutputStream out) throws IOException
	{
		out.writeByte(this.slot);
	}

	@Override
	public void readPacketData(DataInputStream in) throws IOException
	{
		this.slot = (int)in.readByte();
	}

	@Override
	public int getSize()
	{
		return 1;
	}
}
