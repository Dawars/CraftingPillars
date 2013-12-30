package me.dawars.CraftingPillars.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityAnvilPillar;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AnvilPillarBlock extends BasePillar
{
	public AnvilPillarBlock(int id, Material mat)
	{
		super(id, mat);
		if(CraftingPillars.rayTrace)
		{
			new CollisionBox(0, 0F, 16F, 4F, 8F, 24F, 12F);
			new CollisionBox(1, 8F, 16F, 4F, 16F, 24F, 12F);
			new CollisionBox(-1, 4F, 26F, 4F, 12F, 34F, 12F);
		}
		else
		{
			new CollisionBox(0, 0F, 15F, 4F, 8F, 16F, 12F);
			new CollisionBox(1, 8F, 15F, 4F, 16F, 16F, 12F);
			new CollisionBox(-1, 0F, 0F, 3F, 16F, 16F, 13F);
		}
	}

	@Override
	public int getRenderType()
	{
		return CraftingPillars.anvilPillarRenderID;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		int l = world.getBlockMetadata(x, y, z) & 3;
		if(l != 0 && l != 2)
			this.setBlockBounds(3F/16F, 0F, 0F, 13F/16F, 1F, 1F);
		else
			this.setBlockBounds(0F, 0F, 3F/16F, 1F, 1F, 13F/16F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canActionPerformed(World world, int x, int y, int z, int id, int button, EntityPlayer player)
	{
		return id == 2 && button == 0 && ((TileEntityAnvilPillar)world.getBlockTileEntity(x, y, z)).getStackInSlot(2) != null;
	}

	@Override
	public void onActionPerformed(World world, int x, int y, int z, int id, int button, EntityPlayer player)
	{
		if(id == 2 && button == 0)
		{
			TileEntityAnvilPillar tile = (TileEntityAnvilPillar)world.getBlockTileEntity(x, y, z);
			tile.buildItem(player);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		TileEntityAnvilPillar pillarTile = (TileEntityAnvilPillar) world.getBlockTileEntity(x, y, z);

		for(int i = 0; i < pillarTile.getSizeInventory(); i++)
		{
			if(pillarTile.getStackInSlot(i) != null)
			{
				EntityItem itemDropped = new EntityItem(world, x + 0.5D, y, z + 0.5D, pillarTile.getStackInSlot(i));
				itemDropped.motionX = itemDropped.motionY = itemDropped.motionZ = 0D;

				if(pillarTile.getStackInSlot(i).hasTagCompound())
					itemDropped.getEntityItem().setTagCompound((NBTTagCompound) pillarTile.getStackInSlot(i).getTagCompound().copy());

				if(!world.isRemote)
					world.spawnEntityInWorld(itemDropped);
			}
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		TileEntityAnvilPillar tile = new TileEntityAnvilPillar();
		return tile;
	}
}
