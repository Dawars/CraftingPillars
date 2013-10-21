package me.dawars.CraftingPillars.proxy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.renderer.RenderCraftingPillar;
import me.dawars.CraftingPillars.renderer.RenderFurnacePillar;
import me.dawars.CraftingPillars.tile.TileEntityCraftingPillar;
import me.dawars.CraftingPillars.tile.TileEntityFurnacePillar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
	/* INSTANCES */
	public Object getClient()
	{
		return FMLClientHandler.instance().getClient();
	}
	
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	@Override
	public void registerRenderers()
	{
		CraftingPillars.craftingPillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		CraftingPillars.furnacePillarRenderID = RenderingRegistry.getNextAvailableRenderId();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraftingPillar.class, new RenderCraftingPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnacePillar.class, new RenderFurnacePillar());
		
		RenderingRegistry.registerBlockHandler(new RenderCraftingPillar());
		RenderingRegistry.registerBlockHandler(new RenderFurnacePillar());
		
	}
	
	/* NETWORKING */
	@Override
	public void sendToServer(Packet packet)
	{
		FMLClientHandler.instance().getClient().getNetHandler().addToSendQueue(packet);
	}
	
	/*
	 * @Override public void sendToPlayer(EntityPlayer entityplayer,
	 * ElysiumPacket packet){}
	 * 
	 * @Override public void sendToPlayers(Packet packet, World world, int x,
	 * int y, int z, int maxDistance){}
	 */
	
	@Override
	public String playerName()
	{
		return FMLClientHandler.instance().getClient().thePlayer.username;
	}
}
