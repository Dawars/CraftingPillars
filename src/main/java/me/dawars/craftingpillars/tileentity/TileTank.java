package me.dawars.craftingpillars.tileentity;

import me.dawars.craftingpillars.CraftingPillars;
import me.dawars.craftingpillars.client.render.Blobs;
import me.dawars.craftingpillars.network.PacketCraftingPillar;
import me.dawars.craftingpillars.util.helpers.ServerHelper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileTank extends TilePillar implements ITickable {
    private Random random = new Random();

    FluidTank tank = new FluidTank(16 * Fluid.BUCKET_VOLUME);

    private List<Blobs> blobs = new ArrayList<>();

    private int compareTracker;
    private int lastDisplayLevel;


    private boolean renderFlag = true;
    private boolean cached = false;

    @Override
    public void update() {
        CraftingPillars.LOGGER.info(getTankFluidAmount());

        if (ServerHelper.isClientWorld(worldObj)) {

            EntityPlayerSP player = FMLClientHandler.instance().getClient().thePlayer;
            if (pos.distanceSq(player.posX, player.posY, player.posZ) < 128) {
                int numBlob = (getTankFluidAmount() + Fluid.BUCKET_VOLUME - 1) / Fluid.BUCKET_VOLUME;
                while (this.blobs.size() < numBlob)
                    this.addBlob();
                while (this.blobs.size() > numBlob)
                    this.removeBlob();

                for (int i = 0; i < this.blobs.size(); i++)
                    this.blobs.get(i).update(0.1F);
            }

            return;
        }

//        transferFluid(); // TODO maybe batch tanks in cubes/column?

        if (updateOnInterval(32)) {// FIXME change to constant
            int curScale = tank.getFluid() == null ? 0 : tank.getFluid().amount * 15 / tank.getCapacity();
            if (curScale != compareTracker) {
                compareTracker = curScale;
//                updateAdjacentHandlers(false);
                sendTilePacket(Side.CLIENT);
            }
            if (!cached) {
                updateLighting();
//                updateAdjacentHandlers(false);
                sendTilePacket(Side.CLIENT);
            }
        }
        if (renderFlag && updateOnInterval(4)) {
            updateRender();
        }
    }
/*
//  update connected tanks above and below
    protected void updateAdjacentHandlers(boolean packet) {

        if (ServerHelper.isClientWorld(worldObj)) {
            return;
        }
        boolean curAutoOutput = enableAutoOutput;

        adjacentTanks[0] = BlockHelper.getAdjacentTileEntity(this, EnumFacing.DOWN) instanceof TileTank;
        enableAutoOutput |= adjacentTanks[0];

        adjacentTanks[1] = BlockHelper.getAdjacentTileEntity(this, EnumFacing.UP) instanceof TileTank;

        if (packet && curAutoOutput != enableAutoOutput) {
            sendTilePacket(Side.CLIENT);
        }
        cached = true;
    }*/

    public void updateRender() {

        renderFlag = false;
        boolean sendUpdate = false;

        int curDisplayLevel = 0;
        int curLight = getLightValue();

        if (tank.getFluidAmount() > 0) {
            curDisplayLevel = blobs.size();
            if (curDisplayLevel == 0) {
                curDisplayLevel = 1;
            }
            if (lastDisplayLevel == 0) {
                lastDisplayLevel = curDisplayLevel;
                sendUpdate = true;
            }
        } else if (lastDisplayLevel != 0) {
            lastDisplayLevel = 0;
            sendUpdate = true;
        }
        if (curDisplayLevel != lastDisplayLevel) {
            lastDisplayLevel = curDisplayLevel;
            sendUpdate = true;
        }
        if (curLight != getLightValue()) {
            updateLighting();
            sendUpdate = true;
        }
        if (sendUpdate) {
            CraftingPillars.LOGGER.info("updateRender");
            sendTilePacket(Side.CLIENT);
        }
    }

    public int getLightValue() {
        return tank.getFluid() == null ? 0 : tank.getFluid().getFluid().getLuminosity();
    }

    @Override
    public void invalidate() {

        cached = false;
        super.invalidate();
    }

    /* NBT METHODS */
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        tank.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        tank.writeToNBT(nbt);
        return nbt;
    }

    /* NETWORK METHODS */

    /* SERVER -> CLIENT */
    @Override
    public PacketCraftingPillar getTilePacket() {

        PacketCraftingPillar payload = super.getTilePacket();
        CraftingPillars.LOGGER.info("getTilePacket");
        payload.addFluidStack(tank.getFluid());

        return payload;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleTilePacket(PacketCraftingPillar payload) {

        super.handleTilePacket(payload);

        FluidStack fluidStack = payload.getFluidStack();
        tank.setFluid(fluidStack);

        callBlockUpdate();
    }


    public boolean isEmpty() {
        return tank.getFluidAmount() <= 0;
    }

    public FluidTank getFluidTank() {
        return tank;
    }

    public FluidStack getTankFluid() {

        return tank.getFluid();
    }

    public int getTankCapacity() {

        return tank.getCapacity();
    }

    public int getTankFluidAmount() {

        return tank.getFluidAmount();
    }

    /* CAPABILITIES */
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, final EnumFacing from) {

        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new IFluidHandler() {

                @Override
                public IFluidTankProperties[] getTankProperties() {

                    FluidTankInfo info = tank.getInfo();
                    return new IFluidTankProperties[]{new FluidTankProperties(info.fluid, info.capacity, true, true)};
                }

                @Override
                public int fill(FluidStack resource, boolean doFill) {

                    if (resource == null || from == EnumFacing.DOWN) {
                        return 0;
                    }
                    renderFlag = true;
                    int amount = tank.fill(resource, doFill);

                    /*if (adjacentTanks[1] && from != EnumFacing.UP) {
                        if (amount != resource.amount) {
                            FluidStack remaining = resource.copy();
                            remaining.amount -= amount;
                            return amount + FluidHelper.insertFluidIntoAdjacentFluidHandler(worldObj, pos, EnumFacing.UP, remaining, doFill);
                        }
                    }*/
                    return amount;
                }

                @Nullable
                @Override
                public FluidStack drain(FluidStack resource, boolean doDrain) {
                    renderFlag = true;
                    return tank.drain(resource, doDrain);
                }

                @Nullable
                @Override
                public FluidStack drain(int maxDrain, boolean doDrain) {
                    renderFlag = true;
                    return tank.drain(maxDrain, doDrain);
                }
            });
        }
        return super.getCapability(capability, from);
    }



    /* Metaball rendering */
    @SideOnly(Side.CLIENT)
    public void addBlob() {
        this.blobs.add(new Blobs(this.random.nextInt(12) + 2.5F, this.random.nextInt(9) + 4.5F, this.random.nextInt(12) + 2.5F, 4));
    }

    @SideOnly(Side.CLIENT)
    public void removeBlob() {
        this.blobs.remove(random.nextInt(this.blobs.size()));
    }

    @SideOnly(Side.CLIENT)
    public List<Blobs> getBlobs() {
        return blobs;
    }

}
