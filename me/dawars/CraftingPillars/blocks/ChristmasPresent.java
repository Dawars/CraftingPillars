package me.dawars.CraftingPillars.blocks;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityPotPillar;
import me.dawars.CraftingPillars.tiles.TileEntityPresent;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ChristmasPresent extends BaseBlockContainer
{

	public ChristmasPresent(int id, Material mat) {
		super(id, mat);
	}

	@Override
	public int getRenderType()
	{
		return CraftingPillars.PresentRenderID;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		TileEntityPresent tile = new TileEntityPresent();
		return tile;
	}
	
}
