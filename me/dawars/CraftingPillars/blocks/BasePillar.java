package me.dawars.CraftingPillars.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public abstract class BasePillar extends BaseBlockContainer
{
	public BasePillar(int id, Material mat)
	{
		super(id, mat);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	public abstract boolean handleClick(int button, World world, int x, int y, int z, EntityPlayer player, float hitX, float hitY, float hitZ);
	
	public boolean handleClickEvent(int button, int x, int y, int z, EntityPlayer player)
	{
		System.out.println("Handling event at: "+x+" "+y+" "+z);
		return this.handleClick(button, player.worldObj, x, y, z, player, 0F, 0F, 0F);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
			return true;
		
		hitX *= 16F;
		hitY *= 16F;
		hitZ *= 16F;
		
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0)
		{
			hitX = 16F - hitX;
			hitZ = 16F - hitZ;
		}
		if(meta == 1)
		{
			float s = hitX;
			hitX = 16F - hitZ;
			hitZ = s;
		}
		//else if(meta == 2) { hitX = hitX; hitZ = hitZ; }
		else if(meta == 3)
		{
			float s = hitX;
			hitX = hitZ;
			hitZ = 16F - s;
		}
		
		this.handleClick(2, world, x, y, z, player, hitX, hitY, hitZ);
		
		return true;
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		if(!world.isRemote)
			this.handleClick(0, world, x, y, z, player, 0F, 0F, 0F);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int meta = (int)Math.floor(entity.rotationYaw/90F + 0.5F) & 3;
		world.setBlockMetadataWithNotify(x, y, z, meta, 0);
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister itemIcon)
	{
		this.blockIcon = itemIcon.registerIcon(CraftingPillars.id + ":craftingPillar_side");
	}
}
