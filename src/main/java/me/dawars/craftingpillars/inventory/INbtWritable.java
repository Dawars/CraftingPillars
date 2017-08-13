/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package me.dawars.craftingpillars.inventory;

import net.minecraft.nbt.NBTTagCompound;

public interface INbtWritable {
    NBTTagCompound writeToNBT(NBTTagCompound nbt);
}
