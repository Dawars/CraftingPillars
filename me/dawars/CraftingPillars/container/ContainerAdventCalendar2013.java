package me.dawars.CraftingPillars.container;

import java.util.Calendar;
import java.util.Random;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.gui.BaseContainer;
import me.dawars.CraftingPillars.properties.CalendarPlayerProps2013;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;

public class ContainerAdventCalendar2013 extends BaseContainer implements IInventory
{
	static class AdventSlot extends Slot
	{
		public AdventSlot(IInventory inventory, int i, int x, int y)
		{
			super(inventory, i, x, y);
		}
		
		@Override
		public boolean canTakeStack(EntityPlayer player)
		{
			return false;
		}
	}
	
	private ItemStack[] inventory;
	private EntityPlayer player;
	
	// TODO items
	private static ItemStack[] adventItems = new ItemStack[]{
		new ItemStack(CraftingPillars.itemElysiumLoreBook.itemID, 1, 0),
		new ItemStack(CraftingPillars.blockChristmasTreeSapling.blockID, 1, 0),
		new ItemStack(Item.cookie, 16, 0),
		new ItemStack(CraftingPillars.blockFurnacePillar.blockID, 1, 0),
		new ItemStack(CraftingPillars.blockBasePillar.blockID, 1, 0),
		new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
		new ItemStack(Block.ice.blockID, 1, 0),
		new ItemStack(CraftingPillars.itemWinterFood2013.itemID, 1, 3),
		new ItemStack(CraftingPillars.blockAnvilPillar.blockID, 1, 0),
		new ItemStack(CraftingPillars.itemWinterFood2013.itemID, 1, 0),
		new ItemStack(Item.coal.itemID, 8, 0),
		new ItemStack(CraftingPillars.blockDiskPlayerPillar.blockID, 1, 0),
		new ItemStack(Item.snowball.itemID, 32, 0),
		new ItemStack(Item.appleRed.itemID, 1, 0),
		new ItemStack(Block.dirt.blockID, 1, 0),//TODO
		new ItemStack(CraftingPillars.itemWinterFood2013.itemID, 1, 0),
		new ItemStack(Block.dirt.blockID, 1, 0),//TODO
		new ItemStack(CraftingPillars.blockFreezerPillar.blockID, 1, 0),
		new ItemStack(Block.dirt.blockID, 1, 0),//TODO
		new ItemStack(Block.dirt.blockID, 1, 0),//TODO
		new ItemStack(CraftingPillars.itemDiscElysium.itemID, 1, 0),
		new ItemStack(Block.dirt.blockID, 1, 0),//TODO
		new ItemStack(Block.dirt.blockID, 1, 0),//TODO
		new ItemStack(Block.dirt.blockID, 1, 0)
	};
	
	public ContainerAdventCalendar2013(InventoryPlayer inventoryPlayer, EntityPlayer player)
	{
		super(24);
		this.player = player;
		this.inventory = new ItemStack[24];
		this.player = player;
		
		for(int i = 0; i < this.inventory.length; i++)
		{
			this.addSlotToContainer(new AdventSlot(this, i, (i%4)*44 + 54, (i/4)*30 + 53));
			this.setInventorySlotContents(i, adventItems[i]);
		}
	}
	
	public void setDiscovered(int slot)
	{
		CalendarPlayerProps2013.get(this.player).setDiscovered(slot);
	}
	
	public boolean isDiscovered(int slot)
	{
		return CalendarPlayerProps2013.get(this.player).discovered[slot];
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return this.player == entityplayer;
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.inventory.length;
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
		return CraftingPillars.itemCalendar2013.getUnlocalizedName() + ".name";
	}
	
	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return this.player == entityplayer;
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
		return false;
	}
	
	@Override
	public void onInventoryChanged()
	{
		
	}
}
