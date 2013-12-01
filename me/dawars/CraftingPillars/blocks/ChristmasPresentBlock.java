package me.dawars.CraftingPillars.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityPotPillar;
import me.dawars.CraftingPillars.tiles.TileEntityChristmasPresent;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ChristmasPresentBlock extends BaseBlockContainer
{
	private static ItemStack[] presents;
	
	public static void init()
	{
		// TODO presents
		presents = new ItemStack[]{
			new ItemStack(CraftingPillars.blockChristmasPresent, 1, 0),
			new ItemStack(CraftingPillars.blockChristmasPresent, 1, 1),
			new ItemStack(CraftingPillars.itemWinterFood2013, 16, 0),//more food
			new ItemStack(CraftingPillars.itemElysiumLoreBook, 1, 0),
			new ItemStack(CraftingPillars.blockCraftingPillar, 1, 0),
			new ItemStack(CraftingPillars.itemRibbonDiamond, 2, 0),
			new ItemStack(CraftingPillars.itemDiscElysium, 1, 0)
		};
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			EntityItem item = new EntityItem(world, x+0.5D, y+0.5D, z+0.5D);
			item.setEntityItemStack(presents[CraftingPillars.rand.nextInt(presents.length)].copy());
			if(item.getEntityItem().itemID == CraftingPillars.blockChristmasPresent.blockID)
				player.addStat(CraftingPillars.achievementRecursion3, 1);
			world.spawnEntityInWorld(item);
			world.setBlock(x, y, z, 0);
		}
	}
	
	public ChristmasPresentBlock(int id, Material mat)
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
		TileEntityChristmasPresent tile = new TileEntityChristmasPresent();
		return tile;
	}

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
	@SideOnly(Side.CLIENT)
	@Override
    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
    }
	/**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int par1)
    {
        return par1;
    }
	/**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
	@Override
    protected ItemStack createStackedBlock(int par1)
    {
        return new ItemStack(this.blockID, 1, par1);
    }
}
