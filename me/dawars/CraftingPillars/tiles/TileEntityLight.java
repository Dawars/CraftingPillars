package me.dawars.CraftingPillars.tiles;

import java.awt.Color;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityLight extends BaseTileEntity
{
	private static int[] colors = new int[]{
		Color.red.getRGB(),
		Color.green.getRGB(),
		Color.blue.getRGB()
	};
	
	public int color;
	
	public TileEntityLight()
	{
//		this.color = colors[new Random(System.currentTimeMillis()).nextInt(colors.length)];
		this.color = new Random().nextInt(0xFFFFFF);
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
