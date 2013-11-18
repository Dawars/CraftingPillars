package me.dawars.CraftingPillars.client;

import me.dawars.CraftingPillars.blocks.BasePillar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindingReplaceUse extends KeyBindingReplace
{
	public KeyBindingReplaceUse()
	{
		super(Minecraft.getMinecraft().gameSettings.keyBindUseItem);
	}
	
	@Override
	public boolean onPressEvent()
	{
		boolean b = BasePillar.doClick(2);
		if(b)
			Minecraft.getMinecraft().thePlayer.swingItem();
		return b;
	}
}
