package me.dawars.craftingpillars;

import me.dawars.craftingpillars.client.render.TESRCraftingPillar;
import me.dawars.craftingpillars.tiles.TileEntityCraftingPillar;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements Proxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraftingPillar.class, new TESRCraftingPillar());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}
