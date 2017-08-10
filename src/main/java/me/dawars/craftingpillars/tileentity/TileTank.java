package me.dawars.craftingpillars.tileentity;

import me.dawars.craftingpillars.CraftingPillars;
import me.dawars.craftingpillars.client.render.Blobs;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.client.FMLClientHandler;

import javax.annotation.Nullable;
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
/* // FIXME light in base class
                int lightValue = getFluidLightLevel();
                if (prevLightValue != lightValue) {
                    prevLightValue = lightValue;
                    worldObj.lightupdateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
                }
*/

            EntityPlayerSP player = FMLClientHandler.instance().getClient().thePlayer;
            if (pos.distanceSq(player.posX, player.posY, player.posZ) < 128) {
                while (this.blobs.size() < getTankFluidAmount() / Fluid.BUCKET_VOLUME)
                    this.addBlob();
                while (this.blobs.size() > getTankFluidAmount() / Fluid.BUCKET_VOLUME)
                    this.removeBlob();

                for (int i = 0; i < this.blobs.size(); i++)
                    this.blobs.get(i).update(0.1F);
            }

            System.out.println(blobs.size());
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

    public int getTankCapacity() {

        return tank.getCapacity();
    }

    public int getTankFluidAmount() {

        return tank.getFluidAmount();
    }

    @Nullable
    public Fluid getFluid() {
        return tank.getFluid() == null ? null : tank.getFluid().getFluid();
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

    public FluidTank getTank() {
        return tank;
    }

    public void addBlob() {
        this.blobs.add(new Blobs(this.random.nextInt(12) + 2.5F, this.random.nextInt(9) + 4.5F, this.random.nextInt(12) + 2.5F, 4));
    }

    public void removeBlob() {
        this.blobs.remove(this.random.nextInt(this.blobs.size()));
    }

    public List<Blobs> getBlobs() {
        return blobs;
    }
}
