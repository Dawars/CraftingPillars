package me.dawars.CraftingPillars.handlers;

import java.util.EnumSet;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.network.packets.PacketClick;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;

public class ClickHandler extends KeyHandler
{
	private static KeyBinding[] bindings = new KeyBinding[]{Minecraft.getMinecraft().gameSettings.keyBindAttack, Minecraft.getMinecraft().gameSettings.keyBindUseItem};
	private static boolean[] repeat = new boolean[]{true, true};
	
	public ClickHandler()
	{
		super(bindings, repeat);
	}

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

	@Override
	public String getLabel()
	{
		return CraftingPillars.id+" keyhandler";
	}
	
	boolean pleft, pright, cleft, cright;

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		if(Minecraft.getMinecraft().inGameHasFocus)
		{
			if(kb.keyCode == Minecraft.getMinecraft().gameSettings.keyBindAttack.keyCode)
			{
				if(!pleft)
				{
					cleft = doClick(0);
					pleft = true;
				}
				if(cleft)
				{
					Minecraft.getMinecraft().gameSettings.keyBindAttack.pressed = false;
					Minecraft.getMinecraft().gameSettings.keyBindAttack.pressTime = 0;
				}
			}
			else
			{
				if(!pright)
				{
					cright = doClick(2);
					pright = true;
				}
				if(cright)
				{
					Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
					Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressTime = 0;
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
		if(kb.keyCode == Minecraft.getMinecraft().gameSettings.keyBindAttack.keyCode)
		{
			pleft = false;
			cleft = false;
		}
		else
		{
			pright = false;
			cright = false;
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}
}
