package me.dawars.CraftingPillars.items;

import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemRecord;

public class PillarRecord extends ItemRecord
{
        public PillarRecord(int id, String recordName)
        {
                super(id, recordName);
                this.setCreativeTab(CreativeTabs.tabMisc);
                this.maxStackSize = 1;
               
               
        }
       
       
            public String getRecordTitle()
            {
               if(this.recordName == "mac_dubstepgun:seeherobrine.ogg")
                        return "McMillan - Polyhymnia";
               
               return "C418 - " + this.recordName;
            }
       
 
       
       
}