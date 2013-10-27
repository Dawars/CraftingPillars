package me.dawars.CraftingPillars.event;

import cpw.mods.fml.client.FMLClientHandler;
import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EventHandler {

	Minecraft mc = FMLClientHandler.instance().getClient();;

	@ForgeSubscribe
	public void onClick(PlayerInteractEvent event) {

		if (event.action == Action.RIGHT_CLICK_BLOCK) {

			if ((mc.theWorld.getBlockId(event.x, event.y, event.z) == CraftingPillars.blockCraftingPillar.blockID ||
					mc.theWorld.getBlockId(event.x, event.y, event.z) == CraftingPillars.blockAnvilPillar.blockID ||
					mc.theWorld.getBlockId(event.x, event.y, event.z) == CraftingPillars.blockFurnacePillar.blockID) && 
					event.face == 1 && event.entityPlayer.isSneaking() && event.entityPlayer.inventory.getCurrentItem() != null) {
				if (event.isCancelable()) {
					event.setCanceled(true);
				}

			}

		}

	}
}
