package me.dawars.CraftingPillars.tile;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.container.ContainerCraftingPillar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class TileEntityFurnacePillar extends BaseTileEntity implements IInventory, ISidedInventory
{
	private ItemStack[] inventory = new ItemStack[this.getSizeInventory()];
	public int burnTime, cookTime, xp;
	
	// @SideOnly(Side.CLIENT)
	public float rot = 0F;
	
	@Override
	public void updateEntity()
	{
		if(this.worldObj.isRemote)
		{
			this.rot += 0.1F;
			if(this.rot >= 360F)
				this.rot -= 360F;
		}
		
		boolean changed = false;
		
		if(this.burnTime > 0)
			this.burnTime--;
		else if(this.canBurn())
			this.burnItem();
		
		if(!this.worldObj.isRemote && this.burnTime > 0 && this.getStackInSlot(0) != null)
		{
			if(this.cookTime > 0)
				this.cookTime--;
			else if(this.canSmelt())
				this.smeltItem();
		}
		
		if(changed)
			this.onInventoryChanged();
		
		super.updateEntity();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		this.inventory = new ItemStack[this.getSizeInventory()];
		NBTTagList nbtlist = nbt.getTagList("Items");
		for(int i = 0; i < nbtlist.tagCount(); i++)
		{
			NBTTagCompound nbtslot = (NBTTagCompound) nbtlist.tagAt(i);
			int j = nbtslot.getByte("Slot") & 255;
			
			if((j >= 0) && (j < this.getSizeInventory()))
				this.inventory[j] = ItemStack.loadItemStackFromNBT(nbtslot);
		}
		
		this.burnTime = nbt.getInteger("BurnTime");
		this.cookTime = nbt.getInteger("CookTime");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("BurnTime", this.burnTime);
		nbt.setInteger("CookTime", this.cookTime);
		
		NBTTagList nbtlist = new NBTTagList();
		for(int i = 0; i < this.getSizeInventory(); i++)
		{
			if(this.inventory[i] != null)
			{
				NBTTagCompound nbtslot = new NBTTagCompound();
				nbtslot.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(nbtslot);
				nbtlist.appendTag(nbtslot);
			}
		}
		nbt.setTag("Items", nbtlist);
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		NBTTagCompound nbt = pkt.data;
		this.readFromNBT(nbt);
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, nbt);
	}
	
	 @SideOnly(Side.CLIENT)

	    /**
	     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	     * cooked
	     */
	    public int getCookProgressScaled(int par1)
	    {
	        return this.cookTime * par1 / 200;
	    }

	@Override
	public void onInventoryChanged()
	{
		super.onInventoryChanged();
		
		if(!this.worldObj.isRemote)
		{
			if(this.getStackInSlot(0) == null)
				this.cookTime = 150;
			/*
			 * if(this.cookTime == 0 && this.getStackInSlot(0) != null)
			 * this.cookTime = 150;
			 */
			CraftingPillars.proxy.sendToPlayers(this.getDescriptionPacket(), this.worldObj, this.xCoord, this.yCoord, this.zCoord, 64);
		}
	}
	
//	public void dropItemFromSlot(int slot, int amount, int side)
//	{
//		if(!this.worldObj.isRemote && this.getStackInSlot(slot) != null)
//		{
//			//TODO: drop out of clicked side
//			EntityItem droppedItem = new EntityItem(this.worldObj, this.xCoord + 0.5D, this.yCoord + (slot == 1 ? 1.2D : 1.5D), this.zCoord + 0.5D);
//			// int max = this.getStackInSlot(slot).stackSize;
//			droppedItem.setEntityItemStack(this.decrStackSize(slot, amount));
//			
//			droppedItem.motionX = random.nextDouble() / 4 - 0.125D;
//			droppedItem.motionZ = random.nextDouble() / 4 - 0.125D;
//			droppedItem.motionY = random.nextDouble() / 4;
//			droppedItem.setEntityItemStack(new ItemStack(droppedItem.getEntityItem().getItem(), amount));
//			this.worldObj.spawnEntityInWorld(droppedItem);
//			
//			if(slot == 2 && xp > 0)
//			{
//				System.out.println(xp);
//				EntityXPOrb xpEntity = new EntityXPOrb(this.worldObj, this.xCoord + 0.5D, this.yCoord + 1.5D, this.zCoord + 0.5D, xp);
//				this.worldObj.spawnEntityInWorld(xpEntity);
//			}
//		}
//	}
	
	public void dropItemFromSlot(int slot, int i)
	{
		if(this.getStackInSlot(slot) != null)
		{
			EntityItem droppedItem = new EntityItem(this.worldObj, this.xCoord + 0.5D, this.yCoord + 1.5D, this.zCoord + 0.5D);
			
			ItemStack decrStack = this.decrStackSize(slot, i);
			droppedItem.setEntityItemStack(decrStack);
			
			droppedItem.motionX = random.nextDouble() / 4 - 0.125D;
			droppedItem.motionZ = random.nextDouble() / 4 - 0.125D;
			droppedItem.motionY = random.nextDouble() / 4;
			
			if(!this.worldObj.isRemote)
				this.worldObj.spawnEntityInWorld(droppedItem);
		}
	}

	
	@Override
	public int getSizeInventory()
	{
		return 3;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.inventory[slot];
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		this.inventory[slot] = stack;
		
		if(stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}
		
		this.onInventoryChanged();
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = null;
		
		if(this.inventory[slot] != null)
		{
			if(this.inventory[slot].stackSize <= amount)
			{
				stack = this.inventory[slot];
				this.inventory[slot] = null;
				this.onInventoryChanged();
			}
			else
			{
				stack = this.inventory[slot].splitStack(amount);
				
				if(this.inventory[slot].stackSize == 0)
				{
					this.inventory[slot] = null;
				}
				
				this.onInventoryChanged();
			}
		}
		
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		if(stack != null)
		{
			this.setInventorySlotContents(slot, null);
		}
		
		return stack;
	}
	
	@Override
	public String getInvName()
	{
		return "Furnace Pillar";
	}
	
	@Override
	public boolean isInvNameLocalized()
	{
		return true;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public void openChest()
	{
	}
	
	@Override
	public void closeChest()
	{
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return new int[]{};
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
		return false;
	}
	
	public boolean canSmelt()
	{
		if(this.inventory[0] == null)
			return false;
		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);
		if(result == null)
			return false;
		if(this.inventory[2] != null)
		{
			if(!this.inventory[2].isItemEqual(result))
				return false;
			if(this.inventory[2].stackSize + result.stackSize >= result.getMaxStackSize())
				return false;
			if(this.inventory[2].stackSize + result.stackSize >= this.getInventoryStackLimit())
				return false;
		}
		if(result.stackSize >= this.getInventoryStackLimit())
			return false;
		return true;
	}
	
	
	public void smeltItem()
	{
		ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);
		this.xp += FurnaceRecipes.smelting().getExperience(this.inventory[0]);
		
		if(this.inventory[2] == null)
			this.inventory[2] = itemstack.copy();
		else if(this.inventory[2].isItemEqual(itemstack))
			inventory[2].stackSize += itemstack.stackSize;
		
		this.inventory[0].stackSize--;
		
		if(this.inventory[0].stackSize <= 0)
			this.inventory[0] = null;
		else if(this.inventory[2].stackSize + itemstack.stackSize <= this.getInventoryStackLimit() && this.inventory[2].stackSize + itemstack.stackSize <= itemstack.getMaxStackSize())
			this.cookTime = 150;
		
		this.onInventoryChanged();
	}
	
	public boolean canBurn()
	{
		if(this.inventory[1] == null)
			return false;
		if(TileEntityFurnace.getItemBurnTime(this.inventory[1]) <= 0)
			return false;
		return this.canSmelt();
	}
	
	public void burnItem()
	{
		if(this.cookTime == 0)
			this.cookTime = 150;
		this.burnTime = TileEntityFurnace.getItemBurnTime(this.inventory[1]);
		this.inventory[1].stackSize--;
		if(this.inventory[1].stackSize <= 0)
			this.inventory[1] = null;
		this.onInventoryChanged();
	}
}
