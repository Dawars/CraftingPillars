package me.dawars.CraftingPillars.blocks;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityPotPillar;
import me.dawars.CraftingPillars.tiles.TileEntityPresent;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ChristmasPresent extends BaseBlockContainer
{
	private static ItemStack[] presents;
	
	public static void init()
	{
		// TODO presents
		presents = new ItemStack[]{
				new ItemStack(CraftingPillars.blockPresent, 1, 0),
				new ItemStack(CraftingPillars.itemWinterFood2013, 16, 0),
				new ItemStack(CraftingPillars.itemElysiumLoreBook, 1, 0),
				new ItemStack(CraftingPillars.blockCraftingPillar, 1, 0)
		};
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			EntityItem item = new EntityItem(world, x+0.5D, y+0.5D, z+0.5D);
			item.setEntityItemStack(presents[CraftingPillars.rand.nextInt(presents.length)].copy());
			//if(item.getEntityItem().itemID == CraftingPillars.blockPresent.blockID)
				// TODO recursion III
			world.spawnEntityInWorld(item);
			world.setBlock(x, y, z, 0);
		}
	}
	
	public ChristmasPresent(int id, Material mat)
	{
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
