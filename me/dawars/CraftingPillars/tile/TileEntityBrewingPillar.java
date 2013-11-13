package me.dawars.CraftingPillars.tile;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.brewing.PotionBrewedEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class TileEntityBrewingPillar extends BaseTileEntity implements
		IInventory, ISidedInventory {
	private ItemStack[] inventory = new ItemStack[5];

	// @SideOnly(Side.CLIENT)
	public float rot = 0F;
	public boolean showNum = false;

	private int brewTime;
	/**
	 * an integer with each bit specifying whether that slot of the stand
	 * contains a potion
	 */
	private int filledSlots;
	private int ingredientID;

	@Override
	public void updateEntity() {
		// System.out.println((this.worldObj.isRemote ? "Client: " :
		// "Server: ")+this.cookTime+" "+this.burnTime);

		if (this.worldObj.isRemote) {
			this.rot += 0.1F;
			if (this.rot >= 360F)
				this.rot -= 360F;
		}

		if (!worldObj.isRemote) {
			if (this.brewTime > 0) {
				--this.brewTime;

				if (this.brewTime == 0) {
					this.brewPotions();
					this.onInventoryChanged();
				} else if (!this.canBrew()) {
					this.brewTime = 0;
					this.onInventoryChanged();
				} else if (this.ingredientID != this.inventory[4].itemID) {
					this.brewTime = 0;
					this.onInventoryChanged();
				}
			} else if (this.canBrew()) {
				this.brewTime = 350;
				this.ingredientID = this.inventory[4].itemID;
			}

			int i = this.getFilledSlots();

			if (i != this.filledSlots) {
				this.filledSlots = i;
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, i, 2);
			}
		}

		super.updateEntity();
	}

	public int getBrewTime() {
		return this.brewTime;
	}

	private boolean canBrew() {
		if (this.inventory[4] != null && this.inventory[4].stackSize > 0) {
			ItemStack itemstack = this.inventory[4];

			if (!Item.itemsList[itemstack.itemID].isPotionIngredient()) {
				return false;
			} else {
				boolean flag = false;

				for (int i = 0; i < 4; ++i) {
					if (this.inventory[i] != null
							&& this.inventory[i].getItem() instanceof ItemPotion) {
						int j = this.inventory[i].getItemDamage();
						int k = this.getPotionResult(j, itemstack);

						if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)) {
							flag = true;
							break;
						}

						List list = Item.potion.getEffects(j);
						List list1 = Item.potion.getEffects(k);

						if ((j <= 0 || list != list1)
								&& (list == null || !list.equals(list1)
										&& list1 != null) && j != k) {
							flag = true;
							break;
						}
					}
				}

				return flag;
			}
		} else {
			return false;
		}
	}

	private void brewPotions()
    {
        if (this.canBrew())
        {
            ItemStack itemstack = this.inventory[4];

            for (int i = 0; i < 4; ++i)
            {
                if (this.inventory[i] != null && this.inventory[i].getItem() instanceof ItemPotion)
                {
                    int j = this.inventory[i].getItemDamage();
                    int k = this.getPotionResult(j, itemstack);
                    List list = Item.potion.getEffects(j);
                    List list1 = Item.potion.getEffects(k);

                    if ((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null))
                    {
                        if (j != k)
                        {
                            this.inventory[i].setItemDamage(k);
                        }
                    }
                    else if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k))
                    {
                        this.inventory[i].setItemDamage(k);
                    }
                }
            }

            if (Item.itemsList[itemstack.itemID].hasContainerItem())
            {
                this.inventory[4] = Item.itemsList[itemstack.itemID].getContainerItemStack(inventory[4]);
            }
            else
            {
                --this.inventory[4].stackSize;

                if (this.inventory[4].stackSize <= 0)
                {
                    this.inventory[4] = null;
                }
            }
            
            MinecraftForge.EVENT_BUS.post(new PotionBrewedEvent(inventory));
        }
    }

	/**
	 * The result of brewing a potion of the specified damage value with an
	 * ingredient itemstack.
	 */
	private int getPotionResult(int par1, ItemStack par2ItemStack) {
		return par2ItemStack == null ? par1
				: (Item.itemsList[par2ItemStack.itemID].isPotionIngredient() ? PotionHelper
						.applyIngredient(par1,
								Item.itemsList[par2ItemStack.itemID]
										.getPotionEffect()) : par1);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items");
		this.inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
					.tagAt(i);
			byte j = nbttagcompound1.getByte("Slot");

			if (j >= 0 && j < this.inventory.length) {
				this.inventory[j] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.brewTime = nbt.getShort("BrewTime");

		this.showNum = nbt.getBoolean("showNum");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("BrewTime", (short) this.brewTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.inventory.length; ++i) {
			if (this.inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("Items", nbttaglist);
		nbt.setBoolean("showNum", this.showNum);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		NBTTagCompound nbt = pkt.data;
		this.readFromNBT(nbt);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();

		if (!this.worldObj.isRemote)
			CraftingPillars.proxy.sendToPlayers(this.getDescriptionPacket(),
					this.worldObj, this.xCoord, this.yCoord, this.zCoord, 64);
	}

	public void dropItemFromSlot(int slot, int amount, EntityPlayer player) {
		if (!this.worldObj.isRemote && this.getStackInSlot(slot) != null) {
			EntityItem itemEntity = new EntityItem(this.worldObj, player.posX,
					player.posY, player.posZ);
			itemEntity.setEntityItemStack(this.decrStackSize(slot, amount));
			this.worldObj.spawnEntityInWorld(itemEntity);
			this.onInventoryChanged();
		}
	}

	@SideOnly(Side.CLIENT)
	public void setBrewTime(int par1) {
		this.brewTime = par1;
	}

	/**
	 * returns an integer with each bit specifying wether that slot of the stand
	 * contains a potion
	 */
	public int getFilledSlots() {
		int i = 0;

		for (int j = 0; j < 4; ++j) {
			if (this.inventory[j] != null) {
				i |= 1 << j;
			}
		}

		return i;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.inventory[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventory[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = null;

		if (this.inventory[slot] != null) {
			if (this.inventory[slot].stackSize <= amount) {
				stack = this.inventory[slot];
				this.inventory[slot] = null;
				this.onInventoryChanged();
			} else {
				stack = this.inventory[slot].splitStack(amount);

				if (this.inventory[slot].stackSize == 0) {
					this.inventory[slot] = null;
				}

				this.onInventoryChanged();
			}
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			this.setInventorySlotContents(slot, null);
		}

		return stack;
	}

	/**
	 * Returns an array containing the indices of the slots that can be accessed
	 * by automation on the given side of this block.
	 */
	public int[] getAccessibleSlotsFromSide(int par1) {
		return new int[] { 0, 1, 2, 3, 4 };
	}

	/**
	 * Returns true if automation can insert the given item in the given slot
	 * from the given side. Args: Slot, item, side
	 */
	public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
		return this.isItemValidForSlot(par1, par2ItemStack);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot
	 * from the given side. Args: Slot, item, side
	 */
	public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) {
		return true;
	}

	@Override
	public String getInvName() {
		return "Brewing Pillar";
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return Item.itemsList[itemstack.itemID].isPotionIngredient() || itemstack.getItem() instanceof ItemPotion || itemstack.itemID == Item.glassBottle.itemID;
	}
}