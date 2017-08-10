package me.dawars.craftingpillars.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;

public class TileBase extends TileEntity {

    protected void updateBlock() {
        markDirty();

        IBlockState state = worldObj.getBlockState(pos);
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
    }
}
