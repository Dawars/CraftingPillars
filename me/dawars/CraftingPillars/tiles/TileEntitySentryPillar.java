package me.dawars.CraftingPillars.tiles;

import java.util.List;
import java.util.Random;

import javax.swing.text.html.parser.Entity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.CustomParticle;
import me.dawars.CraftingPillars.container.ContainerCraftingPillar;
import me.dawars.CraftingPillars.tiles.BaseTileEntity;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

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
			if(this.cooldown <= 0)
			{
				if(this.target != null && this.getStackInSlot(0) != null)
				{
					System.out.println(target.getEntityName());
					//FIXME: if target is in range
					if(this.getStackInSlot(0) != null)
					{
						if((float) this.target.getDistanceSq(xCoord, yCoord, zCoord) < 64)
						{
							if(this.getStackInSlot(0).itemID == Item.arrow.itemID)//Add snowball and...?
							{
					            EntityArrow entityarrow = new EntityArrow(worldObj, xCoord, yCoord+1, zCoord);
					            entityarrow.setDamage(entityarrow.getDamage() + 1);
					            
			
					            entityarrow.posY = this.yCoord + 1.5F;
					            double d0 = this.target.posX - this.xCoord - 0.5F;
					            double d1 = this.target.boundingBox.minY + (double)(this.target.height / 3.0F) - entityarrow.posY;
					            double d2 = this.target.posZ - this.zCoord - 0.5F;
					            double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
			
					            if (d3 >= 1.0E-7D)
					            {
					                float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
					                float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
					                double d4 = d0 / d3;
					                double d5 = d2 / d3;
					                entityarrow.setLocationAndAngles(this.xCoord + 0.5F + d4, entityarrow.posY, this.zCoord + 0.5F + d5, f2, f3);
					                entityarrow.yOffset = 0.0F;
					                float f4 = (float)d3 * 0.2F;
					                entityarrow.setThrowableHeading(d0, d1 + (double)f4, d2, 1.6F, (float)(14 - this.worldObj.difficultySetting * 4));
					            }
					            
					            worldObj.spawnEntityInWorld(entityarrow);
					            worldObj.playSoundAtEntity(this.target, "random.bow", 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F) + 0.5F);
							}
							
							if(this.getStackInSlot(0).itemID == Item.snowball.itemID)//Add snowball and...?
							{
					            EntitySnowball entitysnowball = new EntitySnowball(worldObj, xCoord + 0.5F, yCoord + 1.5F, zCoord + 0.5F);
					            
					            double d0 = this.target.posX - this.xCoord - 0.5F;
					            double d1 = this.target.posY + (double)this.target.getEyeHeight() - 1.100000023841858D - entitysnowball.posY;
					            double d2 = this.target.posZ - this.zCoord - 0.5F;;
					            float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
					            entitysnowball.setThrowableHeading(d0, d1 + (double)f1, d2, 1.6F, 12.0F);
					            entitysnowball.playSound("random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
					            entitysnowball.worldObj.spawnEntityInWorld(entitysnowball);
							}
							
							
				            this.decrStackSize(0, 1);
				            this.cooldown = 20;
						}
					}
				}
			} else {
				this.cooldown--;
			}
			
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
