package me.dawars.craftingpillars.inventory;

import me.dawars.craftingpillars.tileentity.TileShowcase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class InventoryShowcase extends InventoryAdapterTile<TileShowcase> {
    public InventoryShowcase(TileShowcase tile, int size, String name) {
        super(tile, size, name);
    }

    @Override
    public boolean canExtractItem(int slotIndex, ItemStack stack, EnumFacing side) {
        ItemStack stackInSlot = getStackInSlot(slotIndex);
        return stackInSlot != null && stackInSlot.stackSize >= stack.stackSize;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {
        ItemStack stackInSlot = getStackInSlot(slot);
        return stackInSlot != null && stackInSlot.stackSize + stack.stackSize <= getInventoryStackLimit();
    }
}
