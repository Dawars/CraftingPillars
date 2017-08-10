package me.dawars.craftingpillars.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.LinkedList;
import java.util.List;

public class CpBlocks {

    private static final List<BaseBlock> inits = new LinkedList<>();

    public static final BaseBlock craftingPillar = new BlockCraftingPillar("craftingpillar");
    public static final BaseBlock tankPillar = new BlockTank("tank_pillar");

    public static void init() {
        inits.add(craftingPillar);
        inits.add(tankPillar);


    }

    public static void register() {
        for (BaseBlock block : inits) {
            registerBlock(block);
        }
    }

    public static void registerRenderers() {
        for (BaseBlock block : inits) {
            registerRenderer(block);
        }
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
