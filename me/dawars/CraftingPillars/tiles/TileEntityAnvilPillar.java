package me.dawars.CraftingPillars.tiles;

import net.minecraft.entity.player.EntityPlayer;

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
		return 64;
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
