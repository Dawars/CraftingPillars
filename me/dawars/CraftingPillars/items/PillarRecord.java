package me.dawars.CraftingPillars.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemRecord;

public class PillarRecord extends ItemRecord
{
	public PillarRecord(int id, String recordName)
	{
		super(id, recordName);
		this.setCreativeTab(CraftingPillars.tabPillar);
		this.maxStackSize = 1;

	}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister itemIcon)
    {
        this.itemIcon = itemIcon.registerIcon(CraftingPillars.id + ":" + getUnlocalizedName().substring(5));
    }
	
	public String getRecordTitle()
	{
		return this.recordName == CraftingPillars.id.toLowerCase() + ":UranusParadiseShort" ? "Elysium Theme" : this.recordName;
	}
}