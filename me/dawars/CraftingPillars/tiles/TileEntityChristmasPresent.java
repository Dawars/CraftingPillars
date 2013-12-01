package me.dawars.CraftingPillars.tiles;

import java.util.Random;

import me.dawars.CraftingPillars.renderer.RenderPresent;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityChristmasPresent extends BaseTileEntity
{
	public int color;
	
	public TileEntityChristmasPresent()
	{
		this.color = new Random(System.currentTimeMillis()).nextInt(RenderPresent.colors.length/2);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.color = nbt.getInteger("color");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("color", this.color);
	}
}
