package me.dawars.craftingpillars.tiles;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import java.util.Arrays;

public class TileEntityCraftingPillar extends TileEntity implements ITickable, IInventory, ISidedInventory {
    private static final int RESULTSLOT = 9;

    private ItemStack[] inventory = new ItemStack[10];
    private float itemRot = 0;
    private Container container = new Container() {
        @Override
        public boolean canInteractWith(EntityPlayer playerIn) {
            return true;
        }
    };
    private InventoryCrafting craftMatrix = new InventoryCrafting(container, 3, 3);

    public float getItemRotation() {
        return itemRot;
    }

    public TileEntityCraftingPillar() {
        Arrays.fill(inventory, ItemStack.EMPTY);
    }

    @Override
    public void update() {
        if (world.isRemote) {
            itemRot += 0.01f;
        }
    }

    public void onChanged() {
        // TODO craft result
        for (int i = 0; i < 9; ++i) {
            craftMatrix.setInventorySlotContents(i, inventory[i]);
        }
        inventory[RESULTSLOT] = CraftingManager.findMatchingResult(craftMatrix, world);

        world.getPlayers(EntityPlayerMP.class,
                (EntityPlayerMP player) -> player != null
                        && getDistanceSq(player.posX, player.posY, player.posZ) < getMaxRenderDistanceSquared())
                .forEach((EntityPlayerMP player) -> player.connection.sendPacket(getUpdatePacket()));
    }

    public void onPlayerLeftClickOnBlock(EntityPlayer player) {
        if (world.isRemote) {
            return;
        }


    }

    public boolean onPlayerRightClickOnTop(EntityPlayer player, float hitX, float hitZ) {
        if (world.isRemote) {
            return false;
        }

        int i = hitX > 0.33f ? (hitX > 0.66f ? 2 : 1) : 0;
        i += hitZ > 0.33f ? (hitZ > 0.66f ? 6 : 3) : 0;

        ItemStack activeStack = player.getHeldItemMainhand();

        if (!activeStack.isEmpty() && (inventory[i].isEmpty() || inventory[i].isItemEqual(activeStack))) {
            int amount = Math.min(player.isSneaking() ? activeStack.getCount() : 1,
                    inventory[i].getMaxStackSize() - inventory[i].getCount());

            if (amount > 0) {
                ItemStack split = activeStack.splitStack(amount);
                split.grow(inventory[i].getCount());
                inventory[i] = split;
                onChanged();
            }
        } else if (activeStack.isEmpty() && !inventory[i].isEmpty()) {
            int amount = player.isSneaking() ? inventory[i].getCount() : 1;

            world.spawnEntity(new EntityItem(world, pos.getX() + hitX, pos.getY() + 1, pos.getZ() + hitZ,
                    inventory[i].splitStack(amount)));
            onChanged();
        }

        return true;
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
            compound.setTag(String.format("inv%d", i), inventory[i].serializeNBT());
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        for (int i = 0; i < inventory.length; ++i) {
            inventory[i] = new ItemStack(compound.getCompoundTag(String.format("inv%d", i)));
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
        return index == RESULTSLOT && stack.isItemEqual(inventory[RESULTSLOT]) && stack.getCount() > inventory[RESULTSLOT].getCount();
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < inventory.length; ++i) {
            if (!inventory[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        // TODO onChanged
        if (index == RESULTSLOT) {
            // TODO craft or not to craft?
            return inventory[RESULTSLOT].copy();
        }
        return inventory[index].splitStack(count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        // TODO onChanged
        if (index == RESULTSLOT) {
            // TODO craft or not to craft?
        }
        return inventory[index].splitStack(inventory[index].getCount());
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory[index] = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        // TODO
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {}

    @Override
    public String getName() {
        return "craftingpillar";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
