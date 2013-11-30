package me.dawars.CraftingPillars.tiles;

import java.awt.Color;
import java.util.Random;

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
		this.color = colors[new Random(System.currentTimeMillis()).nextInt(colors.length)];
	}
}
