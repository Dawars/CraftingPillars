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
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileTank extends TilePillar implements ITickable {
    private Random random = new Random();

    FluidTank tank = new FluidTank(16 * Fluid.BUCKET_VOLUME);

    private List<Blobs> blobs = new ArrayList<>();

    private int compareTracker;

    @Override
    public void update() {
//        CraftingPillars.LOGGER.info(getTankFluidAmount());

        if (ServerHelper.isClientWorld(worldObj)) {

            EntityPlayerSP player = FMLClientHandler.instance().getClient().thePlayer;
            if (pos.distanceSq(player.posX, player.posY, player.posZ) < 128) {
                while (this.blobs.size() < getTankFluidAmount() / Fluid.BUCKET_VOLUME)
                    this.addBlob();
                while (this.blobs.size() > getTankFluidAmount() / Fluid.BUCKET_VOLUME)
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
                callNeighborTileChange();
            }

            updateLighting();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        tank.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag = super.writeToNBT(tag);
        tank.writeToNBT(tag);
        return tag;
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

        CraftingPillars.LOGGER.info("handleTilePacket "+fluidStack.amount);
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) tank;
        return super.getCapability(capability, facing);
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

    public int getFluidLightLevel() {
        if (isEmpty()) return 0;
        FluidStack tankFluid = tank.getFluid();
        return tankFluid == null ? 0 : tankFluid.getFluid().getLuminosity(tankFluid);
    }
}
