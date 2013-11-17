package me.dawars.CraftingPillars.tiles;

import java.util.Map;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.container.ContainerCraftingPillar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
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
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class TileEntityAnvilPillar extends BaseTileEntityPillar
{
	// @SideOnly(Side.CLIENT)
	public float rot = 0F;
	
	@Override
	public void updateEntity()
	{
		if(this.worldObj.isRemote)
		{
			this.rot += 0.1F;
			if(this.rot >= 360F)
				this.rot -= 360F;
		}
		
		super.updateEntity();
	}
	
	public void buildItem(EntityPlayer player)
	{
		this.inventory[0] = null;
		this.inventory[1] = null;
		this.dropItemFromSlot(2, 64, player);
	}
	
	@Override
	public void onInventoryChanged()
	{
		this.updateOutput();
		super.onInventoryChanged();
	}
	
	public void updateOutput()
	{
		if(this.inventory[0] == null)
		{
			this.inventory[2] = this.inventory[1];
			return;
		}
		if(this.inventory[1] == null)
		{
			this.inventory[2] = this.inventory[0];
			return;
		}
		
		// TODO update
	}
	
	@Override
	public boolean isOnlyDisplaySlot(int i)
	{
		return i == 2;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 3;
	}
	
	@Override
	public String getInvName()
	{
		return "craftingpillars.pillar.anvil.name";
	}
}
