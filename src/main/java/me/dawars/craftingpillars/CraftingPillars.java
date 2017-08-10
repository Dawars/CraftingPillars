package me.dawars.craftingpillars;

import me.dawars.craftingpillars.blocks.CpBlocks;
import me.dawars.craftingpillars.tileentity.TileEntityCraftingPillar;
import me.dawars.craftingpillars.tileentity.TileTank;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = CraftingPillars.MODID, name = CraftingPillars.NAME, version = CraftingPillars.VERSION)
public class CraftingPillars {
    public static final String MODID = "craftingpillars";
    public static final String NAME = "Crafting Pillars";
    public static final String VERSION = "2.0";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "me.dawars.craftingpillars.ClientProxy", serverSide = "me.dawars.craftingpillars.ServerProxy")
    private static CommonProxy proxy;

    public static final CreativeTabs CREATIVETAB = new CreativeTabs("craftingpillars") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(CpBlocks.craftingPillar);
        }
    };


    public CraftingPillars() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Pre-Initialization...");

        proxy.preInit(event);

        CpBlocks.init();
        CpBlocks.register();

        MinecraftForge.EVENT_BUS.register(this);
        registerTileEntities();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initialization...");

        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("Post-Initialization...");

        proxy.postInit(event);
    }
/*
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        LOGGER.info("Registering blocks...");
        event.getRegistry().registerAll(BLOCK_CRAFTINGPILLAR);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        LOGGER.info("Registering items...");
        event.getRegistry().registerAll(new ItemBlock(BLOCK_CRAFTINGPILLAR).setRegistryName(BLOCK_CRAFTINGPILLAR.getRegistryName()));
    }*/

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityCraftingPillar.class,MODID+":tileentity_craftingpillar");
        GameRegistry.registerTileEntity(TileTank.class,MODID+":TE_tank");
    }

    @SubscribeEvent
    public void onLeftClickBlockEvent(PlayerInteractEvent.LeftClickBlock event) {
        LOGGER.info("leftclick: "+event.getWorld().isRemote+" "+event.getHitVec());

        /*if (event.getWorld().isRemote) {
            if (event.getFace() == EnumFacing.UP) {
                Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
                if (block.getClass() == BlockCraftingPillar.class) {
                    event.setCanceled(true);
                    LOGGER.info("canceled!");
                    // TODO maybe make custom method in BasePillar with hit vector
                    block.onBlockClicked(event.getWorld(), event.getPos(), event.getEntityPlayer());
                }
            }
        }*/
    }

    @SubscribeEvent
    public void onBreakEvent(BlockEvent.BreakEvent event) {
        LOGGER.info("breakevent: "+event.getWorld().isRemote);
        /*if (event.getPlayer().isCreative() && event.getState().getBlock().getClass() == BlockCraftingPillar.class) {
            TileEntityCraftingPillar te = (TileEntityCraftingPillar) event.getWorld().getTileEntity(event.getPos());
            if (te != null && te.getResultStack() != null && te.getResultStack().stackSize > 0) {
                event.setCanceled(true);
                //MinecraftForge.EVENT_BUS.post(new PlayerInteractEvent.LeftClickBlock(event.getPlayer(), event.getPos(), ))
            }
        }*/
    }
}
