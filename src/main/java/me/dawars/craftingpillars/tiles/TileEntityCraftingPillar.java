package me.dawars.craftingpillars.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;

public class TileEntityCraftingPillar extends TileEntity implements ITickable, IInventory, ISidedInventory {
    private static final int RESULTSLOT = 9;

    private ItemStack[] inventory = new ItemStack[10];
    private float itemRot = 0, itemRotSpeed = 0.05f;
    private Container container = new Container() {
        @Override
        public boolean canInteractWith(EntityPlayer playerIn) {
            return true;
        }
    };
    private InventoryCrafting craftMatrix = new InventoryCrafting(container, 3, 3);

    public float getItemRotation(float partialTicks) {
        return itemRot + itemRotSpeed*partialTicks;
    }

    public ItemStack getResultStack() {
        return getStackInSlot(RESULTSLOT);
    }

    @Override
    public void update() {
        if (worldObj.isRemote) {
            itemRot += itemRotSpeed;
        }
    }

    public ItemStack craftItem() {
        return null;
    }

    public void playerCraftItem(EntityPlayer player) {

    }

    public void playerInsertItem(int slotIndex, ItemStack itemStack, boolean wholeStack) {
        if (inventory[slotIndex] == null || inventory[slotIndex].stackSize <= 0) {
            inventory[slotIndex] = itemStack.splitStack(wholeStack ? itemStack.stackSize : 1);
            onChanged();
        } else if (inventory[slotIndex].isItemEqual(itemStack)) {
            int amount = Math.min(wholeStack ? itemStack.stackSize : 1,
                    inventory[slotIndex].getMaxStackSize() - inventory[slotIndex].stackSize);
            inventory[slotIndex].stackSize += amount;
            itemStack.stackSize -= amount;
            onChanged();
        }
    }

    public void playerExtractItem(int slotIndex, float hitX, float hitZ, boolean wholeStack) {
        if (inventory[slotIndex] == null || inventory[slotIndex].stackSize <= 0) {
            return;
        }

        worldObj.spawnEntityInWorld(new EntityItem(worldObj,
                pos.getX() + hitX,
                pos.getY() + 1,
                pos.getZ() + hitZ,
                inventory[slotIndex].splitStack(wholeStack ? inventory[slotIndex].stackSize : 1)));
        if (inventory[slotIndex].stackSize <= 0) {
            inventory[slotIndex] = null;
        }
        onChanged();
    }

    private void onChanged() {
        for (int i = 0; i < 9; ++i) {
            craftMatrix.setInventorySlotContents(i, inventory[i]);
        }
        inventory[RESULTSLOT] = CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj);

        markDirty();
        IBlockState state = worldObj.getBlockState(pos);
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, serializeNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        deserializeNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] != null) {
                compound.setTag(String.format("slot%d", i), inventory[i].serializeNBT());
            }
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        for (int i = 0; i < inventory.length; ++i) {
            inventory[i] = ItemStack.loadItemStackFromNBT(compound.getCompoundTag(String.format("slot%d", i)));
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[] {RESULTSLOT};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory[index];
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        // TODO overview crafting
        ItemStack ret = index == RESULTSLOT
                ? craftItem()
                : inventory[index] == null ? null : inventory[index].splitStack(count);
        onChanged();
        return ret;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        // TODO overview crafting
        ItemStack ret = index == RESULTSLOT ? craftItem() : inventory[index];
        inventory[index] = null;
        onChanged();
        return ret;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        inventory[index] = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) { }

    @Override
    public void closeInventory(EntityPlayer player) { }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) { }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() { }

    @Override
    public String getName() {
        return "craftingpillar";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
