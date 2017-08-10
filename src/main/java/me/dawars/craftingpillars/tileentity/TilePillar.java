package me.dawars.craftingpillars.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public class TilePillar extends TileBase {
    private static final String SHOW_NUM_KEY = "show_num";
    private boolean showNum;

    public boolean isShowNum() {
        return showNum;
    }

    public void setShowNum(boolean showNum) {
        this.showNum = showNum;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        compound.getBoolean(SHOW_NUM_KEY);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound nbtTagCompound = super.writeToNBT(compound);
        nbtTagCompound.setBoolean(SHOW_NUM_KEY, showNum);
        return nbtTagCompound;
    }

    public void toggleShow() {
        showNum = !showNum;
        updateBlock();
    }
}
