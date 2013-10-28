package me.dawars.CraftingPillars.event;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EventHandler {


	@ForgeSubscribe
	public void onClick(PlayerInteractEvent event) {
	
			if (event.action == Action.RIGHT_CLICK_BLOCK) {
	
				if ((event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == CraftingPillars.blockCraftingPillar.blockID ||
	//					event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == CraftingPillars.blockAnvilPillar.blockID ||
						event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == CraftingPillars.blockShowOffPillar.blockID ||
						event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == CraftingPillars.blockFurnacePillar.blockID) && 
						event.face == 1 && event.entityPlayer.isSneaking() && event.entityPlayer.inventory.getCurrentItem() != null) {
					if (event.isCancelable()) {
						event.setCanceled(true);
					}
	
				}
	
			}

	}
}
