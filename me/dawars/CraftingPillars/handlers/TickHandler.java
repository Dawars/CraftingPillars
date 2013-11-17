package me.dawars.CraftingPillars.handlers;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.client.KeyBindingInterceptor;
import me.dawars.CraftingPillars.network.packets.PacketClick;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.GameSettings;
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
							//System.out.println("Packet sent! "+button+" "+id+" "+x+" "+y+" "+z);
							CraftingPillars.proxy.sendToServer(new PacketClick(button, id, x, y, z).pack());
							return true;
						}
					}
		return false;
	}
	
	boolean pleft, pright;
	GameSettings gs;
	KeyBindingInterceptor intLeft, intRight;
	
	public TickHandler()
	{
		gs = Minecraft.getMinecraft().gameSettings;
		intLeft = new KeyBindingInterceptor(gs.keyBindAttack);
		intRight = new KeyBindingInterceptor(gs.keyBindUseItem);
		intLeft.setInterceptionActive(true);
		intRight.setInterceptionActive(true);
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		// This code doesn't clear any presses
		if(intLeft.isKeyDown())
		{
			if(!pleft)
			{
				if(doClick(0))
					pleft = true;
			}
			
			if(pleft)
				intLeft.retrieveClick();
		}
		else
			pleft = false;
		
		if(intRight.isKeyDown())
		{
			if(!pright)
			{
				if(doClick(2))
					pright = true;
			}
			
			if(pright)
				intRight.retrieveClick();
		}
		else
			pright = false;
		
		// This is my code, it works fine expect the first tick of click
		/*if(gs.keyBindAttack.pressed)
		{
			if(!pleft && doClick(0))
			{
				pleft = true;
				gs.keyBindAttack.pressed = false;
			}
		}
		else
		{
			pleft = false;
		}
		
		if(gs.keyBindUseItem.pressed)
		{
			System.out.println("right");
			if(!pright && doClick(2))
			{
				pright = true;
				gs.keyBindUseItem.pressed = false;
			}
		}
		else
		{
			pright = false;
		}*/
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
