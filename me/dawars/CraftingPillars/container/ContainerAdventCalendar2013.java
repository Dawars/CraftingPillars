package me.dawars.CraftingPillars.container;

import java.util.Calendar;
import java.util.Random;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.gui.BaseContainer;
import me.dawars.CraftingPillars.items.WinterFood2013;
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
	
	public static ItemStack getStackForDrop(int slot)
	{
		if(adventItems[slot].itemID == CraftingPillars.itemWinterFood2013.itemID){
			if(adventItems[slot].getItemDamage() == 4 || adventItems[slot].getItemDamage() == 5)
				return new ItemStack(adventItems[slot].itemID, CraftingPillars.rand.nextInt(5) + 10, CraftingPillars.rand.nextInt(2) + 4);

			if(adventItems[slot].getItemDamage() == 6 || adventItems[slot].getItemDamage() == 7 || adventItems[slot].getItemDamage() == 8)
				return new ItemStack(adventItems[slot].itemID, CraftingPillars.rand.nextInt(5) + 10, CraftingPillars.rand.nextInt(3) + 6);
			
			
			return new ItemStack(adventItems[slot].itemID, adventItems[slot].stackSize, adventItems[slot].getItemDamage());
		}
		return adventItems[slot];
	}
	
	public static ItemStack[] adventItems;
	public static String[] tooltips;
	
	public static void init()
	{
		adventItems = new ItemStack[]{
			new ItemStack(CraftingPillars.itemElysiumLoreBook, 1, 0),
			new ItemStack(CraftingPillars.blockPotPillar, 1, 0),
			new ItemStack(CraftingPillars.blockChristmasTreeSapling, 1, 0),
			new ItemStack(CraftingPillars.itemWinterFood2013, 1, 2),
			new ItemStack(Item.snowball, 64, 0),
			new ItemStack(CraftingPillars.itemVirgacs, 1, 0),
			new ItemStack(CraftingPillars.blockBasePillar, 5, 0),
			new ItemStack(CraftingPillars.itemWinterFood2013, 3, 3),
			new ItemStack(CraftingPillars.blockFurnacePillar, 1, 0),
			new ItemStack(Item.coal, 8, 0),
			new ItemStack(CraftingPillars.itemWinterFood2013, 10, 0),
			new ItemStack(CraftingPillars.blockBrewingPillar, 1, 0),
			new ItemStack(Block.ice, 7, 0),
			new ItemStack(CraftingPillars.itemWinterFood2013, 6, 1),
			new ItemStack(CraftingPillars.blockFreezerPillar, 1, 0),
			new ItemStack(CraftingPillars.itemWinterFood2013, 3, 0),
			new ItemStack(Item.appleRed, 14, 0),
			new ItemStack(CraftingPillars.blockDiskPlayerPillar, 1, 0),
			new ItemStack(CraftingPillars.itemWinterFood2013, 7, 4),
			new ItemStack(CraftingPillars.itemDiscElysium, 1, 0),
			new ItemStack(CraftingPillars.itemRibbonDiamond, 3),
			new ItemStack(CraftingPillars.itemWinterFood2013, 10, 7),
			new ItemStack(CraftingPillars.blockChristmasLight, 7, 0),
			new ItemStack(Block.dirt.blockID, 1, 0)//TODO: coming soon text
		};
		
		tooltips = new String[]{
			"The story of Elysium and more",
			"A pot for everything",
			"Grows over time in a pillar",
			"Hmm, sweet!",
			"Let's throw these at eachother!",
			"You were a bad boy, #name#!",
			"Connect your pillars!",
			"Hmm, tastes good!",
			"Let's cook porkchops!",
			"Oh, you need fuel!?",
			"Never enough of these",
			"What kind of sorcery is this!? :O",
			"\"What!? Why is this here?\" - FBalazs",
			"\"Let's just go to sleep instead!\" - Dawars",
			"Make more ice from water!",
			"Recursion lvl 99",
			"One apple a day, keeps the doctor away!",
			"Listen to music!",
			"Granny made you some cookies, #name#!",
			"Do you hear this awesome music?",
			"Just take it!",
			"Szaloncukor for everyone!",
			"Put it everywhere!",
			"Coming Soon - Please update the mod!"
		};
	}
	
	public ContainerAdventCalendar2013(InventoryPlayer inventoryPlayer, EntityPlayer player)
	{
		super(24);
		this.player = player;
		this.inventory = new ItemStack[24];
		this.player = player;
		
		for(int i = 0; i < tooltips.length; i++)
			tooltips[i] = tooltips[i].replaceAll("#name#", player.username);
		
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
