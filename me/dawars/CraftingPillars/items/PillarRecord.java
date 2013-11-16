package me.dawars.CraftingPillars.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.handlers.SoundHandler;
import net.minecraft.client.audio.SoundManager;
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
		if(this.recordName == CraftingPillars.id + ":UranusParadiseShort") return "Elysium Theme";
		return this.recordName;
	}
}