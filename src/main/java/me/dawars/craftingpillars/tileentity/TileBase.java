package me.dawars.craftingpillars.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;

import java.util.Random;
/**
 * Contains various functions from ThermalExpansion and Forestry.
 *
 */
public class TileBase extends TileEntity {
    private static final Random rand = new Random();

    private int tickCount = rand.nextInt(256);

    public void updateBlock() {
        markDirty();

        IBlockState state = worldObj.getBlockState(pos);
        worldObj.notifyBlockUpdate(getPos(), state, state, 3);
    }


    protected final boolean updateOnInterval(int tickInterval) {
        return tickCount % tickInterval == 0;
    }

    protected void updateLighting() {

        int light2 = worldObj.getLightFor(EnumSkyBlock.BLOCK, getPos()), light1 = getLightValue();
        if (light1 != light2 && worldObj.checkLightFor(EnumSkyBlock.BLOCK, getPos())) {
            IBlockState state = worldObj.getBlockState(getPos());
            worldObj.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    public int getLightValue() {

        return 0;
    }

    public void callBlockUpdate() {

        IBlockState state = worldObj.getBlockState(pos);
        worldObj.notifyBlockUpdate(pos, state, state, 3);
    }

    public void callNeighborStateChange() {

        worldObj.notifyNeighborsOfStateChange(pos, getBlockType());
    }

    public void callNeighborTileChange() {

        worldObj.updateComparatorOutputLevel(pos, getBlockType());
    }

    public void markChunkDirty() {

        worldObj.markChunkDirty(pos, this);
    }

    @Override
    public void onChunkUnload() {

        if (!tileEntityInvalid) {
            invalidate();
        }
    }

    /* Networking */
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

    public int getComparatorInputOverride() {
        return 0;
    }
}
