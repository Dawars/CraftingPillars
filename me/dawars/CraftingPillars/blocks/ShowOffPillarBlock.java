package me.dawars.CraftingPillars.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tile.TileEntityCraftingPillar;
import me.dawars.CraftingPillars.tile.TileEntityShowOffPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ShowOffPillarBlock extends BaseBlockContainer
{
	public ShowOffPillarBlock(int id, Material mat)
	{
		super(id, mat);
	}
	
	@Override
	public int getRenderType()
	{
		return CraftingPillars.showOffPillarRenderID;
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
		
		TileEntityShowOffPillar workTile = (TileEntityShowOffPillar) world.getBlockTileEntity(x, y, z);

		if(hitY < 1F && !player.isSneaking())
		{
			workTile.showNum = !workTile.showNum;
			workTile.onInventoryChanged();
		}
		
		if(hitY == 1F)
		{
			if(player.isSneaking())//pick out
			{
				if(workTile.getStackInSlot(0) != null)
				{
					workTile.dropItemFromSlot(0);
				}
			}
			else if(player.getCurrentEquippedItem() != null)
			{//put in
				player.addStat(CraftingPillars.achievementShowoff, 1);
				if(workTile.getStackInSlot(0) == null)
				{//slot empty
					if(!player.capabilities.isCreativeMode)
						player.getCurrentEquippedItem().stackSize--;
					
					ItemStack in = new ItemStack(player.getCurrentEquippedItem().itemID, 1, player.getCurrentEquippedItem().getItemDamage());
					workTile.setInventorySlotContents(0, in);
				}
				else if((workTile.getStackInSlot(0).isItemEqual(player.getCurrentEquippedItem())) && (workTile.getStackInSlot(0).stackSize < workTile.getStackInSlot(0).getMaxStackSize()))
				{//slot not empty
					if(!player.capabilities.isCreativeMode)
						player.getCurrentEquippedItem().stackSize--;
					
					workTile.decrStackSize(0, -1);
					workTile.onInventoryChanged();
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
			TileEntityShowOffPillar workTile = (TileEntityShowOffPillar) world.getBlockTileEntity(x, y, z);
			
			if(workTile.getStackInSlot(0) != null)
			{
				EntityItem itemDropped = new EntityItem(world, x + 0.1875D, y + 1D, z + 0.1875D, workTile.getStackInSlot(0));
				itemDropped.motionX = itemDropped.motionY = itemDropped.motionZ = 0D;
				
				if(workTile.getStackInSlot(0).hasTagCompound())
					itemDropped.getEntityItem().setTagCompound((NBTTagCompound) workTile.getStackInSlot(0).getTagCompound().copy());
				
				world.spawnEntityInWorld(itemDropped);
			}
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		TileEntityShowOffPillar tile = new TileEntityShowOffPillar();
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
