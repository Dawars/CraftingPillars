package me.dawars.CraftingPillars.blocks;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityExtendPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ExtendPillarBlock extends BaseBlockContainer
{
	public ExtendPillarBlock(int id, Material mat)
	{
		super(id, mat);
	}
	
	@Override
	public int getRenderType()
	{
		return CraftingPillars.extendPillarRenderID;
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
		switch(side)
		{
			case 0:
			case 1:
				meta = 0;
				break;
			case 2:
			case 3:
				meta = 1;
				break;
			case 4:
			case 5:
				meta = 2;
				break;
		}
		
		return meta;
    }
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		TileEntityExtendPillar tile = new TileEntityExtendPillar();
		return tile;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister itemIcon)
	{
		this.blockIcon = itemIcon.registerIcon(CraftingPillars.id + ":craftingPillar_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
	{
		return this.blockIcon;
	}
}
