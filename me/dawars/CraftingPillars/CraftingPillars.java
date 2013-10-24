package me.dawars.CraftingPillars;

import java.io.File;

import me.dawars.CraftingPillars.blocks.AnvilPillarBlock;
import me.dawars.CraftingPillars.blocks.CraftingPillarBlock;
import me.dawars.CraftingPillars.blocks.FurnacePillarBlock;
import me.dawars.CraftingPillars.proxy.CommonProxy;
import me.dawars.CraftingPillars.tile.TileEntityAnvilPillar;
import me.dawars.CraftingPillars.tile.TileEntityCraftingPillar;
import me.dawars.CraftingPillars.tile.TileEntityFurnacePillar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(name = CraftingPillars.name, version = CraftingPillars.version, useMetadata = false, modid = CraftingPillars.id, dependencies = "required-after:Forge@[8.9.0,)")
public class CraftingPillars
{
	@Instance(CraftingPillars.id)
	private static CraftingPillars instance;
	
	public static CraftingPillars getInstance()
	{
		return instance;
	}
	
	public static final String version = "1.3";
	public static final String name = "Crafting Pillars";
	public static final String id = "craftingpillars";
	
	@SidedProxy(clientSide = "me.dawars.CraftingPillars.proxy.ClientProxy", serverSide = "me.dawars.CraftingPillars.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Configuration config;
	
	public static int craftingPillarRenderID;
	public static int furnacePillarRenderID;
	public static int anvilPillarRenderID;
	
	public static Block blockCraftingPillar;
	public static Block blockFurnacePillar;
	public static Block blockAnvilPillar;
	
	public static final Achievement achievementRecursion = new Achievement(510, "recursion", -2, 0, /* blockCraftingPillar */Block.dirt, null).registerAchievement();
	
	@EventHandler
	public void load(FMLPreInitializationEvent evt)
	{
		config = new Configuration(new File(evt.getModConfigurationDirectory(), "CraftingPillars.cfg"));
		try
		{
			config.load();
			// Block Registering
			Property idCraftingPillar = CraftingPillars.config.getBlock("CraftingPillar.id", BlockIds.idCraftingPillar);
			blockCraftingPillar = (new CraftingPillarBlock(idCraftingPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("craftingPillar");
			registerBlock(blockCraftingPillar, "Crafting Pillar");
			
			Property idFurnacePillar = CraftingPillars.config.getBlock("FurnacePillar.id", BlockIds.idFurnacePillar);
			blockFurnacePillar = (new FurnacePillarBlock(idFurnacePillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("furnacePillar");
			registerBlock(blockFurnacePillar, "Furnace Pillar");
			
			Property idAnvilPillar = CraftingPillars.config.getBlock("AnvilPillar.id", BlockIds.idAnvilPillar);
			blockAnvilPillar = (new AnvilPillarBlock(idAnvilPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("anvilPillar");
			registerBlock(blockAnvilPillar, "Anvil Pillar");
			
			GameRegistry.registerTileEntity(TileEntityCraftingPillar.class, "TileEntityCraftingPillar");
			GameRegistry.registerTileEntity(TileEntityFurnacePillar.class, "TileEntityFurnacePillar");
			GameRegistry.registerTileEntity(TileEntityAnvilPillar.class, "TileEntityAnvilPillar");
			
			LanguageRegistry.instance().addStringLocalization("achievement.recursion", "Recursion!");
			LanguageRegistry.instance().addStringLocalization("achievement.recursion.desc", "Craft a CraftingPillar in a CraftingPillar");
			
			proxy.registerRenderers();
			
		}
		finally
		{
			config.save();
		}
		GameRegistry.addRecipe(new ItemStack(blockCraftingPillar), new Object[] { "SSS", " C ", "SSS", Character.valueOf('S'), Block.cobblestone, Character.valueOf('C'), Block.workbench });
		GameRegistry.addRecipe(new ItemStack(blockFurnacePillar), new Object[] { "SSS", " F ", "SSS", Character.valueOf('S'), Block.stone, Character.valueOf('F'), Block.furnaceIdle });
	}
	
	public static void registerBlock(Block block, String name)
	{
		GameRegistry.registerBlock(block, CraftingPillars.id + ":" + block.getUnlocalizedName().substring(5));
		LanguageRegistry.addName(block, name);
	}
}
