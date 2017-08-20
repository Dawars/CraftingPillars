package me.dawars.craftingpillars.inventory;

import me.dawars.craftingpillars.tileentity.TilePillar;
import net.minecraft.inventory.ISidedInventory;

public interface IInventoryAdapter<T extends TilePillar> extends ISidedInventory, INbtWritable, INbtReadable {
}
