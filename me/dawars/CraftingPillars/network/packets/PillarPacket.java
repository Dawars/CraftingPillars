package me.dawars.CraftingPillars.network.packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.network.packet.Packet250CustomPayload;

public abstract class PillarPacket
{
	public Packet250CustomPayload send()
	{
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream(this.getSize());
			DataOutputStream out = new DataOutputStream(bos);
			this.writePacketData(out);
			return new Packet250CustomPayload(CraftingPillars.packetChannel, bos.toByteArray());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public void receive(Packet250CustomPayload packet)
	{
		try
		{
			this.readPacketData(new DataInputStream(new ByteArrayInputStream(packet.data)));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public abstract void writePacketData(DataOutputStream out) throws IOException;
	
	public abstract void readPacketData(DataInputStream in) throws IOException;
	
	public abstract int getSize();
}
