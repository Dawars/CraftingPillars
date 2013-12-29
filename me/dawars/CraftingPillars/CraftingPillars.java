package me.dawars.CraftingPillars;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import thaumcraft.api.ItemApi;

import me.dawars.CraftingPillars.api.CraftingPillarAPI;
import me.dawars.CraftingPillars.api.sentry.*;
import me.dawars.CraftingPillars.blocks.*;
import me.dawars.CraftingPillars.client.gui.GuiHandler;
import me.dawars.CraftingPillars.container.ContainerAdventCalendar2013;
import me.dawars.CraftingPillars.handlers.*;
import me.dawars.CraftingPillars.items.*;
import me.dawars.CraftingPillars.proxy.CommonProxy;
import me.dawars.CraftingPillars.tiles.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(name = CraftingPillars.name, version = CraftingPillars.version, useMetadata = false, modid = CraftingPillars.id, dependencies = "required-after:Forge@[9.11.1.953,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {CraftingPillars.channelGame, CraftingPillars.channelGui, CraftingPillars.channelProps}, packetHandler = PillarPacketHandler.class)
public class CraftingPillars
{
	@Instance(CraftingPillars.id)
	private static CraftingPillars instance;
	
	public static CraftingPillars getInstance()
	{
		return instance;
	}
	
	public static final String version = "1.4.2";
	public static final String name = "Crafting Pillars";
	public static final String id = "craftingpillars";
	public static final String channelGame = "PillarGameClick";
	public static final String channelGui = "PillarGuiClick";
	public static final String channelProps = "PillarProps";
	
	public static Random rand;
	
	// The Handler For Opening Guis
    private GuiHandler guiHandler = new GuiHandler();
	public static Property checkUpdates;
	
	public static boolean modForestry = false;
	public static boolean modElysium = false;
	public static boolean modThaumcraft = false;
	
	public static ItemStack itemWandThaumcraft;

	@SidedProxy(clientSide = "me.dawars.CraftingPillars.proxy.ClientProxy", serverSide = "me.dawars.CraftingPillars.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Configuration config;
	
	public static final CreativeTabs tabPillar = new CreativeTabs("CraftingPillars")
	{
		@Override
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
	public static int sentryPillarRenderID;

	public static int PresentRenderID;
	public static int lightRenderID;
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
	public static Block blockSentryPillar;
	
	public static Block blockChristmasLeaves;
	public static Block blockChristmasTreeSapling;
	public static Block blockChristmasPresent;
	public static Block blockChristmasLight;

	public static Item itemDiscElysium;
	public static Item itemCalendar2013;
	public static Item itemElysiumLoreBook;
	public static Item itemRibbonDiamond;
	public static Item itemWinterFood2013;
	public static Item itemVirgacs;
	
	public static boolean floatingItems = true;
	public static boolean rayTrace = false;
	public static boolean renderHitBoxes = true;
	public static boolean winter;
	public static int maxTreeState;
	
	public static AchievementPage achievementPage;
	
	public static Achievement achievementGettingStarted;
	public static Achievement achievementChristmas;
	public static Achievement achievementRecursion;
	public static Achievement achievementCompressingLiquids;
	
	public static Achievement achievementShowoff;
	public static Achievement achievementDiamond;
	public static Achievement achievementDisc;
	public static Achievement achievementRecursion3;
	public static boolean debug = false;
	
	public void addItemsAndBlocks()
	{
		// Block Registering
		Property idExtendPillar = CraftingPillars.config.getBlock("ExtendPillar.id", BlockIds.idExtendPillar);
		blockBasePillar = (new ExtendPillarBlock(idExtendPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("extendPillar");
		registerBlock(blockBasePillar);
		LanguageRegistry.instance().addStringLocalization(blockBasePillar.getUnlocalizedName()+".name", "Base Pillar");
		
		Property idShowOffPillar = CraftingPillars.config.getBlock("ShowOffPillar.id", BlockIds.idShowOffPillar);
		blockShowOffPillar = (new ShowOffPillarBlock(idShowOffPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("showOffPillar");
		registerBlock(blockShowOffPillar);
		LanguageRegistry.instance().addStringLocalization(blockShowOffPillar.getUnlocalizedName()+".name",  "Showcase Pillar");
		
		Property idCraftingPillar = CraftingPillars.config.getBlock("CraftingPillar.id", BlockIds.idCraftingPillar);
		blockCraftingPillar = (new CraftingPillarBlock(idCraftingPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("craftingPillar");
		registerBlock(blockCraftingPillar);
		LanguageRegistry.instance().addStringLocalization(blockCraftingPillar.getUnlocalizedName()+".name", "Crafting Pillar");
		
		Property idFurnacePillar = CraftingPillars.config.getBlock("FurnacePillar.id", BlockIds.idFurnacePillar);
		blockFurnacePillar = (new FurnacePillarBlock(idFurnacePillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("furnacePillar");
		registerBlock(blockFurnacePillar);
		LanguageRegistry.instance().addStringLocalization(blockFurnacePillar.getUnlocalizedName()+".name", "Smelting Pillar");
		
		Property idAnvilPillar = CraftingPillars.config.getBlock("AnvilPillar.id", BlockIds.idAnvilPillar);
		blockAnvilPillar = (new AnvilPillarBlock(idAnvilPillar.getInt(), Material.anvil)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("anvilPillar");
		registerBlock(blockAnvilPillar);
		blockAnvilPillar.setCreativeTab(null);
		LanguageRegistry.instance().addStringLocalization(blockAnvilPillar.getUnlocalizedName()+".name", "Anvil Pillar - NOT WORKING");
		
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

		Property idSentryPillar = CraftingPillars.config.getBlock("SentryPillar.id", BlockIds.idSentryPillar);
		blockSentryPillar = (new SentryPillarBlock(idSentryPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("sentryPillar");
		registerBlock(blockSentryPillar);
		LanguageRegistry.instance().addStringLocalization(blockSentryPillar.getUnlocalizedName()+".name", "Sentry Pillar");
		
		//Christmas
		Property idChristmasLeaves = CraftingPillars.config.getBlock("ChristmasLeaves.id", BlockIds.idChristmasLeaves);
		blockChristmasLeaves = (new ChristmasLeavesBlock(idChristmasLeaves.getInt(), Material.leaves)).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("xmasLeaves");
		if(!winter)
			blockChristmasLeaves.setCreativeTab(null);
		registerBlock(blockChristmasLeaves);
		LanguageRegistry.instance().addStringLocalization("tile.xmasLeaves.spruce.name", "en_US", "Christmas Leaves");
		LanguageRegistry.instance().addStringLocalization("tile.xmasLeaves.fostimber.name", "en_US", "Fostimber Christmas Leaves");

		Property idChristmasTreeSapling = CraftingPillars.config.getBlock("ChristmasTreeSapling.id", BlockIds.idChristmasTreeSapling);
		blockChristmasTreeSapling = (new ChristmasTreeSapling(idChristmasTreeSapling.getInt(), Material.plants)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("ChristmasTreeSapling");
		if(!winter)
			blockChristmasTreeSapling.setCreativeTab(null);
		registerBlock(blockChristmasTreeSapling);
		LanguageRegistry.instance().addStringLocalization(blockChristmasTreeSapling.getUnlocalizedName()+".name", "en_US", "Christmas Tree Sapling");

		Property idChristmasPresent = CraftingPillars.config.getBlock("ChristmasPresent.id", BlockIds.idChristmasPresent);
		blockChristmasPresent = (new ChristmasPresentBlock(idChristmasPresent.getInt(), Material.cloth)).setHardness(1.0F).setStepSound(Block.soundClothFootstep).setUnlocalizedName("PresentSide");
		if(!winter)
			blockChristmasPresent.setCreativeTab(null);
		registerBlock(blockChristmasPresent);
		LanguageRegistry.instance().addStringLocalization(blockChristmasPresent.getUnlocalizedName()+".name", "en_US", "Christmas Present");
		
		Property idChristmasLight = CraftingPillars.config.getBlock("ChristmasLight.id", BlockIds.idChristmasLight);
		blockChristmasLight = (new ChristmasLightBlock(idChristmasLight.getInt(), Material.glass)).setHardness(0.1F).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("christmas_light");
		if(!winter)
			blockChristmasLight.setCreativeTab(null);
		registerBlock(blockChristmasLight);
		LanguageRegistry.instance().addStringLocalization(blockChristmasLight.getUnlocalizedName()+".name", "en_US", "Christmas Light");
		
		GameRegistry.registerBlock(blockChristmasLeaves, ChristmasLeavesItemBlock.class, CraftingPillars.id);
		
		//Items
		Property idDiscElysium = CraftingPillars.config.getItem("DiscElysium.id", BlockIds.idDiscElysium);
		itemDiscElysium = new PillarRecord(idDiscElysium.getInt(), CraftingPillars.id + ":UranusParadiseShort").setUnlocalizedName("record").setTextureName(CraftingPillars.id + ":ElysiumDisk");
        LanguageRegistry.instance().addStringLocalization(CraftingPillars.id + ":UranusParadiseShort", "Elysium - Uranus Paradise Short");
        
		Property idAdventCalendar = CraftingPillars.config.getItem("AdventCalendar2013.id", BlockIds.idAdventCalendar2013);
		itemCalendar2013 = new AdventCalendar2013(idAdventCalendar.getInt()).setUnlocalizedName("AdventCalendar2013");
		if(!winter)
			itemCalendar2013.setCreativeTab(null);
		LanguageRegistry.instance().addStringLocalization(itemCalendar2013.getUnlocalizedName() + ".name", "Advent Calendar 2013");
		
		Property idWinterFood = CraftingPillars.config.getItem("idWinterFood.id", BlockIds.idWinterFood);
		itemWinterFood2013 = new WinterFood2013(idWinterFood.getInt(), 5, 0.5F).setUnlocalizedName("WinterFood");
		if(!winter)
			itemWinterFood2013.setCreativeTab(null);
		for(int i = 0; i < WinterFood2013.itemNames.length; i++)
			LanguageRegistry.instance().addStringLocalization(itemWinterFood2013.getUnlocalizedName() + "." + i + ".name", WinterFood2013.itemNames[i]);

		Property idRibbonDiamond = CraftingPillars.config.getItem("RibbonDiamond.id", BlockIds.idRibbonDiamond);
		itemRibbonDiamond = new BaseItem(idRibbonDiamond.getInt()).setUnlocalizedName("RibbonDiamond");
		LanguageRegistry.instance().addStringLocalization(itemRibbonDiamond.getUnlocalizedName() + ".name", "Ribbon Diamond");
		if(!winter)
			itemRibbonDiamond.setCreativeTab(null);
		Property idVirgacs = CraftingPillars.config.getItem("Virgacs.id", BlockIds.idVirgacs);
		itemVirgacs = new ItemVirgacs(idVirgacs.getInt()).setUnlocalizedName("Virgacs");
		LanguageRegistry.instance().addStringLocalization(itemVirgacs.getUnlocalizedName() + ".name", "Virgacs");
		if(!winter)
			itemVirgacs.setCreativeTab(null);
		Property idLoreBook = CraftingPillars.config.getItem("LoreBook.id", BlockIds.idLoreBook);
		itemElysiumLoreBook = new BookElysium(idLoreBook.getInt()).setUnlocalizedName("ElysiumLoreBook");
		LanguageRegistry.instance().addStringLocalization(itemElysiumLoreBook.getUnlocalizedName() + ".name", "Elysium Lore Book");
	}
	
	public void addAchievementsAndCreativeTab()
	{
		achievementGettingStarted = new Achievement(509, "gettingstarted", 0, 0, blockBasePillar, null).registerAchievement();
		
		achievementRecursion = new Achievement(510, "recursion", 1, 1, blockCraftingPillar, achievementGettingStarted).registerAchievement();
		achievementShowoff = new Achievement(511, "showoff", 3, 1, blockShowOffPillar, achievementRecursion).registerAchievement();
		achievementRecursion3 = new Achievement(518, "recursion3", 5, 1, blockChristmasPresent, achievementShowoff).registerAchievement();
		
		achievementCompressingLiquids = new Achievement(512, "liquids", 1, 2, blockFreezerPillar, achievementGettingStarted).registerAchievement();
		
		achievementChristmas = new Achievement(515, "christmaspillar", 0, 4, blockChristmasTreeSapling, null).setSpecial().setIndependent().registerAchievement();
		achievementDiamond = new Achievement(516, "christmasdiamond", 2, 4, itemRibbonDiamond, achievementChristmas).setSpecial().registerAchievement();
		achievementDisc = new Achievement(517, "elysiandisc", 4, 4, itemDiscElysium, achievementChristmas).setSpecial().registerAchievement();
		
		achievementPage = new AchievementPage(name,
				achievementGettingStarted,
				achievementRecursion,
				achievementShowoff,
				achievementCompressingLiquids,
				achievementRecursion3,
				achievementChristmas,
				achievementDiamond,
				achievementDisc
				);
		AchievementPage.registerAchievementPage(achievementPage);
		
		LanguageRegistry.instance().addStringLocalization("achievement.gettingstarted", "en_US", "Getting Started");
		LanguageRegistry.instance().addStringLocalization("achievement.gettingstarted.desc", "en_US", "Craft a BasicPillar");
		
		LanguageRegistry.instance().addStringLocalization("achievement.recursion", "en_US", "Recursion I");
		LanguageRegistry.instance().addStringLocalization("achievement.recursion.desc", "en_US", "Craft a CraftingPillar in a CraftingPillar!");
		
		LanguageRegistry.instance().addStringLocalization("achievement.showoff", "en_US", "Recursion II");
		LanguageRegistry.instance().addStringLocalization("achievement.showoff.desc", "en_US", "Show off your Showcase Pillar!");
		
		LanguageRegistry.instance().addStringLocalization("achievement.recursion3", "en_US", "Recursion III");
		LanguageRegistry.instance().addStringLocalization("achievement.recursion3.desc", "en_US", "Get a present from a present!");
		
		LanguageRegistry.instance().addStringLocalization("achievement.christmaspillar", "en_US", "Christmas Event");
		LanguageRegistry.instance().addStringLocalization("achievement.christmaspillar.desc", "en_US", "Celebrate Christmas with us!");
		
		LanguageRegistry.instance().addStringLocalization("achievement.christmasdiamond", "en_US", "Diamond Mania");
		LanguageRegistry.instance().addStringLocalization("achievement.christmasdiamond.desc", "en_US", "Unwrap a Diamond!");
		
		LanguageRegistry.instance().addStringLocalization("achievement.liquids", "en_US", "Liquid Tanks!");
		LanguageRegistry.instance().addStringLocalization("achievement.liquids.desc", "en_US", "Store liquids in an ancient way!");
		
		LanguageRegistry.instance().addStringLocalization("achievement.elysiandisc", "en_US", "Preparing with music!");
		LanguageRegistry.instance().addStringLocalization("achievement.elysiandisc.desc", "en_US", "Listen to the Elysium Theme disc!");
		
		
		
		LanguageRegistry.instance().addStringLocalization("itemGroup.CraftingPillars", "en_US", "Crafting Pillars");
	}
	
	public void registerTileEntities()
	{
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
		GameRegistry.registerTileEntity(TileEntitySentryPillar.class, "TileEntitySentryPillar");
		
		GameRegistry.registerTileEntity(TileEntityChristmasPresent.class, "TileEntityPresent");
		GameRegistry.registerTileEntity(TileEntityLight.class, "TileEntityLight");
	}
	
	public void addCrafting()
	{
		GameRegistry.registerCraftingHandler(new PillarCraftingHandler());
		
		GameRegistry.addRecipe(new ItemStack(blockBasePillar), new Object[] { "SSS", " S ", "SSS", Character.valueOf('S'), Block.stone });
		GameRegistry.addRecipe(new ItemStack(blockFreezerPillar), new Object[] { "SSS", "GPG", "SSS", Character.valueOf('S'), Block.blockSnow, Character.valueOf('P'), blockBasePillar, Character.valueOf('G'), Block.thinGlass});
		GameRegistry.addShapelessRecipe(new ItemStack(blockShowOffPillar), new ItemStack(Item.itemFrame), new ItemStack(blockBasePillar));
		GameRegistry.addShapelessRecipe(new ItemStack(blockCraftingPillar), new ItemStack(Block.workbench), new ItemStack(blockBasePillar));
		GameRegistry.addShapelessRecipe(new ItemStack(blockFurnacePillar), new ItemStack(Block.furnaceIdle), new ItemStack(blockBasePillar));
		GameRegistry.addShapelessRecipe(new ItemStack(blockBrewingPillar), new ItemStack(Item.brewingStand), new ItemStack(blockBasePillar));
		GameRegistry.addShapelessRecipe(new ItemStack(blockDiskPlayerPillar), new ItemStack(Block.jukebox), new ItemStack(blockBasePillar));
		GameRegistry.addShapelessRecipe(new ItemStack(blockSentryPillar), new ItemStack(Block.dispenser), new ItemStack(blockBasePillar));
		GameRegistry.addRecipe(new ItemStack(blockPotPillar), new Object[] { "S", "F", "P", Character.valueOf('S'), Block.dirt, Character.valueOf('P'), blockBasePillar , Character.valueOf('F'), Item.flowerPot});
		GameRegistry.addRecipe(new ItemStack(blockChristmasLight, 3), new Object[] { "G", "L", Character.valueOf('G'), Item.ingotGold, Character.valueOf('L'), Block.glowStone});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.diamond), new ItemStack(itemRibbonDiamond));
	}
	
	public void addToOreDictionary()
	{
		OreDictionary.registerOre("record", itemDiscElysium);
		OreDictionary.registerOre("treeSapling", blockChristmasTreeSapling);
		OreDictionary.registerOre("treeLeaves",  blockChristmasLeaves);
	}
	
	public void initAPI()
	{
		CraftingPillarAPI.addDiskTexture(itemDiscElysium.itemID, CraftingPillars.id + ":textures/models/disk_elysium.png");
		
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
		
		SentryBehaviors.add(Item.arrow.itemID, new SentryBehaviorArrow());
		SentryBehaviors.add(Item.snowball.itemID, new SentryBehaviorSnowball());
		SentryBehaviors.add(Item.fireballCharge.itemID, new SentryBehaviorFireball());
		SentryBehaviors.add(Item.potion.itemID, new SentryBehaviorPotion());
	}
	
	public void registerHandlers()
	{
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
        MinecraftForge.EVENT_BUS.register(new PillarEventHandler());
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt)
	{
		rand = new Random(System.currentTimeMillis());
		
		maxTreeState = 0;
		if(isAfter("2013-12-03"))
			maxTreeState++;
		if(isAfter("2013-12-10"))
			maxTreeState++;
		if(isAfter("2013-12-17"))
			maxTreeState++;
		if(isAfter("2013-12-24"))
			maxTreeState++;
		if(debug)
			maxTreeState = 4;
		
		try
		{
			config = new Configuration(new File(evt.getModConfigurationDirectory(), "CraftingPillars.cfg"));
			config.load();
			
			winter = isWinterTime() && config.get("default", "enableWinter", true).getBoolean(true);
			
			this.addItemsAndBlocks();
			this.addAchievementsAndCreativeTab();
			this.registerTileEntities();
			this.addCrafting();
			this.addToOreDictionary();
			this.initAPI();
			this.registerHandlers();
			checkUpdates = CraftingPillars.config.get("default", "checkUpdate", true, "set this to false if you only want to get notified for major version");

			proxy.init();
			ChristmasPresentBlock.init();
			ContainerAdventCalendar2013.init();
			
			config.save();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent evt){
    	modForestry = Loader.isModLoaded("Forestry");
    	modElysium = Loader.isModLoaded("elysium");
    	modThaumcraft = Loader.isModLoaded("Thaumcraft");
    	
    	if(modThaumcraft)
    	{
    		System.out.println("Loading Thaumcraft 4 wand...");
    		itemWandThaumcraft = ItemApi.getItem("itemWandCasting", 0);
    		if(itemWandThaumcraft == null){
    			modThaumcraft = false;
    			System.out.println("Thaumcraft compatibility disabled...");

    		}
    	}
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
			return c.after(v);
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
		if(debug)
			return 24;
		if(c.get(Calendar.YEAR) == year && c.get(Calendar.MONTH) == Calendar.DECEMBER/* && c.get(Calendar.DAY_OF_MONTH) <= 24*/)
			return c.get(Calendar.DAY_OF_MONTH);
		else
			return 0;
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
