package me.dawars.CraftingPillars.properties;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.network.packets.PacketCalendarProps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CalendarPlayerProps implements IExtendedEntityProperties
{
	public static final String name = "PillarPlayerExtension";
	
	public static void register(Entity entity)
	{
		entity.registerExtendedProperties(name, new CalendarPlayerProps(entity));
	}
	
	public static CalendarPlayerProps get(Entity entity)
	{
		return (CalendarPlayerProps)entity.getExtendedProperties(name);
	}
	
	
	
	public Entity player;
	public boolean[] discovered;
	public int data;
	
	public CalendarPlayerProps(Entity entity)
	{
		this.player = entity;
		this.discovered = new boolean[24];
	}
	
	public void setData(int data)
	{
		this.data = data;
		for(int i = 0; i < 24; i++)
			this.discovered[i] = (this.data/(int)Math.pow(2, i))%2 == 1;
	}
	
	public void setDiscovered(int slot)
	{
		this.discovered[slot] = true;
		this.data = 0;
		for(int i = 0; i < 24; i++)
			if(this.discovered[i])
				this.data += Math.pow(2, i);
	}
	
	@Override
	public void init(Entity entity, World world)
	{
		
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = new NBTTagCompound();
		props.setInteger("discovered", this.data);
		compound.setTag(name, props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound props = (NBTTagCompound)compound.getTag(name);
		if(props.hasKey("discovered"))
		{
			this.setData(props.getInteger("discovered"));
		}
		else
		{
			this.discovered = new boolean[24];
			this.data = 0;
		}
	}
}
