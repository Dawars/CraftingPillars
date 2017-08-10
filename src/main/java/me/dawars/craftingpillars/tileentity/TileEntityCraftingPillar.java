package me.dawars.craftingpillars.tileentity;

import me.dawars.craftingpillars.CraftingPillars;
import me.dawars.craftingpillars.blocks.BlockCraftingPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3i;

public class TileEntityCraftingPillar extends TileEntity implements ITickable {
    private float itemRot = 0, itemRotSpeed = 0.05f;
    private Container container = new Container() {
        @Override
        public boolean canInteractWith(EntityPlayer playerIn) {
            return true;
        }
    };
    private InventoryCrafting craftMatrix = new InventoryCrafting(container, 3, 3);
    private ItemStack resultStack = null;

    public float getItemRotation(float partialTicks) {
        return itemRot + itemRotSpeed * partialTicks;
    }

    public ItemStack getResultStack() {
        return resultStack;
    }

    public ItemStack getStackInSlot(int i) {
        return craftMatrix.getStackInSlot(i);
    }

    //012
    //345
    //678

    public float getSlotX(int i) {
        EnumFacing facing = worldObj.getBlockState(pos).getValue(BlockCraftingPillar.FACING);
        Vec3i vecInvZ = facing.getDirectionVec();
        Vec3i vecX = vecInvZ.crossProduct(new Vec3i(0, -1, 0));
        return 0.5f
                - vecInvZ.getX() * 5.f / 16f * (i / 3 - 1)
                - vecX.getX() * 5.f / 16f * (i % 3 - 1);
    }

    public float getSlotZ(int i) {
        EnumFacing facing = worldObj.getBlockState(pos).getValue(BlockCraftingPillar.FACING);
        Vec3i vecInvZ = facing.getDirectionVec();
        Vec3i vecX = vecInvZ.crossProduct(new Vec3i(0, -1, 0));
        return 0.5f
                - vecInvZ.getZ() * 5.f / 16f * (i / 3 - 1)
                - vecX.getZ() * 5.f / 16f * (i % 3 - 1);
    }

    public int getSlotIndex(float x, float z) {
        return 0;
    }

    @Override
    public void update() {
        if (worldObj.isRemote) {
            itemRot += itemRotSpeed;
        }
    }

    public void dropItems() {
        for (int i = 0; i < craftMatrix.getSizeInventory(); ++i) {
            ItemStack stack = craftMatrix.removeStackFromSlot(i);
            if (stack != null) {
                worldObj.spawnEntityInWorld(new EntityItem(worldObj,
                        pos.getX() + 0.5,
                        pos.getY() + 1,
                        pos.getZ() + 0.5,
                        stack));
            }
        }
    }

    public ItemStack craftItem() {
        if (resultStack == null) {
            return null;
        }

        ItemStack[] remaining = CraftingManager.getInstance().getRemainingItems(craftMatrix, worldObj);

        for (int i = 0; i < remaining.length; ++i) {
            ItemStack current = craftMatrix.getStackInSlot(i);

            if (current != null) {
                --current.stackSize;
                if (current.stackSize <= 0) {
                    current = null;
                }
            }

            if (remaining[i] != null) {
                if (current == null) {
                    current = remaining[i];
                } else if (ItemStack.areItemsEqual(current, remaining[i])
                        && ItemStack.areItemStackTagsEqual(current, remaining[i])) {
                    current.stackSize += remaining[i].stackSize;
                } else {
                    worldObj.spawnEntityInWorld(new EntityItem(worldObj,
                            pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                            remaining[i]));
                }
            }

            craftMatrix.setInventorySlotContents(i, current);
        }

        ItemStack ret = resultStack;
        onChanged();
        return ret;
    }

    public void playerCraftItem(EntityPlayer player) {
        ItemStack stack = craftItem();
        if (stack != null) {
            // TODO onCrafting(player, stack)
            worldObj.spawnEntityInWorld(new EntityItem(worldObj,
                    pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5,
                    stack));
        }
    }

    public void playerInsertItem(int slotIndex, ItemStack itemStack, boolean wholeStack) {
        ItemStack current = craftMatrix.getStackInSlot(slotIndex);

        if (current == null) { // if the slot is empty
            craftMatrix.setInventorySlotContents(slotIndex,
                    wholeStack ? itemStack.copy() : itemStack.splitStack(1));
            onChanged();
        } else if (ItemStack.areItemsEqual(current, itemStack)
                && ItemStack.areItemStackTagsEqual(current, itemStack)) {
            // if the slot has the same item as the player in his hand
            int amount = Math.min(wholeStack ? itemStack.stackSize : 1,
                    current.getMaxStackSize() - current.stackSize);
            if (amount > 0) {
                current.stackSize += amount;
                itemStack.stackSize -= amount;
                onChanged();
            }
        }
    }

    public void playerExtractItem(int slotIndex, float hitX, float hitZ, boolean wholeStack) {
        if (craftMatrix.getStackInSlot(slotIndex) != null) {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj,
                    pos.getX() + hitX,
                    pos.getY() + 1,
                    pos.getZ() + hitZ,
                    wholeStack ? craftMatrix.removeStackFromSlot(slotIndex)
                            : craftMatrix.decrStackSize(slotIndex, 1)));
            onChanged();
        }
    }

    private void onChanged() {
        // refreshing the crafting result
        resultStack = CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj);

        for (int i = 0; i < craftMatrix.getSizeInventory(); ++i) {
            CraftingPillars.LOGGER.info("slot" + i + " = " + craftMatrix.getStackInSlot(i));
        }
        CraftingPillars.LOGGER.info("result = " + resultStack);

        // notifying the world about the change
        markDirty();
        IBlockState state = worldObj.getBlockState(pos);
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(super.getUpdateTag());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        deserializeNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for (int i = 0; i < craftMatrix.getSizeInventory(); ++i) {
            ItemStack stack = craftMatrix.getStackInSlot(i);
            if (stack != null) {
                compound.setTag("slot" + i, stack.serializeNBT());
            }
        }
        if (resultStack != null) {
            compound.setTag("result", resultStack.serializeNBT());
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        for (int i = 0; i < craftMatrix.getSizeInventory(); ++i) {
            craftMatrix.setInventorySlotContents(i,
                    ItemStack.loadItemStackFromNBT(compound.getCompoundTag("slot" + i)));
        }
        resultStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("result"));
    }
}
