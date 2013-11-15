package me.dawars.CraftingPillars.tiles;

import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;

public class TileEntityDiskPlayerPillar extends BaseTileEntity
{
    /** ID of record which is in Jukebox */
    private ItemStack record;

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("RecordItem"))
        {
            this.setDisk(ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("RecordItem")));
        }
        else if (par1NBTTagCompound.getInteger("Record") > 0)
        {
            this.setDisk(new ItemStack(par1NBTTagCompound.getInteger("Record"), 1, 0));
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);

        if (this.getDisk() != null)
        {
            par1NBTTagCompound.setCompoundTag("RecordItem", this.getDisk().writeToNBT(new NBTTagCompound()));
            par1NBTTagCompound.setInteger("Record", this.getDisk().itemID);
        }
    }
    @Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		NBTTagCompound nbt = pkt.data;
		this.readFromNBT(nbt);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();

		if (!this.worldObj.isRemote)
			CraftingPillars.proxy.sendToPlayers(this.getDescriptionPacket(),
					this.worldObj, this.xCoord, this.yCoord, this.zCoord, 64);
	}
    public ItemStack getDisk()
    {
        return this.record;
    }

    public void setDisk(ItemStack item)
    {
        this.record = item;
        this.onInventoryChanged();
    }
}
