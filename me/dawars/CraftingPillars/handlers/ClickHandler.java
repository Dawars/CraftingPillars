package me.dawars.CraftingPillars.handlers;

import java.util.EnumSet;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BasePillar;
import me.dawars.CraftingPillars.network.packets.PacketClick;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class ClickHandler extends KeyHandler
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
	
	public ClickHandler()
	{
		super(new KeyBinding[]{Minecraft.getMinecraft().gameSettings.keyBindAttack, Minecraft.getMinecraft().gameSettings.keyBindUseItem}, new boolean[]{false, false});
	}

	@Override
	public String getLabel()
	{
		return CraftingPillars.name+" KeyHandler";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.theWorld != null && mc.inGameHasFocus && !tickEnd)
		{
			if(kb.keyCode == mc.gameSettings.keyBindAttack.keyCode)
			{
				if(this.doClick(0))
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.keyCode, false);
			}
			else if(kb.keyCode == mc.gameSettings.keyBindUseItem.keyCode)
			{
				if(this.doClick(2))
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.keyCode, false);
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
		
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}
}
