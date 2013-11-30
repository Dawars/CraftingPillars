package me.dawars.CraftingPillars.blocks;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityLight;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ChristmasLightBlock extends BaseBlockContainer
{
	public ChristmasLightBlock(int id, Material mat)
	{
		super(id, mat);
		this.setLightValue(1F);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		switch(world.getBlockMetadata(x, y, z))
		{
			case 0:
				this.setBlockBounds(4F/16F, 6F/16F, 4F/16F, 12F/16F, 16F/16F, 12F/16F);
				break;
			case 1:
				this.setBlockBounds(4F/16F, 0F/16F, 4F/16F, 12F/16F, 10F/16F, 12F/16F);
				break;
			case 2:
				this.setBlockBounds(4F/16F, 4F/16F, 6F/16F, 12F/16F, 12F/16F, 16F/16F);
				break;
			case 3:
				this.setBlockBounds(4F/16F, 4F/16F, 0F/16F, 12F/16F, 12F/16F, 10F/16F);
				break;
			case 4:
				this.setBlockBounds(6F/16F, 4F/16F, 4F/16F, 16F/16F, 12F/16F, 12F/16F);
				break;
			case 5:
				this.setBlockBounds(0F/16F, 4F/16F, 4F/16F, 10F/16F, 12F/16F, 12F/16F);
				break;
		}
	}
	
	@Override
	public int getRenderType()
	{
		return CraftingPillars.lightRenderID;
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
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
	{
		return side;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityLight();
	}
}
