package me.dawars.CraftingPillars.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

public class PacketClick extends PillarPacket
{
	public int mouseButton, btnId, x, y, z;
	
	public PacketClick(Packet250CustomPayload packet)
	{
		this.receive(packet);
	}
	
	public PacketClick(int mouseButton, int button, int x, int y, int z)
	{
		this.mouseButton = mouseButton;
		this.btnId = button;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void writePacketData(DataOutputStream out) throws IOException
	{
		out.writeByte(this.mouseButton);
		out.writeByte(this.btnId);
		out.writeInt(this.x);
		out.writeInt(this.y);
		out.writeInt(this.z);
	}

	@Override
	public void readPacketData(DataInputStream in) throws IOException
	{
		this.mouseButton = (int)in.readByte();
		this.btnId = (int)in.readByte();
		this.x = in.readInt();
		this.y = in.readInt();
		this.z = in.readInt();
	}

	@Override
	public int getSize()
	{
		return 14;
	}
}
