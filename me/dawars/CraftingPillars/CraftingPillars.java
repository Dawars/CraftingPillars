package me.dawars.CraftingPillars;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.dawars.CraftingPillars.api.CraftingPillarAPI;
import me.dawars.CraftingPillars.blocks.*;
import me.dawars.CraftingPillars.client.gui.GuiHandler;
import me.dawars.CraftingPillars.handlers.*;
import me.dawars.CraftingPillars.items.*;
import me.dawars.CraftingPillars.tiles.*;
import me.dawars.CraftingPillars.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(name = CraftingPillars.name, version = CraftingPillars.version, useMetadata = false, modid = CraftingPillars.id, dependencies = "required-after:Forge@[8.9.0,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {CraftingPillars.channelGame, CraftingPillars.channelGui}, packetHandler = PillarPacketHandler.class)
public class CraftingPillars
{
	@Instance(CraftingPillars.id)
	private static CraftingPillars instance;
	
	public static CraftingPillars getInstance()
	{
		return instance;
	}
	
	public static final String version = "1.4";
	public static final String name = "Crafting Pillars";
	public static final String id = "craftingpillars";
	public static final String channelGame = "PillarGameClick";
	public static final String channelGui = "PillarGuiClick";
	
	// The Handler For Opening Guis
    private GuiHandler guiHandler = new GuiHandler();
	
	@SidedProxy(clientSide = "me.dawars.CraftingPillars.proxy.ClientProxy", serverSide = "me.dawars.CraftingPillars.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Configuration config;
	
	public static final CreativeTabs tabPillar = new CreativeTabs("CraftingPillars")
	{
		@SideOnly(Side.CLIENT)
		public int getTabIconItemIndex()
		{
			return CraftingPillars.blockCraftingPillar.blockID;
		}
	};

	public static int extendPillarRenderID;
	public static int showOffPillarRenderID;
	public static int craftingPillarRenderID;
	public static int furnacePillarRenderID;
	public static int anvilPillarRenderID;
	public static int tankPillarRenderID;
	public static int brewingillarRenderID;
	public static int diskPlayerRenderID;
	public static int freezerPillarRenderID;

	public static Block blockExtendPillar;
	public static Block blockShowOffPillar;
	public static Block blockCraftingPillar;
	public static Block blockFurnacePillar;
	public static Block blockAnvilPillar;
//	public static Block blockTankPillar;
	public static Block blockBrewingPillar;
	public static Block blockDiskPlayerPillar;
	public static Block blockFreezerPillar;

	public static Item discElysium;
	public static Item itemCalendar;

	public static boolean floatingItems = true, rayTrace = false, renderHitBoxes = true, winter;
	
	public static final Achievement achievementGettingStarted = new Achievement(509, "gettingstarted", -2, 0, /* blockCraftingPillar */Block.stoneBrick, AchievementList.openInventory).registerAchievement();
	public static final Achievement achievementRecursion = new Achievement(510, "recursion", -3, -2, /* blockCraftingPillar */Item.redstone, achievementGettingStarted).registerAchievement();
	public static final Achievement achievementShowoff = new Achievement(511, "showoff", -5, -2, /* blockCraftingPillar */Item.diamond, achievementRecursion).registerAchievement();
	
	@EventHandler
	public void load(FMLPreInitializationEvent evt)
	{
		winter = /*isWinterTime()*/true;
		
		config = new Configuration(new File(evt.getModConfigurationDirectory(), "CraftingPillars.cfg"));
		try
		{
			config.load();
			// Block Registering
			Property idExtendPillar = CraftingPillars.config.getBlock("ExtendPillar.id", BlockIds.idExtendPillar);
			blockExtendPillar = (new ExtendPillarBlock(idExtendPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("extendPillar");
			registerBlock(blockExtendPillar);
			LanguageRegistry.instance().addStringLocalization(blockExtendPillar.getUnlocalizedName()+".name", "en_US", "Base Pillar");
			
			Property idShowOffPillar = CraftingPillars.config.getBlock("ShowOffPillar.id", BlockIds.idShowOffPillar);
			blockShowOffPillar = (new ShowOffPillarBlock(idShowOffPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("showOffPillar");
			registerBlock(blockShowOffPillar);
			LanguageRegistry.instance().addStringLocalization(blockShowOffPillar.getUnlocalizedName()+".name", "en_US", "Show-Off Pillar");
			
			Property idCraftingPillar = CraftingPillars.config.getBlock("CraftingPillar.id", BlockIds.idCraftingPillar);
			blockCraftingPillar = (new CraftingPillarBlock(idCraftingPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("craftingPillar");
			registerBlock(blockCraftingPillar);
			LanguageRegistry.instance().addStringLocalization(blockCraftingPillar.getUnlocalizedName()+".name", "en_US", "Crafting Pillar");
			
			Property idFurnacePillar = CraftingPillars.config.getBlock("FurnacePillar.id", BlockIds.idFurnacePillar);
			blockFurnacePillar = (new FurnacePillarBlock(idFurnacePillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("furnacePillar");
			registerBlock(blockFurnacePillar);
			LanguageRegistry.instance().addStringLocalization(blockFurnacePillar.getUnlocalizedName()+".name", "en_US", "Furnace Pillar");
			
			Property idAnvilPillar = CraftingPillars.config.getBlock("AnvilPillar.id", BlockIds.idAnvilPillar, "Coming soon...");
			blockAnvilPillar = (new AnvilPillar(idAnvilPillar.getInt(), Material.anvil)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("anvilPillar");
			registerBlock(blockAnvilPillar);
			LanguageRegistry.instance().addStringLocalization(blockAnvilPillar.getUnlocalizedName()+".name", "en_US", "Anvil Pillar");

//			Property idTankPillar = CraftingPillars.config.getBlock("TankPillar.id", BlockIds.idTankPillar, "Coming soon...");
//			blockTankPillar = (new TankPillarBlock(idTankPillar.getInt(), Material.glass)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("tankPillar");
//			registerBlock(blockTankPillar);
//			LanguageRegistry.instance().addStringLocalization(blockTankPillar.getUnlocalizedName()+".name", "en_US", "Tank Pillar");

			Property idFreezerPillar = CraftingPillars.config.getBlock("FreezerPillar.id", BlockIds.idFreezerPillar);
			blockFreezerPillar = (new FreezerPillarBlock(idFreezerPillar.getInt(), Material.glass)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("freezerPillar");
			registerBlock(blockFreezerPillar);
			LanguageRegistry.instance().addStringLocalization(blockFreezerPillar.getUnlocalizedName()+".name", "en_US", "Freezer Pillar");
			
			Property idBrewingPillar = CraftingPillars.config.getBlock("BrewingPillar.id", BlockIds.idBrewingPillar);
			blockBrewingPillar = (new BrewingPillarBlock(idBrewingPillar.getInt(), Material.iron)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("brewingPillar");
			registerBlock(blockBrewingPillar);
			LanguageRegistry.instance().addStringLocalization(blockBrewingPillar.getUnlocalizedName()+".name", "en_US", "Brewing Pillar");
			
			Property idDiskPlayerPillar = CraftingPillars.config.getBlock("DiskPlayerPillar.id", BlockIds.idDiskPillar);
			blockDiskPlayerPillar = (new DiskPlayerPillarBlock(idDiskPlayerPillar.getInt(), Material.iron)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("diskPillar");
			registerBlock(blockDiskPlayerPillar);
			LanguageRegistry.instance().addStringLocalization(blockDiskPlayerPillar.getUnlocalizedName()+".name", "en_US", "Juke Pillar");

			Property idDiscElysium = CraftingPillars.config.getItem("idDiscElysium.id", BlockIds.idDiscElysium);
			discElysium = new PillarRecord(idDiscElysium.getInt(), CraftingPillars.id + ":UranusParadiseShort").setUnlocalizedName("record");
            LanguageRegistry.instance().addStringLocalization(CraftingPillars.id + ":UranusParadiseShort", "en_US", "Elysium - Uranus Paradise Short");

			Property idAdventCalendar = CraftingPillars.config.getItem("idAdventCalendar.id", BlockIds.idAdventCalendar);
			itemCalendar = new AdventCalendar(idAdventCalendar.getInt()).setUnlocalizedName("adventCalendar");
			LanguageRegistry.instance().addStringLocalization(itemCalendar.getUnlocalizedName() + ".name", "en_US", "Advent Calendar");

			GameRegistry.registerTileEntity(TileEntityExtendPillar.class, "TileEntityExtendPillar");
			GameRegistry.registerTileEntity(TileEntityShowOffPillar.class, "TileEntityShowOffPillar");
			GameRegistry.registerTileEntity(TileEntityCraftingPillar.class, "TileEntityCraftingPillar");
			GameRegistry.registerTileEntity(TileEntityFurnacePillar.class, "TileEntityFurnacePillar");
			GameRegistry.registerTileEntity(TileEntityAnvilPillar.class, "TileEntityAnvilPillar");
			GameRegistry.registerTileEntity(TileEntityTankPillar.class, "TileEntityTankPillar");
			GameRegistry.registerTileEntity(TileEntityEnchantmentPillar.class, "TileEntityEnchantmentPillar");
			GameRegistry.registerTileEntity(TileEntityBrewingPillar.class, "TileEntityBrewingPillar");
			GameRegistry.registerTileEntity(TileEntityDiskPlayerPillar.class, "TileEntityDiskPlayerPillar");
			GameRegistry.registerTileEntity(TileEntityFreezerPillar.class, "TileEntityFreezerPillar");
			
			
			LanguageRegistry.instance().addStringLocalization("itemGroup.CraftingPillars", "en_US", "Crafting Pillars");
			
			LanguageRegistry.instance().addStringLocalization("achievement.gettingstarted", "en_US", "Getting Started");
			LanguageRegistry.instance().addStringLocalization("achievement.gettingstarted.desc", "en_US", "Craft a BasicPillar");

			LanguageRegistry.instance().addStringLocalization("achievement.recursion", "en_US", "Recursion I");
			LanguageRegistry.instance().addStringLocalization("achievement.recursion.desc", "en_US", "Craft a CraftingPillar in a CraftingPillar");
			
			LanguageRegistry.instance().addStringLocalization("achievement.showoff", "en_US", "Recursion II");
			LanguageRegistry.instance().addStringLocalization("achievement.showoff.desc", "en_US", "Show off your Show-Off Pillar!");
			
			proxy.init();
			
			GameRegistry.addRecipe(new ItemStack(blockExtendPillar), new Object[] { "SSS", " S ", "SSS", Character.valueOf('S'), Block.stone });
			GameRegistry.addRecipe(new ItemStack(blockFreezerPillar), new Object[] { "SSS", "SPS", "SSS", Character.valueOf('S'), Block.blockSnow, Character.valueOf('P'), blockExtendPillar });
			GameRegistry.addShapelessRecipe(new ItemStack(blockShowOffPillar), new ItemStack(Item.itemFrame), new ItemStack(blockExtendPillar));
			GameRegistry.addShapelessRecipe(new ItemStack(blockCraftingPillar), new ItemStack(Block.workbench), new ItemStack(blockExtendPillar));
			GameRegistry.addShapelessRecipe(new ItemStack(blockFurnacePillar), new ItemStack(Block.furnaceIdle), new ItemStack(blockExtendPillar));
			
			GameRegistry.addShapelessRecipe(new ItemStack(blockBrewingPillar), new ItemStack(Item.brewingStand), new ItemStack(blockExtendPillar));
			
			CraftingPillarAPI.addDiskTexture(discElysium.itemID, CraftingPillars.id + ":textures/models/disk_elysium.png");
			
			GameRegistry.registerCraftingHandler(new PillarCraftingHandler());
		}
		finally
		{
			config.save();
		}
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		CraftingPillarAPI.addDiskTexture(Item.record13.itemID, CraftingPillars.id + ":textures/models/disk_13.png");
		CraftingPillarAPI.addDiskTexture(Item.recordCat.itemID, CraftingPillars.id + ":textures/models/disk_cat.png");
		CraftingPillarAPI.addDiskTexture(Item.recordBlocks.itemID, CraftingPillars.id + ":textures/models/disk_blocks.png");
		CraftingPillarAPI.addDiskTexture(Item.recordChirp.itemID, CraftingPillars.id + ":textures/models/disk_chirp.png");
		CraftingPillarAPI.addDiskTexture(Item.recordFar.itemID, CraftingPillars.id + ":textures/models/disk_far.png");
		CraftingPillarAPI.addDiskTexture(Item.recordMall.itemID, CraftingPillars.id + ":textures/models/disk_mall.png");
		CraftingPillarAPI.addDiskTexture(Item.recordMellohi.itemID, CraftingPillars.id + ":textures/models/disk_mellohi.png");
		CraftingPillarAPI.addDiskTexture(Item.recordStal.itemID, CraftingPillars.id + ":textures/models/disk_stal.png");
		CraftingPillarAPI.addDiskTexture(Item.recordStrad.itemID, CraftingPillars.id + ":textures/models/disk_strad.png");
		CraftingPillarAPI.addDiskTexture(Item.recordWard.itemID, CraftingPillars.id + ":textures/models/disk_ward.png");
		CraftingPillarAPI.addDiskTexture(Item.record11.itemID, CraftingPillars.id + ":textures/models/disk_11.png");
		CraftingPillarAPI.addDiskTexture(Item.recordWait.itemID, CraftingPillars.id + ":textures/models/disk_wait.png");
	
        NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
        MinecraftForge.EVENT_BUS.register(new PillarEventHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public static void registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, CraftingPillars.id + ":" + block.getUnlocalizedName().substring(5));
	}
	
	public static boolean isAfter(String date)
	{
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date c = new Date();
			Date v = format.parse(date);
			System.out.println(c);
			System.out.println(v);
			return v.after(c);
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static int getNumberOfCalendarElements()
	{
		Calendar c = Calendar.getInstance();
		return 11;
		/*if(c.get(Calendar.MONTH) == Calendar.DECEMBER)
			return Math.min(c.get(Calendar.DAY_OF_MONTH), 24);
		else if(c.get(Calendar.MONTH) == Calendar.JANUARY)
			return 24;
		else
			return 0;*/
	}
	
	public static boolean isWinterTime()
	{
		Calendar c = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		b.set(Calendar.MONTH, Calendar.DECEMBER);
		b.set(Calendar.DAY_OF_MONTH, 1);
		b.set(Calendar.HOUR_OF_DAY, 0);
		b.set(Calendar.MINUTE, 0);
		b.set(Calendar.MILLISECOND, 0);
		Calendar e = Calendar.getInstance();
		e.set(Calendar.YEAR, c.get(Calendar.YEAR)+1);
		e.set(Calendar.MONTH, Calendar.JANUARY);
		e.set(Calendar.DAY_OF_MONTH, 1);
		e.set(Calendar.HOUR_OF_DAY, 0);
		e.set(Calendar.MINUTE, 0);
		e.set(Calendar.MILLISECOND, 0);
		return c.after(b) && c.before(e);
	}
}
