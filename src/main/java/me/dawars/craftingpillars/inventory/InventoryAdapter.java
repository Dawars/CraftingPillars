/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package me.dawars.craftingpillars.inventory;

import me.dawars.craftingpillars.tileentity.TilePillar;
import me.dawars.craftingpillars.util.Constants;
import me.dawars.craftingpillars.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;

/**
 * With permission from Krapht.
 */
public class InventoryAdapter implements IInventoryAdapter<TilePillar> {

    private final IInventory inventory;

    //private boolean debug = false;

    public InventoryAdapter(int size, String name) {
        this(size, name, 64);
    }

    public InventoryAdapter(int size, String name, int stackLimit) {
        this(new InventoryPlain(size, name, stackLimit));
    }

    public InventoryAdapter(IInventory inventory) {
        this.inventory = inventory;
    }

    //	public InventoryAdapter enableDebug() {
    //		this.debug = true;
    //		return this;
    //	}

    /**
     * @return Copy of this inventory. Stacks are copies.
     */
    public InventoryAdapter copy() {
        InventoryAdapter copy = new InventoryAdapter(inventory.getSizeInventory(), inventory.getName(), inventory.getInventoryStackLimit());

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            if (inventory.getStackInSlot(i) != null) {
                copy.setInventorySlotContents(i, inventory.getStackInSlot(i).copy());
            }
        }

        return copy;
    }


    /* IINVENTORY */
    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slotId) {
        return inventory.getStackInSlot(slotId);
    }

    @Override
    public ItemStack decrStackSize(int slotId, int count) {
        markDirty();
        return inventory.decrStackSize(slotId, count);
    }

    @Override
    public void setInventorySlotContents(int slotId, ItemStack itemstack) {
        markDirty();
        inventory.setInventorySlotContents(slotId, itemstack);
    }

    @Override
    public String getName() {
        return inventory.getName();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public void markDirty() {
        inventory.markDirty();
    }

    @Override
    public ItemStack removeStackFromSlot(int slotIndex) {
        markDirty();
        return inventory.removeStackFromSlot(slotIndex);
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return Constants.SLOTS_NONE;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {
        return isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side) {
        return false;
    }

    /* SAVING & LOADING */
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        InventoryUtil.readFromNBT(this, nbttagcompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        InventoryUtil.writeToNBT(this, nbttagcompound);
        return nbttagcompound;
    }


    /* FIELDS */
    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public void clear() {
    }
}
