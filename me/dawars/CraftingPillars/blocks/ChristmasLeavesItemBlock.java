package me.dawars.CraftingPillars.blocks;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ChristmasLeavesItemBlock extends ItemBlock
{
		
	private final static String[] subNames = {
		"spruce", "fostimber"
	};

	public ChristmasLeavesItemBlock(int id) {
		super(id);
		this.setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata (int meta) {
		return meta;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
	}

}
