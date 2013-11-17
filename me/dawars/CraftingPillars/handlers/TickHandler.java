package me.dawars.CraftingPillars.handlers;

import java.util.EnumSet;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.client.KeyBindingInterceptor;
import me.dawars.CraftingPillars.network.packets.PacketClick;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler
{
	public boolean doClick(int button)
	{
		World world = Minecraft.getMinecraft().theWorld;
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		for(int x = (int)player.posX-5; x <= (int)player.posX+5; x++)
			for(int y = (int)player.posY-5; y <= (int)player.posY+5; y++)
				for(int z = (int)player.posZ-5; z <= (int)player.posZ+5; z++)
					if(Block.blocksList[world.getBlockId(x, y, z)] instanceof BasePillar)
					{
						int id = ((BasePillar)Block.blocksList[world.getBlockId(x, y, z)]).getClickedButtonId(world, x, y, z, button, player);
						if(id > -1)
						{
							System.out.println("Packet sent! "+button+" "+id+" "+x+" "+y+" "+z);
							CraftingPillars.proxy.sendToServer(new PacketClick(button, id, x, y, z).pack());
							return true;
						}
					}
		return false;
	}
	
	KeyBindingInterceptor attackInterceptor = new KeyBindingInterceptor(Minecraft.getMinecraft().gameSettings.keyBindAttack);
	KeyBindingInterceptor useInterceptor = new KeyBindingInterceptor(Minecraft.getMinecraft().gameSettings.keyBindUseItem);
	
	public TickHandler()
	{
		this.attackInterceptor.setInterceptionActive(true);
		this.useInterceptor.setInterceptionActive(true);
	}
	
	boolean pleft, pright;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if(this.attackInterceptor.isKeyDown())
		{
			if(!pleft && doClick(0))
				this.attackInterceptor.retrieveClick();
			pleft = true;
		}
		else
			pleft = false;
		if(this.useInterceptor.isKeyDown())
		{
			if(!pright && doClick(2))
				this.useInterceptor.retrieveClick();
			pright = true;
		}
		else
			pright = false;
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel()
	{
		return CraftingPillars.name+" TickHandler";
	}
}
