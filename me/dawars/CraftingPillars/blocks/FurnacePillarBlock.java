package me.dawars.CraftingPillars.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tile.TileEntityCraftingPillar;
import me.dawars.CraftingPillars.tile.TileEntityFurnacePillar;
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
import net.minecraft.world.World;

public class FurnacePillarBlock extends BaseBlockContainer
{
	public FurnacePillarBlock(int id, Material mat)
	{
		super(id, mat);
	}
	
	@Override
	public int getRenderType()
	{
		return CraftingPillars.furnacePillarRenderID;
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
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		TileEntityFurnacePillar pillarTile = (TileEntityFurnacePillar) world.getBlockTileEntity(x, y, z);
		
		if(pillarTile.getStackInSlot(2) != null)
		{
			pillarTile.dropItemFromSlot(2, pillarTile.getStackInSlot(2).stackSize);
		}
		
		/*if(workTile.getStackInSlot(workTile.getSizeInventory()) != null)
		{
			workTile.craftItem(player);
		}*/
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int meta = determineOrientation(world, x, y, z, entity);
		/*if(!world.isRemote)
			System.out.println("Meta: "+meta);*/
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
		TileEntityFurnacePillar pillarTile = (TileEntityFurnacePillar) world.getBlockTileEntity(x, y, z);
		
		hitX *= 16F;
		hitZ *= 16F;
		
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0)
		{
			hitX = 16F-hitX;
			hitZ = 16F-hitZ;
		}
		if(meta == 1)
		{
			float s = hitX;
			hitX = 16F-hitZ;
			hitZ = s;
		}
		/*else if(meta == 2)
		{
			hitX = hitX;
			hitZ = hitZ;
		}*/
		else if(meta == 3)
		{
			float s = hitX;
			hitX = hitZ;
			hitZ = 16F-s;
		}
		
		/*if(!world.isRemote)
		{
			System.out.println("Hit: "+hitX+" "+hitZ);
			
			if(1F < hitX && hitX < 7F && 5F < hitZ && hitZ < 11F)
				System.out.println("Slot: 0");
			
			if(9F < hitX && hitX < 15F && 5F < hitZ && hitZ < 11F)
				System.out.println("Slot: 2");
		}*/
		
		if(player.isSneaking())
		{
			if(hitY < 1F)
			{
				pillarTile.dropItemFromSlot(1, 1);
			}
			else
			{
				if(1F < hitX && hitX < 7F && 5F < hitZ && hitZ < 11F)
					pillarTile.dropItemFromSlot(0, 1);
				
				if(9F < hitX && hitX < 15F && 5F < hitZ && hitZ < 11F)
					pillarTile.dropItemFromSlot(2, 1);
			}
		}
		else if(player.getCurrentEquippedItem() != null)
		{
			if(hitY < 1F)
			{
				if(pillarTile.getStackInSlot(1) == null)
				{
					if(!player.capabilities.isCreativeMode)
						player.getCurrentEquippedItem().stackSize--;
					
					ItemStack in = new ItemStack(player.getCurrentEquippedItem().itemID, 1, player.getCurrentEquippedItem().getItemDamage());
					pillarTile.setInventorySlotContents(1, in);
				}
				else
					if((pillarTile.getStackInSlot(1).itemID == player.getCurrentEquippedItem().itemID) && (pillarTile.getStackInSlot(1).stackSize < pillarTile.getStackInSlot(1).getMaxStackSize()))
					{
						if(!player.capabilities.isCreativeMode)
							player.getCurrentEquippedItem().stackSize--;
						
						pillarTile.decrStackSize(1, -1);
						pillarTile.onInventoryChanged();
					}
			}
			else
			{
				if(1F < hitX && hitX < 7F && 5F < hitZ && hitZ < 11F)
				{
					if(pillarTile.getStackInSlot(0) == null)
					{
						if(!player.capabilities.isCreativeMode)
							player.getCurrentEquippedItem().stackSize--;
						
						ItemStack in = new ItemStack(player.getCurrentEquippedItem().itemID, 1, player.getCurrentEquippedItem().getItemDamage());
						pillarTile.setInventorySlotContents(0, in);
					}
					else
						if((pillarTile.getStackInSlot(0).itemID == player.getCurrentEquippedItem().itemID) && (pillarTile.getStackInSlot(0).stackSize < pillarTile.getStackInSlot(0).getMaxStackSize()))
						{
							if(!player.capabilities.isCreativeMode)
								player.getCurrentEquippedItem().stackSize--;
							
							pillarTile.decrStackSize(0, -1);
							pillarTile.onInventoryChanged();
						}
				}
				
				/*if(9F < hitX && hitX < 15F && 5F < hitZ && hitZ < 11F)
				{
					if(workTile.getStackInSlot(2) == null)
					{
						if(!player.capabilities.isCreativeMode)
							player.getCurrentEquippedItem().stackSize--;
						
						ItemStack in = new ItemStack(player.getCurrentEquippedItem().itemID, 1, player.getCurrentEquippedItem().getItemDamage());
						workTile.setInventorySlotContents(2, in);
					}
					else
						if((workTile.getStackInSlot(2).itemID == player.getCurrentEquippedItem().itemID) && (workTile.getStackInSlot(2).stackSize < workTile.getStackInSlot(2).getMaxStackSize()))
						{
							if(!player.capabilities.isCreativeMode)
								player.getCurrentEquippedItem().stackSize--;
							
							workTile.decrStackSize(2, -1);
							workTile.onInventoryChanged();
						}
				}*/
			}
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		TileEntityFurnacePillar workTile = (TileEntityFurnacePillar) world.getBlockTileEntity(x, y, z);
		
		for(int i = 0; i < workTile.getSizeInventory(); i++)
		{
			if(workTile.getStackInSlot(i) != null)
			{
				EntityItem itemDropped = new EntityItem(world, x+0.5D, y, z+0.5D, workTile.getStackInSlot(i));
				itemDropped.motionX = itemDropped.motionY = itemDropped.motionZ = 0D;
				
				if(workTile.getStackInSlot(i).hasTagCompound())
					itemDropped.getEntityItem().setTagCompound((NBTTagCompound) workTile.getStackInSlot(i).getTagCompound().copy());
				
				if(!world.isRemote)
					world.spawnEntityInWorld(itemDropped);
			}
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		TileEntityFurnacePillar tile = new TileEntityFurnacePillar();
		return tile;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
		if(((TileEntityFurnacePillar)world.getBlockTileEntity(x, y, z)).burnTime > 0)
	        //for(int i = 0; i < rand.nextInt(3)+1; i++)
	        {
	        	double rx = x + rand.nextDouble()/2 + 0.25D;
	        	double ry = y + rand.nextDouble()/2 + 0.25D;
	        	double rz = z + rand.nextDouble()/2 + 0.25D;
	        	
		        world.spawnParticle("smoke", rx, ry, rz, 0D, 0D, 0D);
		        world.spawnParticle("flame", rx, ry, rz, 0D, 0D, 0D);
	        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister itemIcon)
	{
		this.blockIcon = itemIcon.registerIcon(CraftingPillars.id + ":fancy_workbench_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
	{
		return this.blockIcon;
	}
}
