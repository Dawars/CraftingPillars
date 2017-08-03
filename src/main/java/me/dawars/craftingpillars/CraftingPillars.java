package me.dawars.craftingpillars;

import me.dawars.craftingpillars.blocks.BlockCraftingPillar;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.logging.Logger;

@Mod(modid = CraftingPillars.MODID, name = CraftingPillars.NAME, version = CraftingPillars.VERSION)
public class CraftingPillars {
    public static final String MODID = "craftingpillars";
    public static final String NAME = "Crafting Pillars";
    public static final String VERSION = "2.0";

    public static final Logger LOGGER = Logger.getLogger(MODID);

    public static final CreativeTabs CREATIVETAB = new CreativeTabs("craftingpillars") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(BLOCK_CRAFTINGPILLAR);
        }
    };

    public static final Block BLOCK_CRAFTINGPILLAR = new BlockCraftingPillar("craftingpillar");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Pre-Initialization...");

        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initialization...");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("Post-Initialization...");
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        LOGGER.info("Registering blocks...");
        event.getRegistry().registerAll(BLOCK_CRAFTINGPILLAR);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        LOGGER.info("Registering items...");
        event.getRegistry().registerAll(new ItemBlock(BLOCK_CRAFTINGPILLAR).setRegistryName(BLOCK_CRAFTINGPILLAR.getRegistryName()));
    }
}
