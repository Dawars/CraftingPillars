package me.dawars.craftingpillars.tileentity;

import me.dawars.craftingpillars.inventory.FakeInventoryAdapter;
import me.dawars.craftingpillars.inventory.IInventoryAdapter;
import me.dawars.craftingpillars.inventory.InventorySmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TilePillar extends TileBase implements ISidedInventory {
    private static final String SHOW_NUM_KEY = "show_num";
    private boolean showNum;

    @Nonnull
    private IInventoryAdapter<TilePillar> inventory = FakeInventoryAdapter.instance();

    // Show the number of items/liquid in a container
    public boolean isShowNum() {
        return showNum;
    }

    public void setShowNum(boolean showNum) {
        this.showNum = showNum;
    }

    /* INVENTORY BASICS  from Forestry*/
    public IInventoryAdapter<TilePillar> getInternalInventory() {
        return inventory;
    }

    protected final void setInternalInventory(InventorySmelter inv) {
        if (inv == null) {
            throw new NullPointerException("Inventory cannot be null");
        }
        this.inventory = inv;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        compound.getBoolean(SHOW_NUM_KEY);
        inventory.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound nbtTagCompound = super.writeToNBT(compound);

        nbtTagCompound.setBoolean(SHOW_NUM_KEY, showNum);
        inventory.writeToNBT(compound);

        return nbtTagCompound;
    }

    public void toggleShow() {
        showNum = !showNum;
        markDirty();
        callBlockUpdate();
    }


    /* ISidedInventory */
    @Override
    public final int getSizeInventory() {
        return getInternalInventory().getSizeInventory();
    }

    @Override
    public final ItemStack getStackInSlot(int slotIndex) {
        return getInternalInventory().getStackInSlot(slotIndex);
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        return getInternalInventory().decrStackSize(slotIndex, amount);
    }

    @Override
    public ItemStack removeStackFromSlot(int slotIndex) {
        return getInternalInventory().removeStackFromSlot(slotIndex);
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemstack) {
        getInternalInventory().setInventorySlotContents(slotIndex, itemstack);
    }

    @Override
    public final int getInventoryStackLimit() {
        return getInternalInventory().getInventoryStackLimit();
    }

    @Override
    public final void openInventory(EntityPlayer player) {
        getInternalInventory().openInventory(player);
    }

    @Override
    public final void closeInventory(EntityPlayer player) {
        getInternalInventory().closeInventory(player);
    }

    /**
     * Gets the tile's unlocalized name, based on the block at the location of this entity (client-only).
     */
    @Override
    public String getName() {
        String blockUnlocalizedName = getBlockType().getUnlocalizedName();
        return blockUnlocalizedName + '.' + getBlockMetadata() + ".name";
    }

    @Override
    public final boolean isUseableByPlayer(EntityPlayer player) {
        return getInternalInventory().isUseableByPlayer(player);
    }

    @Override
    public boolean hasCustomName() {
        return getInternalInventory().hasCustomName();
    }

    @Override
    public final boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) {
        return getInternalInventory().isItemValidForSlot(slotIndex, itemStack);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return getInternalInventory().getSlotsForFace(side);
    }

    @Override
    public final boolean canInsertItem(int slotIndex, ItemStack itemStack, EnumFacing side) {
        return getInternalInventory().canInsertItem(slotIndex, itemStack, side);
    }

    @Override
    public final boolean canExtractItem(int slotIndex, ItemStack itemStack, EnumFacing side) {
        return getInternalInventory().canExtractItem(slotIndex, itemStack, side);
    }

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

    @Nonnull
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory != FakeInventoryAdapter.instance()) {
            if (facing != null) {
                SidedInvWrapper sidedInvWrapper = new SidedInvWrapper(inventory, facing);
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(sidedInvWrapper);
            } else {
                InvWrapper invWrapper = new InvWrapper(inventory);
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(invWrapper);
            }

        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory != FakeInventoryAdapter.instance()) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }
}
