package me.dawars.craftingpillars;

import me.dawars.craftingpillars.blocks.CpBlocks;
import me.dawars.craftingpillars.client.render.RendererTank;
import me.dawars.craftingpillars.client.render.TESRCraftingPillar;
import me.dawars.craftingpillars.tileentity.TileEntityCraftingPillar;
import me.dawars.craftingpillars.tileentity.TileTank;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Locale;

public class ClientProxy extends Proxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        CpBlocks.registerRenderers();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraftingPillar.class, new TESRCraftingPillar());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTank.class, new RendererTank());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

    }


    /**
     *  The following methods are from the CoFH Core
     * */
    /* SERVER UTILS */
    @Override
    public boolean isClient() {

        return true;
    }

    @Override
    public boolean isServer() {

        return false;
    }

    @Override
    public World getClientWorld() {

        return Minecraft.getMinecraft().theWorld;
    }

    @Override
    public IThreadListener getClientListener() {

        return Minecraft.getMinecraft();
    }

    @Override
    public IThreadListener getServerListener() {

        return Minecraft.getMinecraft().getIntegratedServer();
    }

    /* PLAYER UTILS */
    @Override
    public EntityPlayer findPlayer(String playerName) {

        for (EntityPlayer player : FMLClientHandler.instance().getClient().theWorld.playerEntities) {
            if (player.getName().toLowerCase(Locale.US).equals(playerName.toLowerCase(Locale.US))) {
                return player;
            }
        }
        return null;
    }

    @Override
    public EntityPlayer getClientPlayer() {

        return Minecraft.getMinecraft().thePlayer;
    }

}
