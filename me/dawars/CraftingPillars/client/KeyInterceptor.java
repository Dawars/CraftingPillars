package me.dawars.CraftingPillars.client;

import net.minecraft.client.settings.KeyBinding;

public class KeyInterceptor extends KeyBinding
{
	public KeyBinding interceptedKeyBinding;
	private boolean intercepting;
	private int interceptedPressTime;
	
	public KeyInterceptor(KeyBinding interceptedKey)
	{
		super(interceptedKey.keyDescription, interceptedKey.keyCode);
		KeyBinding.keybindArray.remove(this);
		this.interceptedKeyBinding = interceptedKey;
		
		this.intercepting = false;
	    this.pressed = false;
	    this.pressTime = 0;
	    this.interceptedPressTime = 0;
	    
	    KeyBinding.resetKeyBindingArrayAndHash();
	}
	
	public void setIntercepting(boolean value)
	{
		this.intercepting = value;
		this.interceptedPressTime = 0;
	}
	
	public boolean isKeyDown()
	{
		this.copyKeyCodeFromOriginal();
		return this.interceptedKeyBinding.pressed;
	}
	
	public boolean retrieveClick()
	{
		if(this.intercepting)
		{
			this.copyKeyCodeFromOriginal();
			this.copyClickInfoFromOriginal();
			
			if(this.interceptedPressTime == 0)
				return false;
			else
			{
				this.interceptedPressTime--;
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isPressed()
	{
		this.copyKeyCodeFromOriginal();
		this.copyClickInfoFromOriginal();
		
		if(this.intercepting)
		{
			this.pressTime = 0;
			this.pressed = false;
			return false;
		}
		else
		{
			if(this.pressTime == 0)
				return false;
			else
			{
				this.pressTime--;
				return true;
			}
		}
	}
	
	public void copyClickInfoFromOriginal()
	{
		this.pressTime += this.interceptedKeyBinding.pressTime;
		this.interceptedPressTime += this.interceptedKeyBinding.pressTime;
		this.pressed = this.interceptedKeyBinding.pressed;
	}
	
	public void copyKeyCodeFromOriginal()
	{
		if(this.keyCode != this.interceptedKeyBinding.keyCode)
		{
			this.keyCode = this.interceptedKeyBinding.keyCode;
			KeyBinding.resetKeyBindingArrayAndHash();
		}
	}
	
	public void clearOriginalEvent()
	{
		this.interceptedKeyBinding.pressTime = 0;
		//this.interceptedKeyBinding.pressed = false;
	}
}
