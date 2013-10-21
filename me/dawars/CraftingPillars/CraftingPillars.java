package me.dawars.CraftingPillars;

import java.io.File;

import me.dawars.CraftingPillars.blocks.CraftingPillarBlock;
import me.dawars.CraftingPillars.blocks.FurnacePillarBlock;
import me.dawars.CraftingPillars.proxy.CommonProxy;
import me.dawars.CraftingPillars.tile.TileEntityCraftingPillar;
import me.dawars.CraftingPillars.tile.TileEntityFurnacePillar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
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
	
	public static final String version = "1.2";
	public static final String name = "Crafting Pillars";
	public static final String id = "craftingpillars";
	
	@SidedProxy(clientSide = "me.dawars.CraftingPillars.proxy.ClientProxy", serverSide = "me.dawars.CraftingPillars.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Configuration config;
	
	public static int craftingPillarRenderID;
	public static int furnacePillarRenderID;
	
	public static Block blockCraftingPillar;
	public static Block blockFurnaceCraftingPillar;
	
	@EventHandler
	public void load(FMLPreInitializationEvent evt)
	{
		config = new Configuration(new File(evt.getModConfigurationDirectory(), "CraftingPillars.cfg"));
		try
		{
			config.load();
			// Block Registering
			Property idCraftingPillar = CraftingPillars.config.getBlock("CraftingPillar.id", BlockIds.idCraftingPillar);
			blockCraftingPillar = (new CraftingPillarBlock(idCraftingPillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("fancy_workbench_side");
			registerBlock(blockCraftingPillar, "Crafting Pillar");
			
			Property idFurnacePillar = CraftingPillars.config.getBlock("FurnacePillar.id", BlockIds.idFurnacePillar);
			blockFurnaceCraftingPillar = (new FurnacePillarBlock(idFurnacePillar.getInt(), Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("furnace_pillar_side");
			registerBlock(blockFurnaceCraftingPillar, "Furnace Pillar");
			
			GameRegistry.registerTileEntity(TileEntityCraftingPillar.class, "TileEntityCraftingPillar");
			GameRegistry.registerTileEntity(TileEntityFurnacePillar.class, "TileEntityFurnacePillar");
			
			proxy.registerRenderers();
			
		}
		finally
		{
			config.save();
		}
		GameRegistry.addRecipe(new ItemStack(blockCraftingPillar), new Object[] { "SSS", " C ", "SSS", Character.valueOf('S'), Block.cobblestone, Character.valueOf('C'), Block.workbench });
	}
	
	public static void registerBlock(Block block, String name)
	{
		GameRegistry.registerBlock(block, CraftingPillars.id + ":" + block.getUnlocalizedName().substring(5));
		LanguageRegistry.addName(block, name);
	}
}
