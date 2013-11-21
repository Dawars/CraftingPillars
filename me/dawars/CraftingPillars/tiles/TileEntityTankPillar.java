package me.dawars.CraftingPillars.tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.dawars.CraftingPillars.Blobs;
import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityTankPillar extends BaseTileEntity implements IFluidHandler
{
	public final FluidTank tank = new FluidTank((int) FluidContainerRegistry.BUCKET_VOLUME * 16);
	
	public List<Blobs> blobs;
	
	public TileEntityTankPillar()
	{
		this.blobs = new ArrayList<Blobs>();
		//generateBlobs();
	}
	
	public void addBlob()
	{
		blobs.add(new Blobs(random.nextInt(12)+2.5F, random.nextInt(9)+4.5F, random.nextInt(12)+2.5F, 3));
	}
	
	public void removeBlob()
	{
		blobs.remove(random.nextInt(blobs.size()));
	}
	
	/*private void generateBlobs()
	{
		//Big
		for(int i = 0; i < 3; i++)
		{
			// x, z: 2.5-13.5 y: 4.5-12.5
			int strength = random.nextInt(3)+4;
			blobs.add(new Blobs(random.nextInt(12)+2.5F, random.nextInt(9)+4.5F, random.nextInt(12)+2.5F, strength));
		}
		//Small
		for(int i = 0; i < 10; i++)
		{
			// x, z: 2.5-13.5 y: 4.5-12.5
			blobs.add(new Blobs(random.nextInt(12)+2.5F, random.nextInt(9)+4.5F, random.nextInt(12)+2.5F, (random.nextInt(20)+5)/10));
		}
	}*/
	
	public int[][][] texIndieces = null;
	
	@Override
	public void updateEntity()
	{
		if(this.worldObj.isRemote)
		{
			if(this.texIndieces == null)
			{
				this.texIndieces = new int[16][16][16];
				for(int i = 0; i < 16; i++)
					for(int j = 0; j < 16; j++)
						for(int k = 0; k < 16; k++)
							this.texIndieces[i][j][k] = random.nextInt(256);
			}
			
			while(this.blobs.size() < this.tank.getFluidAmount()/FluidContainerRegistry.BUCKET_VOLUME)
				this.addBlob();
			while(this.blobs.size() > this.tank.getFluidAmount()/FluidContainerRegistry.BUCKET_VOLUME)
				this.removeBlob();
			
			int i = random.nextInt(16);
			int j = random.nextInt(16);
			int k = random.nextInt(16);
			this.texIndieces[i][j][k]++;
			this.texIndieces[i][j][k] %= 256;
			
			for(i = 0; i < this.blobs.size(); i++)
				this.blobs.get(i).update(0.1F);
		}
//		if(tank.getFluid() != null && worldObj.isRemote)
//			System.out.println((worldObj.isRemote ? "Client: " : "Server: ")+tank.getFluid().amount + " " + FluidRegistry.getFluidName(tank.getFluid()));
	}
	
	/* SAVING & LOADING */
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(nbt.hasKey("tank"))
			this.tank.setFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("tank")));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if(tank.getFluid() != null)
			nbt.setTag("tank", tank.getFluid().writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		NBTTagCompound nbt = pkt.data;
		this.readFromNBT(nbt);
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}
	
	@Override
	public void onInventoryChanged()
	{
		super.onInventoryChanged();
		if(!this.worldObj.isRemote)
			CraftingPillars.proxy.sendToPlayers(this.getDescriptionPacket(), this.worldObj, this.xCoord, this.yCoord, this.zCoord, 64);
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		int res = this.tank.fill(resource, doFill);
		this.onInventoryChanged();
		return res;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		return this.drain(from, resource.amount, doDrain);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		FluidStack res = this.tank.drain(maxDrain, doDrain);
		this.onInventoryChanged();
		return res;
	}
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return true;
	}
	
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return true;
	}
	
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return new FluidTankInfo[] { tank.getInfo() };
	}
	
	public int getFluidLightLevel() {
		FluidStack tankFluid = tank.getFluid();
        return tankFluid == null ? 0 : tankFluid.getFluid().getLuminosity(tankFluid);
	}
}