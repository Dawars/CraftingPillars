package me.dawars.CraftingPillars.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class BaseItem extends Item
{
	public BaseItem(int id)
	{
		super(id);
		this.setCreativeTab(CraftingPillars.tabPillar);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister itemIcon)
	{
		this.itemIcon = itemIcon.registerIcon(CraftingPillars.id + ":" + this.getUnlocalizedName().substring(5));
	}
}
