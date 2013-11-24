package me.dawars.CraftingPillars.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.BlockIds;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityFreezerPillar;
import me.dawars.CraftingPillars.tiles.TileEntityFurnacePillar;
import me.dawars.CraftingPillars.tiles.TileEntityTankPillar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FreezerPillarBlock extends BaseBlockContainer
{
	public FreezerPillarBlock(int id, Material mat)
	{
		super(id, mat);
	}
	
	@Override
	public int getRenderType()
	{
		return CraftingPillars.freezerPillarRenderID;
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
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		ItemStack current = entityplayer.inventory.getCurrentItem();
		TileEntityFreezerPillar tank = (TileEntityFreezerPillar) world.getBlockTileEntity(i, j, k);

		if(current == null && !entityplayer.isSneaking())
		{
			tank.showNum = !tank.showNum;
			tank.onInventoryChanged();
		} else {
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(current);
			
			
			// Handle filled containers
			if(fluid != null && fluid.getFluid().getBlockID() == Block.waterStill.blockID/*elysian water*/)
			{
				int qty = tank.fill(ForgeDirection.UNKNOWN, fluid, true);
				
				if(qty != 0 && !entityplayer.capabilities.isCreativeMode)
				{
					entityplayer.getCurrentEquippedItem().stackSize--;

					if(current.getItem().getContainerItemStack(current) != null)
					{
						entityplayer.inventory.addItemStackToInventory(current.getItem().getContainerItemStack(current));
					}
					
				}
				
				return true;
				
				// Handle empty containers
			}
			else
			{
				
				FluidStack available = tank.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
				if(available != null)
				{
					ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, current);
					
					fluid = FluidContainerRegistry.getFluidForFilledItem(filled);
					
					if(fluid != null)
					{
						if(!entityplayer.capabilities.isCreativeMode)
						{
							if(current.stackSize > 1)
							{
								if(!entityplayer.inventory.addItemStackToInventory(filled))
									return false;
								else
								{
									entityplayer.getCurrentEquippedItem().stackSize--;
								}
							}
							else
							{
								entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, filled);
							}
						}
						tank.drain(ForgeDirection.UNKNOWN, fluid.amount, true);
						return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			TileEntityFreezerPillar pillarTile = (TileEntityFreezerPillar) world.getBlockTileEntity(x, y, z);
			
			if(pillarTile.getStackInSlot(0) != null)
			{
				if(player.isSneaking())
				{
					pillarTile.dropItemFromSlot(0, pillarTile.getStackInSlot(0).stackSize, player);
				}
				else
				{
					ItemStack itemStack = pillarTile.getStackInSlot(0).copy();
					itemStack.stackSize = 1;
					pillarTile.dropItemFromSlot(0, 1, player);
				}
			}
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		if(!world.isRemote)
		{
			TileEntityFreezerPillar pillarTile = (TileEntityFreezerPillar) world.getBlockTileEntity(x, y, z);
			
				if(pillarTile.getStackInSlot(0) != null)
				{
					EntityItem itemDropped = new EntityItem(world, x + 0.5D, y, z + 0.5D, pillarTile.getStackInSlot(0));
					itemDropped.motionX = itemDropped.motionY = itemDropped.motionZ = 0D;
					
					if(pillarTile.getStackInSlot(0).hasTagCompound())
						itemDropped.getEntityItem().setTagCompound((NBTTagCompound) pillarTile.getStackInSlot(0).getTagCompound().copy());
					
					world.spawnEntityInWorld(itemDropped);
			}
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		TileEntityFreezerPillar tile = new TileEntityFreezerPillar();
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