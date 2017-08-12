package me.dawars.craftingpillars;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Proxy {
    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    /**
     * The following methods are from the CoFH Core
     */
    /* SERVER UTILS */
    public boolean isClient() {

        return false;
    }

    public boolean isServer() {

        return true;
    }

    public World getClientWorld() {

        return null;
    }

    public IThreadListener getClientListener() {

        // If this is called on the server, expect a crash.
        return null;
    }

    public IThreadListener getServerListener() {

        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    /* PLAYER UTILS */
    public EntityPlayer findPlayer(String player) {

        return null;
    }

    public EntityPlayer getClientPlayer() {

        return null;
    }
}
