package me.dawars.CraftingPillars.tiles;

import java.util.List;

import me.dawars.CraftingPillars.api.sentry.IBehaviorSentryItem;
import me.dawars.CraftingPillars.api.sentry.SentryBehaviors;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntitySentryPillar extends BaseTileEntity implements IInventory, ISidedInventory
{
	private ItemStack[] inventory = new ItemStack[this.getSizeInventory()];
	
	public float rot = 0F;
	public int cooldown = 20;
	public boolean showNum = false;

	private EntityMob target = null;
	
	@Override
	public void updateEntity()
	{
		if(this.worldObj.isRemote)
		{
			this.rot += 0.1F;
			if(this.rot >= 360F)
				this.rot -= 360F;
		}
		
		if(!worldObj.isRemote)
		{

			List list = this.worldObj.getLoadedEntityList();
	    	
			float closest = Float.MAX_VALUE;
			for (int i = 0; i < this.worldObj.loadedEntityList.size(); i++) {
				if (this.worldObj.loadedEntityList.get(i) instanceof EntityMob)
				{
					EntityMob currentMob = (EntityMob) this.worldObj.loadedEntityList.get(i);
					if(!currentMob.isDead)
					{
						float distance = (float) currentMob.getDistanceSq(xCoord, yCoord, zCoord);
						if (distance <= 64 && distance < closest*closest) {
							closest = distance;
							this.target = (EntityMob) this.worldObj.loadedEntityList.get(i);
						}
					}
				}
			}
			
			if(this.cooldown <= 0)
			{
				if(this.target != null && this.getStackInSlot(0) != null && this.target.getDistanceSq(xCoord, yCoord, zCoord) <= 64)
				{
					System.out.println(target.getEntityName());

					ItemStack ammo = this.getStackInSlot(0);
					if(ammo != null)
					{
				        BlockSourceImpl blocksourceimpl = new BlockSourceImpl(worldObj, xCoord, yCoord, zCoord);
				        IBehaviorSentryItem ibehaviorsentryitem = (IBehaviorSentryItem)SentryBehaviors.sentryBehaviorRegistry.get(ammo.itemID);
				        
				        if(ibehaviorsentryitem != null)
				        {
				    		System.out.println("Starting dispense");

				        	ItemStack itemstack1 = ibehaviorsentryitem.dispense(blocksourceimpl, this.target, ammo);
		                    this.setInventorySlotContents(0, itemstack1.stackSize == 0 ? null : itemstack1);
				        }
				        
			            this.cooldown = 20;
					}
				}
			} else {
				this.cooldown--;
			}
		}
		
		super.updateEntity();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		this.inventory = new ItemStack[this.getSizeInventory()];
		NBTTagList nbtlist = nbt.getTagList("Items");
		for(int i = 0; i < nbtlist.tagCount(); i++)
		{
			NBTTagCompound nbtslot = (NBTTagCompound) nbtlist.tagAt(i);
			int j = nbtslot.getByte("Slot") & 255;
			
			if((j >= 0) && (j < this.getSizeInventory()))
				this.inventory[j] = ItemStack.loadItemStackFromNBT(nbtslot);
		}
		
		this.showNum = nbt.getBoolean("showNum");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbtlist = new NBTTagList();
		for(int i = 0; i < this.getSizeInventory(); i++)
		{
			if(this.inventory[i] != null)
			{
				NBTTagCompound nbtslot = new NBTTagCompound();
				nbtslot.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(nbtslot);
				nbtlist.appendTag(nbtslot);
			}
		}
		nbt.setTag("Items", nbtlist);
		nbt.setBoolean("showNum", this.showNum);
	}

	public void dropItemFromSlot(int slot, int amount, EntityPlayer player)
	{
		if(this.worldObj.isRemote)
			return;
		
		if(this.getStackInSlot(slot) != null)
		{
			EntityItem itemEntity = new EntityItem(this.worldObj, player.posX, player.posY, player.posZ);
			itemEntity.setEntityItemStack(this.decrStackSize(slot, amount));
			this.worldObj.spawnEntityInWorld(itemEntity);
			
			this.onInventoryChanged();
		}
	}
	
	@Override
	public int getSizeInventory()
	{
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.inventory[slot];
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		this.inventory[slot] = stack;
		
		if(stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}
		
		this.onInventoryChanged();
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = null;
		
		if(this.inventory[slot] != null)
		{
			if(this.inventory[slot].stackSize <= amount)
			{
				stack = this.inventory[slot];
				this.inventory[slot] = null;
				this.onInventoryChanged();
			}
			else
			{
				stack = this.inventory[slot].splitStack(amount);
				
				if(this.inventory[slot].stackSize == 0)
				{
					this.inventory[slot] = null;
				}
				
				this.onInventoryChanged();
			}
		}
		
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		if(stack != null)
		{
			this.setInventorySlotContents(slot, null);
		}
		
		return stack;
	}
	
	@Override
	public String getInvName()
	{
		return "Sentry Pillar";
	}
	
	@Override
	public boolean isInvNameLocalized()
	{
		return true;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public void openChest()
	{
	}
	
	@Override
	public void closeChest()
	{
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return new int[] {0};
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return true;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
		return true;
	}
	
}
