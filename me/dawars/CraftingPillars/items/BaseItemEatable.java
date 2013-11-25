package me.dawars.CraftingPillars.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
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
		this.itemIcon = itemIcon.registerIcon(CraftingPillars.id + ":" + getUnlocalizedName().substring(5));
	}
}
