package me.dawars.CraftingPillars.properties;

import net.minecraft.entity.Entity;
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
	
	public CalendarPlayerProps(Entity entity)
	{
		this.player = entity;
		this.discovered = new boolean[24];
	}
	
	@Override
	public void init(Entity entity, World world)
	{
		
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		if(this.player.worldObj.isRemote)
			System.out.println("Client save");
		else
			System.out.println("Server save");
		NBTTagCompound props = new NBTTagCompound();
		int data = 0;
		for(int i = 0; i < 24; i++)
			if(this.discovered[i])
				data += Math.pow(2, i);
		props.setInteger("dicovered", data);
		compound.setTag(name, props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		if(this.player.worldObj.isRemote)
			System.out.println("Client load");
		else
			System.out.println("Server load");
		NBTTagCompound props = (NBTTagCompound)compound.getTag(name);
		if(props.hasKey("discovered"))
		{
			int data = props.getInteger("discovered");
			for(int i = 0; i < 24; i++)
				this.discovered[i] = (data/(int)Math.pow(2, i))%2 == 1;
		}
		else
		{
			this.discovered = new boolean[24];
		}
	}
}
