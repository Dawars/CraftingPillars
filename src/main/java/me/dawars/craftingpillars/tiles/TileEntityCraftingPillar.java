package me.dawars.craftingpillars.tiles;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityCraftingPillar extends TileEntity implements ITickable {
    private ItemStack[][] inventory = new ItemStack[][] {
            {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY},
            {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY},
            {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY}};
    private float itemRot = 0;

    public ItemStack getItemStackAt(int i, int j) {
        return inventory[i][j];
    }

    public float getItemRotation() {
        return itemRot;
    }

    @Override
    public void update() {
        if (world.isRemote) {
            itemRot += 0.01f;
        }
    }

    public void sendDataToClients() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.println(i+""+j+" "+inventory[i][j]);
            }
        }

        world.getPlayers(EntityPlayerMP.class,
                (EntityPlayerMP player) -> player != null
                        && getDistanceSq(player.posX, player.posY, player.posZ) < getMaxRenderDistanceSquared())
                .forEach((EntityPlayerMP player) -> player.connection.sendPacket(getUpdatePacket()));
    }

    public boolean onPlayerRightClickOnTop(EntityPlayer player, float hitX, float hitZ) {
        if (world.isRemote) {
            return false;
        }

        int i = hitX > 0.33f ? (hitX > 0.66f ? 2 : 1) : 0;
        int j = hitZ > 0.33f ? (hitZ > 0.66f ? 2 : 1) : 0;

        ItemStack activeStack = player.getHeldItemMainhand();

        if (player.isSneaking()) {
            if (!inventory[i][j].isEmpty()) {
                world.spawnEntity(new EntityItem(world, pos.getX()+hitX, pos.getY()+1.1, pos.getZ()+hitZ, inventory[i][j]));
                inventory[i][j].setCount(0);
                sendDataToClients();
                return true;
            } else if (!activeStack.isEmpty()) {
                inventory[i][j] = activeStack.copy();
                activeStack.setCount(0);
                sendDataToClients();
                return true;
            }
        } else if (!activeStack.isEmpty()) {
            if (inventory[i][j].isEmpty()) {
                inventory[i][j] = activeStack.splitStack(1);
                sendDataToClients();
                return true;
            } else if (inventory[i][j].isItemEqual(activeStack)
                    && inventory[i][j].getCount() < inventory[i][j].getMaxStackSize()) {
                inventory[i][j].setCount(inventory[i][j].getCount()+1);
                activeStack.splitStack(1);
                sendDataToClients();
                return true;
            }
        }

        return false;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        System.out.println("getUpdatePacket");
        return new SPacketUpdateTileEntity(pos, 0, serializeNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        System.out.println("onDataPacket");
        deserializeNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                compound.setTag(String.format("inv%d%d", i, j), inventory[i][j].serializeNBT());
            }
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                inventory[i][j] = new ItemStack(compound.getCompoundTag(String.format("inv%d%d", i, j)));
            }
        }
    }
}
