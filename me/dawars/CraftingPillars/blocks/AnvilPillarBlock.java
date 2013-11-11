package me.dawars.CraftingPillars.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tile.TileEntityAnvilPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AnvilPillarBlock extends BaseBlockContainer
{
	public AnvilPillarBlock(int id, Material mat)
	{
		super(id, mat);
		this.setCreativeTab(null);
	}
	
	@Override
	public int getRenderType()
	{
		return CraftingPillars.anvilPillarRenderID;
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
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 3;
		
		if(l != 0 && l != 2)
		{
			this.setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
		}
		else
		{
			this.setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
		}
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		TileEntityAnvilPillar pillarTile = (TileEntityAnvilPillar) world.getBlockTileEntity(x, y, z);
		
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int meta = determineOrientation(world, x, y, z, entity);
		/*
		 * if(!world.isRemote) System.out.println("Meta: "+meta);
		 */
		world.setBlockMetadataWithNotify(x, y, z, meta, 0);
	}
	
	public static int determineOrientation(World world, int x, int y, int z, EntityLivingBase entity)
	{
		// Used for up and down orientation
		
		/*
		 * if (MathHelper.abs((float)entity.posX - (float)x) < 2.0F &&
		 * MathHelper.abs((float)entity.posZ - (float)z) < 2.0F) { double d0 =
		 * entity.posY + 1.82D - (double)entity.yOffset;
		 * 
		 * if (d0 - (double)y > 2.0D) { return 1; }
		 * 
		 * if ((double)y - d0 > 0.0D) { return 0; } }
		 */
		
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		// Direction numbers
		// return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
		return l;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntityAnvilPillar pillarTile = (TileEntityAnvilPillar) world.getBlockTileEntity(x, y, z);
		
		hitX *= 16F;
		hitZ *= 16F;
		
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0)
		{
			hitX = 16F - hitX;
			hitZ = 16F - hitZ;
		}
		if(meta == 1)
		{
			float s = hitX;
			hitX = 16F - hitZ;
			hitZ = s;
		}
		/*
		 * else if(meta == 2) { hitX = hitX; hitZ = hitZ; }
		 */
		else if(meta == 3)
		{
			float s = hitX;
			hitX = hitZ;
			hitZ = 16F - s;
		}
		
		return true;
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
