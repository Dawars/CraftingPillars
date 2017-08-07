package me.dawars.craftingpillars.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CpBlocks {
    public static Block craftingPillar;

    public static void init() {
        craftingPillar = new BlockCraftingPillar("craftingpillar");
    }

    public static void register() {
        registerBlock(craftingPillar);
    }

    public static void registerRenderers() {
        registerRenderer(craftingPillar);
    }

    private static void registerBlock(Block block) {
        GameRegistry.register(block);
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        GameRegistry.register(item);
    }

    private static void registerRenderer(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
}
