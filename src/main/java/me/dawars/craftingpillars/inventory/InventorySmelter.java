package me.dawars.craftingpillars.inventory;

import me.dawars.craftingpillars.tileentity.TileSmelter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;

public class InventorySmelter extends InventoryAdapterTile<TileSmelter> {

    public static final int SLOT_RAW = 0;
    public static final int SLOT_FUEL = 1;
    public static final int SLOT_COOKED = 2;

    public InventorySmelter(TileSmelter tile, int size, String name) {
        super(tile, size, name);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[]{SLOT_RAW, SLOT_FUEL, SLOT_COOKED};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {
        // FIXME add inv helper functions
        if (slot == SLOT_FUEL) {
            return TileEntityFurnace.isItemFuel(stack) /* && InventoryHelper.canInsertItem(stack, inventory, slot) */;
        }
        if (slot == SLOT_RAW) {
            return FurnaceRecipes.instance().getSmeltingResult(stack) != null /* && InventoryHelper.canInsertItem(stack, inventory, slot) */;
        }
        return super.canInsertItem(slot, stack, side);
    }

    @Override
    public boolean canExtractItem(int slotIndex, ItemStack stack, EnumFacing side) {
        return slotIndex == SLOT_COOKED;
    }

    @Override
    public ItemStack decrStackSize(int slotId, int count) {
        return super.decrStackSize(slotId, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int slotIndex) {
        return super.removeStackFromSlot(slotIndex);
    }

    @Override
    public void setInventorySlotContents(int slotId, ItemStack itemstack) {
        super.setInventorySlotContents(slotId, itemstack);
    }
}
