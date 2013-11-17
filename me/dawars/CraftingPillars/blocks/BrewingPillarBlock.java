package me.dawars.CraftingPillars.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityBrewingPillar;
import me.dawars.CraftingPillars.tiles.TileEntityCraftingPillar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BrewingPillarBlock extends BaseBlockContainer
{

	public BrewingPillarBlock(int id, Material mat)
	{
		super(id, mat);
		float f = 3*1/16F;
		this.setBlockBounds(f, 0.0F, f, 1.0F - f, 1.0F, 1.0F - f);
		if(!CraftingPillars.update) this.setCreativeTab(null);
	}
	@Override
	public int getRenderType()
	{
		return CraftingPillars.brewingillarRenderID;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
			return true;
		
		TileEntityBrewingPillar pillarTile = (TileEntityBrewingPillar) world.getBlockTileEntity(x, y, z);
		int topSlot = 4;
		int sideSlot = side-2;
		
		if(!player.isSneaking() && player.inventory.getCurrentItem() == null)
		{
			pillarTile.showNum = !pillarTile.showNum;
			pillarTile.onInventoryChanged();
		}
		
		if(player.isSneaking())
		{
			if(side == 1)
			{
				pillarTile.dropItemFromSlot(topSlot, 1, player);
			}
			else
			{
				if(side > 1)
					pillarTile.dropItemFromSlot(sideSlot, 1, player);
			}
		}
		else if(player.getCurrentEquippedItem() != null)
		{
			if(side > 1)
			{
				if(pillarTile.getStackInSlot(sideSlot) == null)
				{
					ItemStack in = new ItemStack(player.getCurrentEquippedItem().itemID, 1, player.getCurrentEquippedItem().getItemDamage());
					if(in.getItem() instanceof ItemPotion || in.itemID == Item.glassBottle.itemID)
					{
						pillarTile.setInventorySlotContents(sideSlot, in);
						
						if(!player.capabilities.isCreativeMode)
							player.getCurrentEquippedItem().stackSize--;
					}
				}
			}
			else if(side == 1)
			{
				if(pillarTile.getStackInSlot(topSlot) == null)
				{
					ItemStack in = new ItemStack(player.getCurrentEquippedItem().itemID, 1, player.getCurrentEquippedItem().getItemDamage());
					if(Item.itemsList[in.itemID].isPotionIngredient())
					{
						pillarTile.setInventorySlotContents(topSlot, in);

						if(!player.capabilities.isCreativeMode)
							player.getCurrentEquippedItem().stackSize--;
					}
				}
				else if(pillarTile.getStackInSlot(topSlot).isItemEqual(player.getCurrentEquippedItem()) && (pillarTile.getStackInSlot(topSlot).stackSize < pillarTile.getStackInSlot(topSlot).getMaxStackSize()))
				{
					if(!player.capabilities.isCreativeMode)
						player.getCurrentEquippedItem().stackSize--;
					
					pillarTile.decrStackSize(topSlot, -1);
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		if(!world.isRemote)
		{
			TileEntityBrewingPillar pillarTile = (TileEntityBrewingPillar) world.getBlockTileEntity(x, y, z);
			
			for(int i = 0; i < pillarTile.getSizeInventory(); i++)
			{
				if(pillarTile.getStackInSlot(i) != null)
				{
					EntityItem itemDropped = new EntityItem(world, x + 0.5D, y, z + 0.5D, pillarTile.getStackInSlot(i));
					itemDropped.motionX = itemDropped.motionY = itemDropped.motionZ = 0D;
					
					if(pillarTile.getStackInSlot(i).hasTagCompound())
						itemDropped.getEntityItem().setTagCompound((NBTTagCompound) pillarTile.getStackInSlot(i).getTagCompound().copy());
					
					world.spawnEntityInWorld(itemDropped);
				}
			}
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		TileEntityBrewingPillar tile = new TileEntityBrewingPillar();
		return tile;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		TileEntityBrewingPillar tile = ((TileEntityBrewingPillar) world.getBlockTileEntity(x, y, z));
		if(tile.canBrew() && tile.getBrewTime() > 0)
		{
			for(int i = 0; i < 4; i++)
			{
				if(rand.nextInt(4) <= 1){
					if(tile.getStackInSlot(i) != null)
					{
						int rotI = i;
						if(i == 3) rotI = 0;
						if(i == 0) rotI = 3;
						float subX = 0;
						float subZ = 0;
			
						if(rotI == 0)
							subX = 0.4F;
						if(rotI == 2)
							subX = -0.4F;
						if(rotI == 1)
							subZ = 0.4F;
						if(rotI == 3)
							subZ = -0.4F;
						int j = tile.getStackInSlot(i).getItemDamage();
	                    int k = TileEntityBrewingPillar.getPotionResult(j, tile.getStackInSlot(4));
	                    List list = Item.potion.getEffects(j);
	                    List list1 = Item.potion.getEffects(k);
	                    if (((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null)) || !ItemPotion.isSplash(j) && ItemPotion.isSplash(k))
	                    {
	                    	world.spawnParticle("smoke", x+0.5F + subX, y + 0.7F, z +0.5F + subZ, 0D, 0D, 0D);
	                    }
					}
				}
			}
		}
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
