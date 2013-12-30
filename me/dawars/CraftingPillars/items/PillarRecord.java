package me.dawars.CraftingPillars.items;

import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PillarRecord extends ItemRecord
{
	public PillarRecord(int id, String recordName)
	{
		super(id, recordName);
		this.setCreativeTab(CraftingPillars.tabPillar);
		this.maxStackSize = 1;

	}

	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	{
		super.onItemUse(item, player, world, x, y, z, par7, par8, par9, par10);
		if (world.getBlockId(x, y, z) == Block.jukebox.blockID && world.getBlockMetadata(x, y, z) == 0)
		{
			player.addStat(CraftingPillars.achievementDisc, 1);
			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	public String getRecordTitle()
	{
		if(this.recordName == CraftingPillars.id + ":UranusParadiseShort") return "Elysium Theme";
		return this.recordName;
	}
}