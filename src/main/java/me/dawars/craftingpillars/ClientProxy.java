package me.dawars.craftingpillars;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraftingPillar.class, new TESRCraftingPillar());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTank.class, new TESRTankPillar());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}
