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
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(name = CraftingPillars.name, version = CraftingPillars.version, useMetadata = false, modid = CraftingPillars.id, dependencies = "required-after:Forge@[8.9.0,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {CraftingPillars.channelGame, CraftingPillars.channelGui, CraftingPillars.channelProps}, packetHandler = PillarPacketHandler.class)
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
	public static final String channelProps = "PillarProps";
	
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
	public static int potPillarRenderID;
	
	public static int christmasLeavesRenderID;

	public static Block blockBasePillar;
	public static Block blockShowOffPillar;
	public static Block blockCraftingPillar;
	public static Block blockFurnacePillar;
	public static Block blockAnvilPillar;
//	public static Block blockTankPillar;
	public static Block blockBrewingPillar;
	public static Block blockDiskPlayerPillar;
	public static Block blockFreezerPillar;
	public static Block blockPotPillar;

	public static Block blockChristmasLeaves;
	public static Block blockChristmasTreeSapling;

	public static Item itemDiscElysium;
	public static Item itemCalendar2013;
	public static Item itemChristmasCandy;
	public static Item itemGingerbreadMan;
	public static Item itemElysiumLoreBook;
	public static Item itemRibbonDiamond;
	

	public static boolean floatingItems = true, rayTrace = false, renderHitBoxes = true, winter;
	
	public static Achievement achievementGettingStarted;
	public static Achievement achievementChristmas;
	public static Achievement achievementRecursion;
	public static Achievement achievementCompressingLiquids;
	
	public static Achievement achievementShowoff;
	public static Achievement achievementDiamond;
	public static Achievement achievementDisc;

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt)
	{
		winter = isWinterTime();
		
		config = new Configuration(new File(evt.getModConfigurationDirectory(), "CraftingPillars.cfg"));
		try
		{
			config.load();
			// Block Registering
			Property idExtendPillar = CraftingPillars.config.getBlock("ExtendPillar.id", BlockIds.idExtendPillar);
			blockBasePillar = (new ExtendPillarBlock(idExtendPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("extendPillar");
			registerBlock(blockBasePillar);
			LanguageRegistry.instance().addStringLocalization(blockBasePillar.getUnlocalizedName()+".name", "Base Pillar");
			
			Property idShowOffPillar = CraftingPillars.config.getBlock("ShowOffPillar.id", BlockIds.idShowOffPillar);
			blockShowOffPillar = (new ShowOffPillarBlock(idShowOffPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("showOffPillar");
			registerBlock(blockShowOffPillar);
			LanguageRegistry.instance().addStringLocalization(blockShowOffPillar.getUnlocalizedName()+".name",  "Show-Off Pillar");
			
			Property idCraftingPillar = CraftingPillars.config.getBlock("CraftingPillar.id", BlockIds.idCraftingPillar);
			blockCraftingPillar = (new CraftingPillarBlock(idCraftingPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("craftingPillar");
			registerBlock(blockCraftingPillar);
			LanguageRegistry.instance().addStringLocalization(blockCraftingPillar.getUnlocalizedName()+".name", "Crafting Pillar");
			
			Property idFurnacePillar = CraftingPillars.config.getBlock("FurnacePillar.id", BlockIds.idFurnacePillar);
			blockFurnacePillar = (new FurnacePillarBlock(idFurnacePillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("furnacePillar");
			registerBlock(blockFurnacePillar);
			LanguageRegistry.instance().addStringLocalization(blockFurnacePillar.getUnlocalizedName()+".name", "Furnace Pillar");
			
			Property idAnvilPillar = CraftingPillars.config.getBlock("AnvilPillar.id", BlockIds.idAnvilPillar);
			blockAnvilPillar = (new AnvilPillar(idAnvilPillar.getInt(), Material.anvil)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("anvilPillar");
			registerBlock(blockAnvilPillar);
			LanguageRegistry.instance().addStringLocalization(blockAnvilPillar.getUnlocalizedName()+".name", "Anvil Pillar");

//			Property idTankPillar = CraftingPillars.config.getBlock("TankPillar.id", BlockIds.idTankPillar, "Coming soon...");
//			blockTankPillar = (new TankPillarBlock(idTankPillar.getInt(), Material.glass)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("tankPillar");
//			registerBlock(blockTankPillar);
//			LanguageRegistry.instance().addStringLocalization(blockTankPillar.getUnlocalizedName()+".name", "Tank Pillar");

			Property idBrewingPillar = CraftingPillars.config.getBlock("BrewingPillar.id", BlockIds.idBrewingPillar);
			blockBrewingPillar = (new BrewingPillarBlock(idBrewingPillar.getInt(), Material.iron)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("brewingPillar");
			registerBlock(blockBrewingPillar);
			LanguageRegistry.instance().addStringLocalization(blockBrewingPillar.getUnlocalizedName()+".name", "Brewing Pillar");

			Property idDiskPlayerPillar = CraftingPillars.config.getBlock("DiskPlayerPillar.id", BlockIds.idDiskPillar);
			blockDiskPlayerPillar = (new DiskPlayerPillarBlock(idDiskPlayerPillar.getInt(), Material.iron)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("diskPillar");
			registerBlock(blockDiskPlayerPillar);
			LanguageRegistry.instance().addStringLocalization(blockDiskPlayerPillar.getUnlocalizedName()+".name", "Juke Pillar");

			Property idFreezerPillar = CraftingPillars.config.getBlock("FreezerPillar.id", BlockIds.idFreezerPillar);
			blockFreezerPillar = (new FreezerPillarBlock(idFreezerPillar.getInt(), Material.glass)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("freezerPillar");
			registerBlock(blockFreezerPillar);
			LanguageRegistry.instance().addStringLocalization(blockFreezerPillar.getUnlocalizedName()+".name", "Freezer Pillar");
			
			Property idPotPillar = CraftingPillars.config.getBlock("PotPillar.id", BlockIds.idPotPillar);
			blockPotPillar = (new PotPillarBlock(idPotPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("potPillar");
			registerBlock(blockPotPillar);
			LanguageRegistry.instance().addStringLocalization(blockPotPillar.getUnlocalizedName()+".name", "Pot Pillar");
			
			//Christmas
			Property idChristmasLeaves = CraftingPillars.config.getBlock("ChristmasLeaves.id", BlockIds.idChristmasLeaves);
			blockChristmasLeaves = (new ChristmasLeavesBlock(idChristmasLeaves.getInt(), Material.leaves)).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("xmasLeaves");
			if(!winter)
				blockChristmasLeaves.setCreativeTab(null);
			registerBlock(blockChristmasLeaves);
			LanguageRegistry.instance().addStringLocalization(blockChristmasLeaves.getUnlocalizedName()+".name", "en_US", "Christmas Leaves");

			Property idChristmasTreeSapling = CraftingPillars.config.getBlock("ChristmasTreeSapling.id", BlockIds.idChristmasTreeSapling);
			blockChristmasTreeSapling = (new ChristmasTreeSapling(idChristmasTreeSapling.getInt(), Material.plants)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("ChristmasTreeSapling");
			if(!winter)
				blockChristmasTreeSapling.setCreativeTab(null);
			registerBlock(blockChristmasTreeSapling);
			LanguageRegistry.instance().addStringLocalization(blockChristmasTreeSapling.getUnlocalizedName()+".name", "en_US", "Christmas Tree Sapling");
			
			
			
			Property idDiscElysium = CraftingPillars.config.getItem("DiscElysium.id", BlockIds.idDiscElysium);
			itemDiscElysium = new PillarRecord(idDiscElysium.getInt(), CraftingPillars.id + ":UranusParadiseShort").setUnlocalizedName("record").setTextureName(CraftingPillars.id + ":ElysiumDisk");
            LanguageRegistry.instance().addStringLocalization(CraftingPillars.id + ":UranusParadiseShort", "Elysium - Uranus Paradise Short");
            
			Property idAdventCalendar = CraftingPillars.config.getItem("AdventCalendar2013.id", BlockIds.idAdventCalendar2013);
			itemCalendar2013 = new AdventCalendar(idAdventCalendar.getInt()).setUnlocalizedName("adventCalendar2013");
			if(!winter)
				itemCalendar2013.setCreativeTab(null);
			LanguageRegistry.instance().addStringLocalization(itemCalendar2013.getUnlocalizedName() + ".name", "Advent Calendar 2013");

			Property idChrsitmasCandy = CraftingPillars.config.getItem("ChrsitmasCandy.id", BlockIds.idChrsitmasCandy);
			itemChristmasCandy = new BaseItemEatable(idChrsitmasCandy.getInt(), 5, 0.5F).setUnlocalizedName("ChristmasCandy");
			if(!winter)
				itemChristmasCandy.setCreativeTab(null);
			LanguageRegistry.instance().addStringLocalization(itemChristmasCandy.getUnlocalizedName() + ".name", "Christmas Candy");

			Property idGingerbreadMan = CraftingPillars.config.getItem("GingerbreadMan.id", BlockIds.idGingerbreadMan);
			itemGingerbreadMan = new BaseItemEatable(idGingerbreadMan.getInt(), 7, 1.5F).setUnlocalizedName("GingerbreadMan");
			if(!winter)
				itemGingerbreadMan.setCreativeTab(null);
			LanguageRegistry.instance().addStringLocalization(itemGingerbreadMan.getUnlocalizedName() + ".name", "Gingerbread Man");

			Property idRibbonDiamond = CraftingPillars.config.getItem("RibbonDiamond.id", BlockIds.idRibbonDiamond);
			itemRibbonDiamond = new BaseItem(idRibbonDiamond.getInt()).setUnlocalizedName("RibbonDiamond");
			LanguageRegistry.instance().addStringLocalization(itemRibbonDiamond.getUnlocalizedName() + ".name", "Ribbon Diamond");
			
			Property idLoreBook = CraftingPillars.config.getItem("LoreBook.id", BlockIds.idLoreBook);
			itemElysiumLoreBook = new BaseItem(idLoreBook.getInt()).setUnlocalizedName("ElysiumLoreBook");
			LanguageRegistry.instance().addStringLocalization(itemElysiumLoreBook.getUnlocalizedName() + ".name", "Elysium Lore Book");

		}
		finally
		{
			config.save();
		}

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
		GameRegistry.registerTileEntity(TileEntityPotPillar.class, "TileEntityPotPillar");
		
		
		LanguageRegistry.instance().addStringLocalization("itemGroup.CraftingPillars", "en_US", "Crafting Pillars");
		
		LanguageRegistry.instance().addStringLocalization("achievement.gettingstarted", "en_US", "Getting Started");
		LanguageRegistry.instance().addStringLocalization("achievement.gettingstarted.desc", "en_US", "Craft a BasicPillar");

		LanguageRegistry.instance().addStringLocalization("achievement.recursion", "en_US", "Recursion I");
		LanguageRegistry.instance().addStringLocalization("achievement.recursion.desc", "en_US", "Craft a CraftingPillar in a CraftingPillar");

		LanguageRegistry.instance().addStringLocalization("achievement.showoff", "en_US", "Recursion II");
		LanguageRegistry.instance().addStringLocalization("achievement.showoff.desc", "en_US", "Show off your Show-Off Pillar!");

		LanguageRegistry.instance().addStringLocalization("achievement.christmaspillar", "en_US", "Christmas Event");
		LanguageRegistry.instance().addStringLocalization("achievement.christmaspillar.desc", "en_US", "Celebrate Christmas with us!");

		LanguageRegistry.instance().addStringLocalization("achievement.christmasdiamond", "en_US", "Diamond Mania");
		LanguageRegistry.instance().addStringLocalization("achievement.christmasdiamond.desc", "en_US", "Unwrap a Diamond!");

		LanguageRegistry.instance().addStringLocalization("achievement.liquids", "en_US", "Liquid Tanks!");
		LanguageRegistry.instance().addStringLocalization("achievement.liquids.desc", "en_US", "Store liquids in an ancient way!");

		LanguageRegistry.instance().addStringLocalization("achievement.elysiandisc", "en_US", "Preparing with music!");
		LanguageRegistry.instance().addStringLocalization("achievement.elysiandisc.desc", "en_US", "Listen to the Elysium Theme disc!");
		
		proxy.init();
		
		GameRegistry.addRecipe(new ItemStack(blockBasePillar), new Object[] { "SSS", " S ", "SSS", Character.valueOf('S'), Block.stone });
		
		GameRegistry.addRecipe(new ItemStack(blockFreezerPillar), new Object[] { "SSS", "GPG", "SSS", Character.valueOf('S'), Block.blockSnow, Character.valueOf('P'), blockBasePillar, Character.valueOf('G'), Block.thinGlass});
		GameRegistry.addShapelessRecipe(new ItemStack(blockShowOffPillar), new ItemStack(Item.itemFrame), new ItemStack(blockBasePillar));
		GameRegistry.addShapelessRecipe(new ItemStack(blockCraftingPillar), new ItemStack(Block.workbench), new ItemStack(blockBasePillar));
		GameRegistry.addShapelessRecipe(new ItemStack(blockFurnacePillar), new ItemStack(Block.furnaceIdle), new ItemStack(blockBasePillar));
		GameRegistry.addShapelessRecipe(new ItemStack(blockBrewingPillar), new ItemStack(Item.brewingStand), new ItemStack(blockBasePillar));
		GameRegistry.addRecipe(new ItemStack(blockBrewingPillar), new Object[] { "S", "F", "P", Character.valueOf('S'), Block.dirt, Character.valueOf('P'), blockBasePillar , Character.valueOf('F'), Block.flowerPot});
		
		if(winter)
		{
			// TODO add crafting for winter items
		}
		
		GameRegistry.addShapelessRecipe(new ItemStack(Item.diamond), new ItemStack(itemRibbonDiamond));
		
		CraftingPillarAPI.addDiskTexture(itemDiscElysium.itemID, CraftingPillars.id + ":textures/models/disk_elysium.png");
		
		//OreDictionary
		OreDictionary.registerOre("record", itemDiscElysium);
		OreDictionary.registerOre("treeSapling", blockChristmasTreeSapling);
		OreDictionary.registerOre("treeLeaves",  blockChristmasLeaves);
		
		GameRegistry.registerCraftingHandler(new PillarCraftingHandler());
		
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
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		achievementGettingStarted = new Achievement(509, "gettingstarted", -2, -1, blockBasePillar, AchievementList.openInventory).registerAchievement();
		achievementRecursion = new Achievement(510, "recursion", -3, -3, blockCraftingPillar, achievementGettingStarted).registerAchievement();
		achievementShowoff = new Achievement(511, "showoff", -5, -3, blockShowOffPillar, achievementRecursion).registerAchievement();
		achievementCompressingLiquids = new Achievement(512, "liquids", -4, -1, blockFreezerPillar, achievementGettingStarted).registerAchievement();
		
		achievementChristmas = new Achievement(515, "christmaspillar", -2, 2, blockChristmasTreeSapling, (Achievement)null).setSpecial().setIndependent().registerAchievement();
		achievementDiamond = new Achievement(516, "christmasdiamond", -1, 2, itemRibbonDiamond, achievementChristmas).setSpecial().registerAchievement();
		achievementDisc = new Achievement(517, "elysiandisc", -1, 2, itemDiscElysium, achievementDiamond).setSpecial().registerAchievement();
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
	
	public static int getWinterDay(int year)
	{
		Calendar c = Calendar.getInstance();
		return 11; // TODO debug
		/*if(c.get(Calendar.YEAR) == year && c.get(Calendar.MONTH) == Calendar.DECEMBER && c.get(Calendar.DAY_OF_MONTH) <= 24)
			return c.get(Calendar.DAY_OF_MONTH);
		else
			return 0;*/
	}
	
	public static boolean isWinterTime()
	{
		Calendar c = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		b.set(Calendar.MONTH, Calendar.NOVEMBER);
		b.set(Calendar.DAY_OF_MONTH, 15);
		b.set(Calendar.HOUR_OF_DAY, 0);
		b.set(Calendar.MINUTE, 0);
		b.set(Calendar.MILLISECOND, 0);
		Calendar e = Calendar.getInstance();
		e.set(Calendar.YEAR, c.get(Calendar.YEAR)+1);
		e.set(Calendar.MONTH, Calendar.JANUARY);
		e.set(Calendar.DAY_OF_MONTH, 15);
		e.set(Calendar.HOUR_OF_DAY, 0);
		e.set(Calendar.MINUTE, 0);
		e.set(Calendar.MILLISECOND, 0);
		return c.after(b) && c.before(e);
	}
}
