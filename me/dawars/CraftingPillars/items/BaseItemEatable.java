package me.dawars.CraftingPillars.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import me.dawars.CraftingPillars.CraftingPillars;

public class BaseItemEatable extends ItemFood
{
	public BaseItemEatable(int id, int heal, float saturation)
	{
		super(id, heal, saturation, false);
		this.setCreativeTab(CraftingPillars.tabPillar);
	}

	public BaseItemEatable(int id, int heal)
	{
		this(id, heal, 0.6F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister itemIcon)
	{
		this.itemIcon = itemIcon.registerIcon(CraftingPillars.id + ":" + this.getUnlocalizedName().substring(5));
	}
}
