package me.dawars.CraftingPillars.blocks;

import static net.minecraftforge.common.EnumPlantType.Cave;
import static net.minecraftforge.common.EnumPlantType.Crop;
import static net.minecraftforge.common.EnumPlantType.Desert;
import static net.minecraftforge.common.EnumPlantType.Nether;
import static net.minecraftforge.common.EnumPlantType.Plains;
import static net.minecraftforge.common.EnumPlantType.Water;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class BaseFlowerBlock extends BlockFlower implements IPlantable
{
	
	public BaseFlowerBlock(int id, Material mat)
	{
		super(id, mat);
		this.setCreativeTab(CraftingPillars.tabPillar);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister itemIcon)
	{
		this.blockIcon = itemIcon.registerIcon(CraftingPillars.id + ":" + getUnlocalizedName().substring(5));
	}
	
	@SideOnly(Side.CLIENT)
	public boolean canThisPlantGrowOnThisBlockID(int id)
    {
        return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.tilledField.blockID;
    }
}
