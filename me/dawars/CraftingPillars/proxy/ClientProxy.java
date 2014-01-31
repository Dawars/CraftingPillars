package me.dawars.CraftingPillars.proxy;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.*;
import me.dawars.CraftingPillars.handlers.PillarSoundHandler;
import me.dawars.CraftingPillars.renderer.*;
import me.dawars.CraftingPillars.tiles.*;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public Object getClient()
	{
		return FMLClientHandler.instance().getClient();
	}

	public Object getPlayer()
	{
		return FMLClientHandler.instance().getClient().thePlayer;
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void init()
	{
		VersionChecker.check();

		MinecraftForge.EVENT_BUS.register(new PillarSoundHandler());

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

		CraftingPillars.christmasLeavesRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.PresentRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.lightRenderID = RenderingRegistry.getNextAvailableRenderId();

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

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChristmasPresent.class, new RenderPresent());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLight.class, new RenderLight());

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

		RenderingRegistry.registerBlockHandler(new RenderChristmasLeaves());
		RenderingRegistry.registerBlockHandler(new RenderPresent());
		RenderingRegistry.registerBlockHandler(new RenderLight());
	}

	@Override
	public void sendToServer(Packet packet)
	{
		FMLClientHandler.instance().getClient().getNetHandler().addToSendQueue(packet);
	}

	@Override
	public String playerName()
	{
		return FMLClientHandler.instance().getClient().thePlayer.username;
	}
}
