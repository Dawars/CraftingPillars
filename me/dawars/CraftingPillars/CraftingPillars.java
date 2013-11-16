package me.dawars.CraftingPillars;

import java.io.File;

import me.dawars.CraftingPillars.blocks.*;
import me.dawars.CraftingPillars.handlers.*;
import me.dawars.CraftingPillars.tiles.*;
import me.dawars.CraftingPillars.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(name = CraftingPillars.name, version = CraftingPillars.version, useMetadata = false, modid = CraftingPillars.id, dependencies = "required-after:Forge@[8.9.0,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {CraftingPillars.packetChannel}, packetHandler = PacketHandler.class)
public class CraftingPillars
{
	@Instance(CraftingPillars.id)
	private static CraftingPillars instance;
	
	public static CraftingPillars getInstance()
	{
		return instance;
	}
	
	public static final String version = "1.3.2";
	public static final String name = "Crafting Pillars";
	public static final String id = "craftingpillars";
	public static final String packetChannel = "PillarChannel";
	
	@SidedProxy(clientSide = "me.dawars.CraftingPillars.proxy.ClientProxy", serverSide = "me.dawars.CraftingPillars.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Configuration config;
	
	public static int extendPillarRenderID;
	public static int showOffPillarRenderID;
	public static int craftingPillarRenderID;
	public static int furnacePillarRenderID;
	public static int anvilPillarRenderID;
	public static int tankPillarRenderID;
	public static int brewingillarRenderID;
	public static int diskPlayerRenderID;

	public static Block blockExtendPillar;
	public static Block blockShowOffPillar;
	public static Block blockCraftingPillar;
	public static Block blockFurnacePillar;
	public static Block blockAnvilPillar;
	public static Block blockTankPillar;
	public static Block blockBrewingPillar;
	public static Block blockDiskPlayerPillar;

	public static boolean floatingItems = true;
	
	public static final Achievement achievementGettingStarted = new Achievement(509, "gettingstarted", -2, 0, /* blockCraftingPillar */Block.stoneBrick, AchievementList.openInventory).registerAchievement();
	public static final Achievement achievementRecursion = new Achievement(510, "recursion", -3, -2, /* blockCraftingPillar */Item.redstone, achievementGettingStarted).registerAchievement();
	public static final Achievement achievementShowoff = new Achievement(511, "showoff", -5, -2, /* blockCraftingPillar */Item.diamond, achievementRecursion).registerAchievement();
	
	@EventHandler
	public void load(FMLPreInitializationEvent evt)
	{
		if(FMLCommonHandler.instance().getSide().isClient())
			VersionChecker.check();
		
		config = new Configuration(new File(evt.getModConfigurationDirectory(), "CraftingPillars.cfg"));
		try
		{
			config.load();
			// Block Registering
			Property idExtendPillar = CraftingPillars.config.getBlock("ExtendPillar.id", BlockIds.idExtendPillar);
			blockExtendPillar = (new ExtendPillarBlock(idExtendPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("extendPillar");
			registerBlock(blockExtendPillar, "Basic Pillar");
			
			Property idShowOffPillar = CraftingPillars.config.getBlock("ShowOffPillar.id", BlockIds.idShowOffPillar);
			blockShowOffPillar = (new ShowOffPillarBlock(idShowOffPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("showOffPillar");
			registerBlock(blockShowOffPillar, "Show-Off Pillar");
			
			Property idCraftingPillar = CraftingPillars.config.getBlock("CraftingPillar.id", BlockIds.idCraftingPillar);
			blockCraftingPillar = (new CraftingPillarBlock(idCraftingPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("craftingPillar");
			registerBlock(blockCraftingPillar, "Crafting Pillar");
			
			Property idFurnacePillar = CraftingPillars.config.getBlock("FurnacePillar.id", BlockIds.idFurnacePillar);
			blockFurnacePillar = (new FurnacePillarBlock(idFurnacePillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("furnacePillar");
			registerBlock(blockFurnacePillar, "Furnace Pillar");
			
			Property idAnvilPillar = CraftingPillars.config.getBlock("AnvilPillar.id", BlockIds.idAnvilPillar, "Coming soon...");
			blockAnvilPillar = (new AnvilPillar(idAnvilPillar.getInt(), Material.anvil)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("anvilPillar");
			registerBlock(blockAnvilPillar, "Anvil Pillar");

			Property idTankPillar = CraftingPillars.config.getBlock("TankPillar.id", BlockIds.idTankPillar, "Coming soon...");
			blockTankPillar = (new TankPillarBlock(idTankPillar.getInt(), Material.glass)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("tankPillar");
			registerBlock(blockTankPillar, "Tank Pillar");
			
			Property idBrewingPillar = CraftingPillars.config.getBlock("BrewingPillar.id", BlockIds.idBrewingPillar);
			blockBrewingPillar = (new BrewingPillarBlock(idBrewingPillar.getInt(), Material.iron)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("brewingPillar");
			registerBlock(blockBrewingPillar, "Brewing Pillar");
			
			//add time check for visiblity
			Property idDiskPlayerPillar = CraftingPillars.config.getBlock("DiskPlayerPillar.id", BlockIds.idDiskPillar);
			blockDiskPlayerPillar = (new DiskPlayerPillarBlock(idDiskPlayerPillar.getInt(), Material.iron)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("diskPillar");
			registerBlock(blockDiskPlayerPillar, "JukePillar");
			
			GameRegistry.registerTileEntity(TileEntityExtendPillar.class, "TileEntityExtendPillar");
			GameRegistry.registerTileEntity(TileEntityShowOffPillar.class, "TileEntityShowOffPillar");
			GameRegistry.registerTileEntity(TileEntityCraftingPillar.class, "TileEntityCraftingPillar");
			GameRegistry.registerTileEntity(TileEntityFurnacePillar.class, "TileEntityFurnacePillar");
			GameRegistry.registerTileEntity(TileEntityAnvilPillar.class, "TileEntityAnvilPillar");
			GameRegistry.registerTileEntity(TileEntityTankPillar.class, "TileEntityTankPillar");
			GameRegistry.registerTileEntity(TileEntityEnchantmentPillar.class, "TileEntityEnchantmentPillar");
			GameRegistry.registerTileEntity(TileEntityBrewingPillar.class, "TileEntityBrewingPillar");
			GameRegistry.registerTileEntity(TileEntityDiskPlayerPillar.class, "TileEntityDiskPlayerPillar");

			LanguageRegistry.instance().addStringLocalization("achievement.gettingstarted", "Getting Started");
			LanguageRegistry.instance().addStringLocalization("achievement.gettingstarted.desc", "Craft a BasicPillar");

			LanguageRegistry.instance().addStringLocalization("achievement.recursion", "Recursion I");
			LanguageRegistry.instance().addStringLocalization("achievement.recursion.desc", "Craft a CraftingPillar in a CraftingPillar");
			
			LanguageRegistry.instance().addStringLocalization("achievement.showoff", "Recursion II");
			LanguageRegistry.instance().addStringLocalization("achievement.showoff.desc", "Show off your Show-Off Pillar!");
			
			proxy.init();
			
			GameRegistry.addRecipe(new ItemStack(blockExtendPillar), new Object[] { "SSS", " S ", "SSS", Character.valueOf('S'), Block.stone });
			GameRegistry.addShapelessRecipe(new ItemStack(blockShowOffPillar), new ItemStack(Item.itemFrame), new ItemStack(blockExtendPillar));
			GameRegistry.addShapelessRecipe(new ItemStack(blockCraftingPillar), new ItemStack(Block.workbench), new ItemStack(blockExtendPillar));
			GameRegistry.addShapelessRecipe(new ItemStack(blockFurnacePillar), new ItemStack(Block.furnaceIdle), new ItemStack(blockExtendPillar));
			GameRegistry.addShapelessRecipe(new ItemStack(blockBrewingPillar), new ItemStack(Item.brewingStand), new ItemStack(blockExtendPillar));
			
			MinecraftForge.EVENT_BUS.register(new me.dawars.CraftingPillars.handlers.EventHandler());
			GameRegistry.registerCraftingHandler(new CraftingHandler());
		}
		finally
		{
			config.save();
		}
	}
	
	public static void registerBlock(Block block, String name)
	{
		GameRegistry.registerBlock(block, CraftingPillars.id + ":" + block.getUnlocalizedName().substring(5));
		LanguageRegistry.addName(block, name);
	}
}
