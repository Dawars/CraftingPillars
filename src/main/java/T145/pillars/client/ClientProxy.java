package T145.pillars.client;

import net.minecraft.network.Packet;
import net.minecraftforge.client.MinecraftForgeClient;
import T145.pillars.client.renderers.RenderAnvilPillar;
import T145.pillars.client.renderers.RenderBrewingPillar;
import T145.pillars.client.renderers.RenderChristmasLeaves;
import T145.pillars.client.renderers.RenderCraftingPillar;
import T145.pillars.client.renderers.RenderDiskPillar;
import T145.pillars.client.renderers.RenderExtendPillar;
import T145.pillars.client.renderers.RenderFreezerPillar;
import T145.pillars.client.renderers.RenderFurnacePillar;
import T145.pillars.client.renderers.RenderLight;
import T145.pillars.client.renderers.RenderPotPillar;
import T145.pillars.client.renderers.RenderPresent;
import T145.pillars.client.renderers.RenderPumpPillar;
import T145.pillars.client.renderers.RenderSentryPillar;
import T145.pillars.client.renderers.RenderShowOffPillar;
import T145.pillars.client.renderers.RenderTankPillar;
import T145.pillars.client.renderers.RenderTrashPillar;
import T145.pillars.client.renderers.RingItemRenderer;
import T145.pillars.common.CommonProxy;
import T145.pillars.common.CraftingPillars;
import T145.pillars.common.tiles.TileEntityAnvilPillar;
import T145.pillars.common.tiles.TileEntityBrewingPillar;
import T145.pillars.common.tiles.TileEntityChristmasPresent;
import T145.pillars.common.tiles.TileEntityCraftingPillar;
import T145.pillars.common.tiles.TileEntityDiskPlayerPillar;
import T145.pillars.common.tiles.TileEntityExtendPillar;
import T145.pillars.common.tiles.TileEntityFreezerPillar;
import T145.pillars.common.tiles.TileEntityFurnacePillar;
import T145.pillars.common.tiles.TileEntityLight;
import T145.pillars.common.tiles.TileEntityPotPillar;
import T145.pillars.common.tiles.TileEntityPumpPillar;
import T145.pillars.common.tiles.TileEntitySentryPillar;
import T145.pillars.common.tiles.TileEntityShowOffPillar;
import T145.pillars.common.tiles.TileEntityTankPillar;
import T145.pillars.common.tiles.TileEntityTrashPillar;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void initIconRegistry()
	{

		CraftingPillars.extendPillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.showOffPillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.craftingPillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.furnacePillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.anvilPillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.tankPillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.brewingillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.diskPlayerRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.freezerPillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.potPillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.sentryPillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.trashPillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.pumpPillarRenderID = RenderingRegistry.getNextAvailableRenderId();

		CraftingPillars.christmasLeavesRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.PresentRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.lightRenderID = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(new RenderExtendPillar());
		RenderingRegistry.registerBlockHandler(new RenderShowOffPillar());
		RenderingRegistry.registerBlockHandler(new RenderCraftingPillar());
		RenderingRegistry.registerBlockHandler(new RenderFurnacePillar());
		RenderingRegistry.registerBlockHandler(new RenderAnvilPillar());
		RenderingRegistry.registerBlockHandler(new RenderTankPillar());
		RenderingRegistry.registerBlockHandler(new RenderBrewingPillar());
		RenderingRegistry.registerBlockHandler(new RenderDiskPillar());
		RenderingRegistry.registerBlockHandler(new RenderFreezerPillar());
		RenderingRegistry.registerBlockHandler(new RenderPotPillar());
		RenderingRegistry.registerBlockHandler(new RenderSentryPillar());
		RenderingRegistry.registerBlockHandler(new RenderTrashPillar());
		RenderingRegistry.registerBlockHandler(new RenderPumpPillar());

		if(CraftingPillars.winter)
			RenderingRegistry.registerBlockHandler(new RenderChristmasLeaves());
		RenderingRegistry.registerBlockHandler(new RenderPresent());
		RenderingRegistry.registerBlockHandler(new RenderLight());
		
		MinecraftForgeClient.registerItemRenderer(CraftingPillars.itemRing, new RingItemRenderer());

	}

	@Override
	public void initTileRenderer()
	{
//		VersionChecker.check();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExtendPillar.class, new RenderExtendPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShowOffPillar.class, new RenderShowOffPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraftingPillar.class, new RenderCraftingPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnacePillar.class, new RenderFurnacePillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnvilPillar.class, new RenderAnvilPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTankPillar.class, new RenderTankPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBrewingPillar.class, new RenderBrewingPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDiskPlayerPillar.class, new RenderDiskPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFreezerPillar.class, new RenderFreezerPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPotPillar.class, new RenderPotPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySentryPillar.class, new RenderSentryPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrashPillar.class, new RenderTrashPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPumpPillar.class, new RenderPumpPillar());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChristmasPresent.class, new RenderPresent());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLight.class, new RenderLight());


	}

	@Override
	public void sendToServer(Packet packet)
	{
		FMLClientHandler.instance().getClient().getNetHandler().addToSendQueue(packet);
	}
}
