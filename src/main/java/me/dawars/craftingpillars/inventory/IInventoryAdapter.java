package me.dawars.craftingpillars.inventory;

import me.dawars.craftingpillars.tileentity.TilePillar;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;

public interface IInventoryAdapter<T extends TilePillar> extends ISidedInventory, INbtWritable {
    void readFromNBT(NBTTagCompound nbt);
}
