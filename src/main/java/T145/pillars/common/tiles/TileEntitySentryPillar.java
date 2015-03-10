package T145.pillars.common.tiles;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import T145.pillars.api.sentry.ISentryBehaviorItem;
import T145.pillars.api.sentry.SentryBehaviors;

public class TileEntitySentryPillar extends BaseTileEntity implements IInventory, ISidedInventory
{
	public static int sentryCooldown = 20;
	public static float sentryRange = 16*16;
	private ItemStack[] inventory = new ItemStack[getSizeInventory()];

	public float rot = 0F;
	public int cooldown = sentryCooldown;
	public boolean showNum = false;
	private String owner = "";

	private EntityLiving target = null;

	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote)
		{
			rot += 0.1F;
			if(rot >= 360F)
				rot -= 360F;
		}

		if(!worldObj.isRemote)
		{
			ItemStack ammo = getStackInSlot(0);
			if(ammo != null && worldObj.loadedEntityList != null)
			{
				float closest = Float.MAX_VALUE;
				for(int i = 0; i < worldObj.loadedEntityList.size(); i++)
				{
					if(worldObj.loadedEntityList.get(i) instanceof IMob)
					{
						EntityLiving currentMob = (EntityLiving)worldObj.loadedEntityList.get(i);
						if(currentMob.isEntityAlive() && !currentMob.isInvisible())
						{
							float distance = (float)currentMob.getDistanceSq(xCoord, yCoord, zCoord);
							if(distance < closest && isVisible(xCoord, yCoord, zCoord, currentMob))
							{
								closest = distance;
								target = currentMob;
							}
						}
					}
				}
			}

			if(cooldown <= 0)
			{
				if(target != null && !target.isDead && target.getDistanceSq(xCoord, yCoord, zCoord) <= sentryRange && getStackInSlot(0) != null)
				{
					if(ammo != null)
					{
						BlockSourceImpl blocksourceimpl = new BlockSourceImpl(worldObj, xCoord, yCoord, zCoord);
						ISentryBehaviorItem ibehaviorsentryitem = SentryBehaviors.get(ammo.getItem());

						if(ibehaviorsentryitem != null)
						{

							ItemStack itemstack1 = null;
							if(!owner.equals(""))
							{
								itemstack1 = ibehaviorsentryitem.dispense(blocksourceimpl, target, worldObj.getPlayerEntityByName(owner), ammo);
							} else
							{
								if(worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 64) != null)
									itemstack1 = ibehaviorsentryitem.dispense(blocksourceimpl, target, worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 64), ammo);
							}
							if(itemstack1 != null)
							{
								setInventorySlotContents(0, itemstack1.stackSize == 0 ? null : itemstack1);
								cooldown = ibehaviorsentryitem.reloadSpeed(ammo);
							}
						} else {
							cooldown = sentryCooldown;
						}

					}
				}
			}
			else
			{
				cooldown--;
			}
		}

		super.updateEntity();
	}

	private boolean isVisible(int x, int y, int z, EntityLiving mob)
	{
		double x1 = x+0.5D;
		double y1 = y+1.5D;
		double z1 = z+0.5D;
		double x2 = mob.posX;
		double y2 = mob.posY+mob.getEyeHeight();
		double z2 = mob.posZ;
		double distance = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) + (z2-z1)*(z2-z1);
		double distanceSqrt = Math.sqrt(distance);

		double dx = (x2-x1)/distanceSqrt;
		double dy = (y2-y1)/distanceSqrt;
		double dz = (z2-z1)/distanceSqrt;

		double i = x1;
		double j = y1;
		double k = z1;

		while((i-x1)*(i-x1) + (j-y1)*(j-y1) + (k-z1)*(k-z1) < distance)
		{
			if(collide(i, j, k))
				return false;
			i += dx;
			j += dy;
			k += dz;
		}
		return true;
	}

	private boolean collide(double i, double j, double k)
	{
		if(!worldObj.blockExists((int) i, (int)j, (int) k))
			return true;
		Block block = worldObj.getBlock((int)Math.floor(i), (int)Math.floor(j), (int)Math.floor(k));
		
		if(block == null || block == Blocks.air || block.getMaterial().isLiquid())
			return false;
		i -= Math.floor(i);
		j -= Math.floor(j);
		k -= Math.floor(k);
		return block.getBlockBoundsMinX() <= i
				&& i <= block.getBlockBoundsMaxX()
				&& block.getBlockBoundsMinY() <= j
				&& j <= block.getBlockBoundsMaxY()
				&& block.getBlockBoundsMinZ() <= k
				&& k <= block.getBlockBoundsMaxZ();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		inventory = new ItemStack[getSizeInventory()];
		NBTTagList nbtlist = nbt.getTagList("Items", 10);
		for(int i = 0; i < nbtlist.tagCount(); i++)
		{
			NBTTagCompound nbtslot = (NBTTagCompound)nbtlist.getCompoundTagAt(i);
			int j = nbtslot.getByte("Slot") & 255;

			if((j >= 0) && (j < getSizeInventory()))
				inventory[j] = ItemStack.loadItemStackFromNBT(nbtslot);
		}

		showNum = nbt.getBoolean("showNum");
		owner = nbt.getString("owner");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbtlist = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(inventory[i] != null)
			{
				NBTTagCompound nbtslot = new NBTTagCompound();
				nbtslot.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(nbtslot);
				nbtlist.appendTag(nbtslot);
			}
		}
		nbt.setTag("Items", nbtlist);
		nbt.setBoolean("showNum", showNum);
		nbt.setString("owner", owner);
	}

	public void dropItemFromSlot(int slot, int amount, EntityPlayer player)
	{
		if(worldObj.isRemote)
			return;

		if(getStackInSlot(slot) != null)
		{
			EntityItem itemEntity = new EntityItem(worldObj, player.posX, player.posY, player.posZ);
			itemEntity.setEntityItemStack(decrStackSize(slot, amount));
			worldObj.spawnEntityInWorld(itemEntity);

			onInventoryChanged();
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
		return inventory[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[slot] = stack;

		if(stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}

		onInventoryChanged();
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = null;

		if(inventory[slot] != null)
		{
			if(inventory[slot].stackSize <= amount)
			{
				stack = inventory[slot];
				inventory[slot] = null;
				onInventoryChanged();
			}
			else
			{
				stack = inventory[slot].splitStack(amount);

				if(inventory[slot].stackSize == 0)
				{
					inventory[slot] = null;
				}

				onInventoryChanged();
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
			setInventorySlotContents(slot, null);
		}

		return stack;
	}


	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}


	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return (getStackInSlot(i) != null && getStackInSlot(i).isItemEqual(itemstack)) || SentryBehaviors.get(itemstack.getItem()) != null;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
		return true;
	}

	public void setOwner(String name)
	{
		owner = name;
	}

	public void setOwner(EntityPlayer player)
	{
		owner = player.getCommandSenderName();
	}

	public String getOwnerName()
	{
		return owner;
	}

	public EntityPlayer getOwner()
	{
		return worldObj.getPlayerEntityByName(owner);
	}

	@Override
	public String getInventoryName() {
		return "Sentry Pillar";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return super.isUseableByPlayer(player);
	}

	public void setAmmo(ItemStack item)
	{
		inventory[0] = item;
	}
}
