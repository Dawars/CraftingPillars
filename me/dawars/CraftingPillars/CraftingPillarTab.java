package me.dawars.CraftingPillars;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class CraftingPillarTab extends CreativeTabs
{
        public CraftingPillarTab(int par1, String par2Str)
        {
        	super(par1, par2Str);
        }
        
        @SideOnly(Side.CLIENT)
        public int getTabIconItemIndex()
        {
        	return CraftingPillars.blockCraftingPillar.blockID;
        }

        public String getTranslatedTabLabel()
        {
        	return "Crafting Pillars";
        }
}