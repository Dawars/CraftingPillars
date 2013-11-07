package me.dawars.CraftingPillars.tile;

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
	private static final int MAX_Fluid = FluidContainerRegistry.BUCKET_VOLUME * 10;
	public final FluidTank tank = new FluidTank((int) MAX_Fluid);
	
	public List<Blobs> blobs;
	
	private Random random;
	
	public TileEntityTankPillar()
	{
		this.random = new Random(System.currentTimeMillis());
		this.blobs = new ArrayList<Blobs>();
		generateBlobs();
		for(int i = 0; i < 16; i++)
			for(int j = 0; j < 16; j++)
				for(int k = 0; k < 16; k++)
					this.texIndieces[i][j][k] = random.nextInt(256);
	}
	
	private void generateBlobs()
	{
		//Big
		for(int i = 0; i < 3; i++)
		{
			// x, z: 2.5-13.5 y: 4.5-12.5
			int strength = random.nextInt(3)+4;
			/*if(random.nextBoolean())
				strength *= -1;*/
			blobs.add(new Blobs(random.nextInt(12)+2.5F, random.nextInt(9)+4.5F, random.nextInt(12)+2.5F, strength));
		}
		//Small
		for(int i = 0; i < 10; i++)
		{
			// x, z: 2.5-13.5 y: 4.5-12.5
			blobs.add(new Blobs(random.nextInt(12)+2.5F, random.nextInt(9)+4.5F, random.nextInt(12)+2.5F, (random.nextInt(20)+5)/10));
		}
	}
	
	public int[][][] texIndieces = new int[16][16][16];
	
	public void updateEntity()
	{
		if(CraftingPillars.proxy.isRenderWorld(worldObj))
		{
			int i = random.nextInt(16);
			int j = random.nextInt(16);
			int k = random.nextInt(16);
			this.texIndieces[i][j][k]++;
			this.texIndieces[i][j][k] %= 256;
			/*for(int i = 0; i < 16; i++)
				for(int j = 0; j < 16; j++)
					for(int k = 0; k < 16; k++)
						if(random.nextInt(16*16*16) == 0)
						{
							this.texIndieces[i][j][k]++;
							this.texIndieces[i][j][k] %= 256;
						}*/
			
			for(i = 0; i < this.blobs.size(); i++)
			{
				this.blobs.get(i).update(0.1F);
			}
			
//			int[][][] field = Blobs.fieldStrength(blobs);
//
//			for(int x = 0; x < 16; x++)
//			{
//				for(int y = 0; y < 16; y++)
//				{
//					for(int z = 0; z < 16; z++)
//					{
//						System.out.print(field[x][y][z] + " ");
//					}
//					System.out.println();
//
//				}
//				System.out.println();
//				System.out.println();
//				System.out.println();
//			}
			
		}
		if(tank.getFluid() != null && !worldObj.isRemote)
			System.out.println(tank.getFluid().amount + " " + FluidRegistry.getFluidName(tank.getFluid()));
	}
	
	/* SAVING & LOADING */
	@Override
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
		FluidStack liquid = new FluidStack(0, 0);
		
		if(data.hasKey("stored") && data.hasKey("FluidId"))
		{
			liquid = new FluidStack(data.getInteger("FluidId"), data.getInteger("stored"));
		}
		else
		{
			liquid = FluidStack.loadFluidStackFromNBT(data.getCompoundTag("tank"));
		}
		tank.setFluid(liquid);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
		if(tank.getFluid() != null)
		{
			data.setTag("tank", tank.getFluid().writeToNBT(new NBTTagCompound()));
		}
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
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, nbt);
	}
	
	@Override
	public void onInventoryChanged()
	{
		super.onInventoryChanged();
		
		if(!this.worldObj.isRemote)
		{
			CraftingPillars.proxy.sendToPlayers(this.getDescriptionPacket(), this.worldObj, this.xCoord, this.yCoord, this.zCoord, 128);
		}
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		return this.tank.fill(resource, doFill);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		return this.drain(from, resource.amount, doDrain);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return this.tank.drain(maxDrain, doDrain);
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
}