package me.dawars.CraftingPillars.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

public class PacketClick extends PillarPacket
{
	public int button;
	
	public PacketClick(Packet250CustomPayload packet)
	{
		this.receive(packet);
	}
	
	public PacketClick(int button)
	{
		this.button = button;
	}

	@Override
	public void writePacketData(DataOutputStream out) throws IOException
	{
		out.writeByte(this.button);
	}

	@Override
	public void readPacketData(DataInputStream in) throws IOException
	{
		this.button = (int)in.readByte();
	}

	@Override
	public int getSize()
	{
		return 1;
	}
}
