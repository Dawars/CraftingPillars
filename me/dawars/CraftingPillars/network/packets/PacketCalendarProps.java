package me.dawars.CraftingPillars.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;
import me.dawars.CraftingPillars.CraftingPillars;

public class PacketCalendarProps extends PillarPacket
{
	public int data;
	
	public PacketCalendarProps(Packet250CustomPayload packet)
	{
		super(CraftingPillars.channelProps);
		this.receive(packet);
	}
	
	public PacketCalendarProps(int data)
	{
		super(CraftingPillars.channelProps);
		this.data = data;
	}

	@Override
	public void writePacketData(DataOutputStream out) throws IOException
	{
		out.writeInt(this.data);
	}

	@Override
	public void readPacketData(DataInputStream in) throws IOException
	{
		this.data = in.readInt();
	}

	@Override
	public int getSize()
	{
		return 4;
	}
}
