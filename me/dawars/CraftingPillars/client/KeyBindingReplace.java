package me.dawars.CraftingPillars.client;

import net.minecraft.client.settings.KeyBinding;

public abstract class KeyBindingReplace extends KeyBinding
{
	public KeyBindingReplace(KeyBinding key)
	{
		super(key.keyDescription, key.keyCode);
		/*keybindArray.remove(this);
		KeyBinding.resetKeyBindingArrayAndHash();*/
	}
	
	public abstract boolean onPressEvent();
	
	@Override
	public boolean isPressed()
	{
		boolean b = super.isPressed() && !this.onPressEvent();
		if(!b)
		{
			this.pressTime = 0;
	        this.pressed = false;
		}
		return b;
	}
}
