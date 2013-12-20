package me.dawars.CraftingPillars.tiles;

import me.dawars.CraftingPillars.BlockIds;
import me.dawars.CraftingPillars.api.sentry.IBehaviorSentryItem;
import me.dawars.CraftingPillars.api.sentry.SentryBehaviors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class TileEntitySentryPillar extends BaseTileEntity implements IInventory, ISidedInventory
{
	private ItemStack[] inventory = new ItemStack[this.getSizeInventory()];
	
	public float rot = 0F;
	public int cooldown = BlockIds.sentryCooldown;
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
			ItemStack ammo = this.getStackInSlot(0);
			if(ammo != null && this.worldObj.loadedEntityList != null )
			{
				float closest = Float.MAX_VALUE;
				for (int i = 0; i < this.worldObj.loadedEntityList.size(); i++) {
					if (this.worldObj.loadedEntityList.get(i) instanceof EntityMob)
					{
						EntityMob currentMob = (EntityMob) this.worldObj.loadedEntityList.get(i);
						if(currentMob.isEntityAlive() && !currentMob.isInvisible())
						{
							float distance = (float) currentMob.getDistanceSq(xCoord, yCoord, zCoord);
							if (distance < closest && isVisible(xCoord, yCoord, zCoord, currentMob)) {
								closest = distance;
								this.target = currentMob;
							}
						}
					}
				}
			}
			
			if(this.cooldown <= 0)
			{
				if(this.target != null && !this.target.isDead && this.target.getDistanceSq(xCoord, yCoord, zCoord) <= BlockIds.sentryRange && this.getStackInSlot(0) != null)
				{
					if(ammo != null)
					{
				        BlockSourceImpl blocksourceimpl = new BlockSourceImpl(worldObj, xCoord, yCoord, zCoord);
				        IBehaviorSentryItem ibehaviorsentryitem = (IBehaviorSentryItem)SentryBehaviors.get(ammo.itemID);
				        
				        if(ibehaviorsentryitem != null)
				        {

				        	ItemStack itemstack1 = ibehaviorsentryitem.dispense(blocksourceimpl, this.target, ammo);
		                    this.setInventorySlotContents(0, itemstack1.stackSize == 0 ? null : itemstack1);
				        }
				        
			            this.cooldown = BlockIds.sentryCooldown;
					}
				}
			} else {
				this.cooldown--;
			}
		}
		
		super.updateEntity();
	}
	
	private boolean isVisible(int x, int y, int z, EntityMob mob)
	{

		double i = x;//block
		double j = y + 1;
		double k = z;
		double distance = i*i+j*j+k*k;
		double distanceSqrt = Math.sqrt(distance);

		double dx = (mob.posX - i) / distanceSqrt;
		double dy = (mob.posY + mob.getEyeHeight() - j) / distanceSqrt;
		double dz = (mob.posZ - k) / distanceSqrt;

		while(i*i+j*j+k*k < distance)
		{
			if (collide((int) i, (int) j, (int) k))
			{
				return false;
			}
			if (i == mob.posX && j == mob.posY+mob.getEyeHeight() && k == mob.posZ)
			{
				return true;
			}
			i += dx;
			j += dy;
			k += dz;
		}
		return true;
	}
	
	private boolean collide(int i, int j, int k) {
		 int id = worldObj.getBlockId(i, j, k);
		    Block block = Block.blocksList[id];
		    if(block == null) return false;
		return !worldObj.isAirBlock(i, j, k) && !block.isBlockReplaceable(worldObj, i, j, k);
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
		return (this.getStackInSlot(i) != null && this.getStackInSlot(i).isItemEqual(itemstack)) || SentryBehaviors.get(itemstack.itemID) != null;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return new int[] {0};
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return this.isItemValidForSlot(slot, itemstack);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
		return true;
	}
	
}
