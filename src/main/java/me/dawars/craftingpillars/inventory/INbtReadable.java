package me.dawars.craftingpillars.inventory;

import net.minecraft.nbt.NBTTagCompound;

public interface INbtReadable {
    void readFromNBT(NBTTagCompound nbt);
}
