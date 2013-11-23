package me.dawars.CraftingPillars.container;

import java.util.Calendar;
import java.util.Random;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.gui.BaseContainer;
import me.dawars.CraftingPillars.properties.CalendarPlayerProps;
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

public class ContainerAdventCalendar extends BaseContainer implements IInventory
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
	public boolean[] discovered;
	
	// TODO items
	private static ItemStack[] adventItems = new ItemStack[]{new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockAnvilPillar.blockID, 1, 0),
															new ItemStack(Block.coalBlock.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0),
															new ItemStack(CraftingPillars.blockBrewingPillar.blockID, 1, 0)};
	
	public ContainerAdventCalendar(InventoryPlayer inventoryPlayer, EntityPlayer player)
	{
		super(CraftingPillars.getNumberOfCalendarElements());
		this.player = player;
		this.inventory = new ItemStack[CraftingPillars.getNumberOfCalendarElements()];
		if(CalendarPlayerProps.get(this.player) != null)
		{
			if(this.player.worldObj.isRemote)
				System.out.println("Client props");
			else
				System.out.println("Server props");
			this.discovered = CalendarPlayerProps.get(this.player).discovered;
		}
		else
			this.discovered = new boolean[24];
		this.player = player;
		
		for(int i = 0; i < this.inventory.length; i++)
		{
			this.addSlotToContainer(new AdventSlot(this, i, (i%4)*44 + 54, (i/4)*30 + 53));
			if(CalendarPlayerProps.get(this.player).discovered[i] || i == this.inventory.length-1)
				this.setInventorySlotContents(i, adventItems[i]);
			else
				this.setInventorySlotContents(i, new ItemStack(Item.goldNugget.itemID, 1, 0)); // TODO chocolate
		}
	}
	
	public void setDiscovered(int slot)
	{
		this.discovered[slot] = true;
		CalendarPlayerProps.get(this.player).discovered[slot] = true;
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
		return "Advent Calendar";
//		return CraftingPillars.itemCalendar.getUnlocalizedName() + ".name";
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
