package me.dawars.CraftingPillars.container;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.gui.BaseContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;

public class ContainerAdventCalendar extends BaseContainer implements IInventory{

    private static ItemStack[] inventory = new ItemStack[24];
    private EntityPlayer player;
	public ContainerAdventCalendar(InventoryPlayer player_inventory, EntityPlayer player)
	{
        super(inventory.length);
        this.player = player;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) 
	{
		return this.player == entityplayer;
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.inventory[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventory[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = null;

		if (this.inventory[slot] != null) {
			if (this.inventory[slot].stackSize <= amount) {
				stack = this.inventory[slot];
				this.inventory[slot] = null;
				this.onInventoryChanged();
			} else {
				stack = this.inventory[slot].splitStack(amount);

				if (this.inventory[slot].stackSize == 0) {
					this.inventory[slot] = null;
				}

				this.onInventoryChanged();
			}
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			this.setInventorySlotContents(slot, null);
		}

		return stack;
	}

	@Override
	public String getInvName() {
		return CraftingPillars.itemCalendar.getUnlocalizedName() + ".name";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

//	@Override
//	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
//		NBTTagCompound nbt = pkt.data;
//		this.readFromNBT(nbt);
//	}
//
//	@Override
//	public Packet getDescriptionPacket() {
//		NBTTagCompound nbt = new NBTTagCompound();
//		this.writeToNBT(nbt);
//		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, nbt);
//	}

//	@Override
//	public void onInventoryChanged()
//	{
//		if (!this.player.worldObj.isRemote)
//			CraftingPillars.proxy.sendToPlayer(this.player, this.getDescriptionPacket());
//	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.player == entityplayer;
	}

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public void onInventoryChanged() {
		
	}

}
