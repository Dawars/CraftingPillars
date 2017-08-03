package me.dawars.craftingpillars.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityCraftingPillar extends TileEntity implements ITickable {
    private ItemStack[][] inventory = new ItemStack[3][3];
    private float itemRot = 0;

    public float getItemRotation() {
        return itemRot;
    }

    @Override
    public void update() {
        if (world.isRemote) {
            itemRot += 0.01f;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }
}
