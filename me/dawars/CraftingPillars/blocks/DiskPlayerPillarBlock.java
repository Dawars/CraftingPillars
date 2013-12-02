package me.dawars.CraftingPillars.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityBrewingPillar;
import me.dawars.CraftingPillars.tiles.TileEntityCraftingPillar;
import me.dawars.CraftingPillars.tiles.TileEntityDiskPlayerPillar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukeBox;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityRecordPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class DiskPlayerPillarBlock extends BaseBlockContainer
{
	public DiskPlayerPillarBlock(int id, Material mat)
	{
		super(id, mat);
	}
	
	@Override
	public int getRenderType()
	{
		return CraftingPillars.diskPlayerRenderID;
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

	/**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
    	TileEntityDiskPlayerPillar tile = ((TileEntityDiskPlayerPillar)world.getBlockTileEntity(x, y, z));
        if (tile.getDisk() == null)
        {
        	if(side == 1)
        	{
	        	ItemStack disk = player.getCurrentEquippedItem();
	        	if(disk != null && disk.getItem() instanceof ItemRecord)
	        	{
	        		if(disk.isItemEqual(new ItemStack(CraftingPillars.itemDiscElysium)))
	        			player.addStat(CraftingPillars.achievementDisc, 1);
		        	insertRecord(world, x, y, z, player.getCurrentEquippedItem());
		            world.playAuxSFXAtEntity((EntityPlayer)null, 1005, x, y, z, disk.itemID);
		            if(!player.capabilities.isCreativeMode)
		            	--player.getCurrentEquippedItem().stackSize;
		            return true;
	        	}
        	}
        }
        else
        {
        	if(side == 1)
    		{
    			this.ejectRecord(world, x, y, z);
    			return true;
    		}
        }
        if(side != 1) tile.showNum = !tile.showNum;
		return true;
    }

    /**
     * Insert the specified music disc in the jukebox at the given coordinates
     */
    public void insertRecord(World world, int x, int y, int z, ItemStack item)
    {
        if (!world.isRemote)
        {
        	TileEntityDiskPlayerPillar tileentityrecordplayer = (TileEntityDiskPlayerPillar)world.getBlockTileEntity(x, y, z);

            if (tileentityrecordplayer != null)
            {
                tileentityrecordplayer.setDisk(item.copy());
            }
        }
    }

    /**
     * Ejects the current record inside of the jukebox.
     */
    public void ejectRecord(World world, int x, int y, int z)
    {
        if (!world.isRemote)
        {
        	TileEntityDiskPlayerPillar tileentityrecordplayer = (TileEntityDiskPlayerPillar)world.getBlockTileEntity(x, y, z);

            if (tileentityrecordplayer != null)
            {
                ItemStack itemstack = tileentityrecordplayer.getDisk();

                if (itemstack != null)
                {
                    world.playAuxSFX(1005, x, y, z, 0);
                    world.playRecord((String)null, x, y, z);
                    tileentityrecordplayer.setDisk((ItemStack)null);
                    float f = 0.7F;
                    double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.2D + 0.6D;
                    double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    ItemStack itemstack1 = itemstack.copy();
                    EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, itemstack1);
                    entityitem.delayBeforeCanPickup = 10;
                    world.spawnEntityInWorld(entityitem);
                }
            }
        }
    }

    /**
     * Called on server worlds only when the block has been replaced by a different block ID, or the same block with a
     * different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old
     * metadata
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        this.ejectRecord(par1World, par2, par3, par4);
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!par1World.isRemote)
        {
            super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityDiskPlayerPillar();
    }

    /**
     * If this returns true, then comparators facing away from this block will use the value from
     * getComparatorInputOverride instead of the actual redstone signal strength.
     */
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    /**
     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
     * strength when this block inputs to a comparator.
     */
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        ItemStack itemstack = ((TileEntityDiskPlayerPillar)par1World.getBlockTileEntity(par2, par3, par4)).getDisk();
        return itemstack == null ? 0 : itemstack.itemID + 1 - Item.record13.itemID;
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
