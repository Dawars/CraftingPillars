package me.dawars.CraftingPillars.client;

import me.dawars.CraftingPillars.blocks.BasePillar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindingReplaceAttack extends KeyBindingReplace
{
	public KeyBindingReplaceAttack()
	{
		super(Minecraft.getMinecraft().gameSettings.keyBindAttack);
	}
	
	@Override
	public boolean onPressEvent()
	{
		boolean b = BasePillar.doClick(0);
		if(b)
			Minecraft.getMinecraft().thePlayer.swingItem();
		return b;
	}
}
