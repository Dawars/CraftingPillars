package me.dawars.craftingpillars.tileentity;

import me.dawars.craftingpillars.CraftingPillars;
import me.dawars.craftingpillars.client.render.Blobs;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.EnumSkyBlock;
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

    private int prevLightValue = 0;

    @Override
    public void update() {
        // TODO pump out on sides

        if (this.worldObj.isRemote) {
            // FIXME light state change
            int lightValue = getFluidLightLevel();
            if (prevLightValue != lightValue) {
                prevLightValue = lightValue;
                worldObj.setLightFor(EnumSkyBlock.BLOCK, pos, lightValue);
            }

            EntityPlayerSP player = FMLClientHandler.instance().getClient().thePlayer;
            if (pos.distanceSq(player.posX, player.posY, player.posZ) < 128) {
                while (this.blobs.size() < getTankFluidAmount() / Fluid.BUCKET_VOLUME)
                    this.addBlob();
                while (this.blobs.size() > getTankFluidAmount() / Fluid.BUCKET_VOLUME)
                    this.removeBlob();

                for (int i = 0; i < this.blobs.size(); i++)
                    this.blobs.get(i).update(0.1F);
            }
        }

        CraftingPillars.LOGGER.info(getTankFluidAmount());
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

    public boolean isEmpty() {
        return tank.getFluidAmount() <= 0;
    }

    public FluidTank getTank() {
        return tank;
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
