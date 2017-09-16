package me.dawars.craftingpillars;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = CraftingPillars.MODID, name = CraftingPillars.NAME, version = CraftingPillars.VERSION)
public class CraftingPillars {
    public static final String MODID = "craftingpillars";
    public static final String NAME = "Crafting Pillars";
    public static final String VERSION = "2.0";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "me.dawars.craftingpillars.ClientProxy", serverSide = "me.dawars.craftingpillars.ServerProxy")
    public static IProxy proxy;

    public static final CreativeTabs CREATIVETAB = new CreativeTabs("craftingpillars") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(CpBlocks.craftingPillar);
        }
    };


    public CraftingPillars() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Pre-Initialization...");

        registerTileEntities();

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initialization...");

        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("Post-Initialization...");

        proxy.postInit(event);
    }

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityCraftingPillar.class,MODID+":tileentity_craftingpillar");
        GameRegistry.registerTileEntity(TileTank.class,MODID+":TE_tank");
    }

    @SubscribeEvent
    public void onBreakEvent(BlockEvent.BreakEvent event) {
        if (!event.world.isRemote && event.getPlayer().capabilities.isCreativeMode && event.getPlayer().isSneaking()) {
            Block block = event.getState().getBlock();
            if (block.getClass() == BlockCraftingPillar.class) {
                event.setCanceled(true);
                block.onBlockClicked(event.getWorld(), event.getPos(), event.getPlayer());
            }
        }
    }
}
