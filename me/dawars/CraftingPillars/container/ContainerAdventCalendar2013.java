package me.dawars.CraftingPillars.container;

import java.util.Calendar;
import java.util.Random;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.gui.BaseContainer;
import me.dawars.CraftingPillars.properties.CalendarPlayerProps2013;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
	private static final ItemStack[] adventItems = new ItemStack[]{
		new ItemStack(CraftingPillars.itemElysiumLoreBook, 1, 0),
		new ItemStack(CraftingPillars.blockPotPillar, 1, 0),
		new ItemStack(CraftingPillars.blockChristmasTreeSapling, 1, 0),
		new ItemStack(CraftingPillars.itemWinterFood2013, 1, 2),
		new ItemStack(Item.snowball, 64, 0),//spawns more
		new ItemStack(CraftingPillars.itemVirgacs, 1, 0),
		new ItemStack(CraftingPillars.blockBasePillar, 5, 0),
		new ItemStack(CraftingPillars.itemWinterFood2013, 3, 3),
		new ItemStack(CraftingPillars.blockFurnacePillar, 1, 0),
		new ItemStack(Item.coal, 8, 0),
		new ItemStack(CraftingPillars.itemWinterFood2013, 23, 0),
		new ItemStack(CraftingPillars.blockBrewingPillar, 1, 0),
		new ItemStack(Block.ice, 7, 0),
		new ItemStack(CraftingPillars.itemWinterFood2013, 6, 1),
		new ItemStack(CraftingPillars.blockFreezerPillar, 1, 0),
		new ItemStack(CraftingPillars.itemWinterFood2013, 3, 0),
		new ItemStack(Item.appleRed, 14, 0),
		new ItemStack(CraftingPillars.blockDiskPlayerPillar, 1, 0),
		new ItemStack(CraftingPillars.itemWinterFood2013, 7, 4),
		new ItemStack(CraftingPillars.itemDiscElysium, 1, 0),
		new ItemStack(CraftingPillars.itemRibbonDiamond, 21),
		new ItemStack(CraftingPillars.itemWinterFood2013, 22, 7),
		new ItemStack(Block.dirt.blockID, 1, 0),//TODO: Star
		new ItemStack(Block.dirt.blockID, 1, 0)//TODO: coming soon text
	};
	
	public static final String[] tooltips = new String[]{
		"A book about Elysium",
		"A pillar for growing Christmas Trees",
		"A sapling just for you",
		"Hmm, sweet!",
		"Let's trow these at your friends!",
		"You were a bad boy, #name#!",
		"Connect your pillars!",
		"Hmm, tastes good!",
		"Let's cook your porkchops!",
		"Oh, you need fuel!?",
		"todo",
		"What kind of sorcery is this!? :O",
		"What!? Why is this here?",
		"todo",
		"Make more ice!",
		"todo",
		"One apple once a day, keeps the doctor away!",
		"Listen to the music!",
		"todo",
		"Do you hear this awesome music?",
		"todo",
		"todo",
		"Put it on the top of your tree!",
		"Comming Soon!"
	};
	
	public ContainerAdventCalendar2013(InventoryPlayer inventoryPlayer, EntityPlayer player)
	{
		super(24);
		this.player = player;
		this.inventory = new ItemStack[24];
		this.player = player;
		
		tooltips[5] = "You were a bad boy, "+player.username+"!";
		
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
