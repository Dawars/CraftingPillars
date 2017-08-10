package me.dawars.craftingpillars;

import me.dawars.craftingpillars.blocks.CpBlocks;
import me.dawars.craftingpillars.client.render.RendererTank;
import me.dawars.craftingpillars.client.render.TESRCraftingPillar;
import me.dawars.craftingpillars.tileentity.TileEntityCraftingPillar;
import me.dawars.craftingpillars.tileentity.TileTank;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
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
}
