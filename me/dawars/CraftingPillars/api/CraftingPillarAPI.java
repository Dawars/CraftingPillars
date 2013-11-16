package me.dawars.CraftingPillars.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

import me.dawars.CraftingPillars.CraftingPillars;

public class CraftingPillarAPI {
	private static String[] diskTextures = new String[Item.itemsList.length];
	
	public static void addDiskTexture(int id, String url)
	{
		diskTextures[id] = url;
	}
	
	public static String getDiskTexture(int id)
	{
		return diskTextures[id] != null ? diskTextures[id] : "placeholder";
	}
}
