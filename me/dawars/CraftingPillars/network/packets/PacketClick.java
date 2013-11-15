package me.dawars.CraftingPillars.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

public class PacketClick extends PillarPacket
{
	public int button, x, y, z;
	
	public PacketClick(Packet250CustomPayload packet)
	{
		this.receive(packet);
	}
	
	public PacketClick(int button, int x, int y, int z)
	{
		this.button = button;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void writePacketData(DataOutputStream out) throws IOException
	{
		out.writeByte(this.button);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
	}

	@Override
	public void readPacketData(DataInputStream in) throws IOException
	{
		this.button = (int)in.readByte();
		this.x = in.readInt();
		this.y = in.readInt();
		this.z = in.readInt();
	}

	@Override
	public int getSize()
	{
		return 13;
	}
}
