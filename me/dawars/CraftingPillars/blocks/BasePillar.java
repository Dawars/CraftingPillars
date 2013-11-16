package me.dawars.CraftingPillars.blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.tiles.TileEntityAnvilPillar;
import me.dawars.CraftingPillars.tiles.BaseTileEntityPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import static org.lwjgl.opengl.GL11.*;

public abstract class BasePillar extends BaseBlockContainer
{
	public static class CollisionBox
	{
		public int id;
		public float minX, minY, minZ, maxX, maxY, maxZ;
		
		public CollisionBox(int id, float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
		{
			this.id = id;
			this.minX = minX/16F;
			this.minY = minY/16F;
			this.minZ = minZ/16F;
			this.maxX = maxX/16F;
			this.maxY = maxY/16F;
			this.maxZ = maxZ/16F;
		}
		
		public boolean inBounds(float x, float y, float z)
		{
			return this.minX<=x && x<=this.maxX && this.minY<=y && y<=this.maxY && this.minZ<=z && z<=this.maxZ;
		}
		
		public boolean inBounds(float x, float y, float z, int meta)
		{
			if(meta == 0)
			{
				x = 1F-x;
				z = 1F-z;
			}
			else if(meta == 1)
			{
				float s = x;
				x = 1F-z;
				z = s;
			}
			/*else if(meta == 2)
			{
				x = x;
				z = z;
			}*/
			else if(meta == 3)
			{
				float s = x;
				x = z;
				z = 1F-s;
			}
			
			return this.inBounds(x, y, z);
		}
		
		public void render()
		{
			glBegin(GL_QUADS);
			
			glNormal3f(1F, 0F, 0F);
			glVertex3f(this.maxX, this.maxY, this.maxZ);
			glVertex3f(this.maxX, this.minY, this.maxZ);
			glVertex3f(this.maxX, this.minY, this.minZ);
			glVertex3f(this.maxX, this.maxY, this.minZ);
			
			glNormal3f(-1F, 0F, 0F);
			glVertex3f(this.minX, this.maxY, this.minZ);
			glVertex3f(this.minX, this.minY, this.minZ);
			glVertex3f(this.minX, this.minY, this.maxZ);
			glVertex3f(this.minX, this.maxY, this.maxZ);
			
			glNormal3f(0F, 1F, 0F);
			glVertex3f(this.minX, this.maxY, this.minZ);
			glVertex3f(this.minX, this.maxY, this.maxZ);
			glVertex3f(this.maxX, this.maxY, this.maxZ);
			glVertex3f(this.maxX, this.maxY, this.minZ);
			
			glNormal3f(0F, -1F, 0F);
			glVertex3f(this.maxX, this.minY, this.minZ);
			glVertex3f(this.maxX, this.minY, this.maxZ);
			glVertex3f(this.minX, this.minY, this.maxZ);
			glVertex3f(this.minX, this.minY, this.minZ);
			
			glNormal3f(0F, 0F, 1F);
			glVertex3f(this.minX, this.maxY, this.maxZ);
			glVertex3f(this.minX, this.minY, this.maxZ);
			glVertex3f(this.maxX, this.minY, this.maxZ);
			glVertex3f(this.maxX, this.maxY, this.maxZ);
			
			glNormal3f(0F, 0F, -1F);
			glVertex3f(this.maxX, this.maxY, this.minZ);
			glVertex3f(this.maxX, this.minY, this.minZ);
			glVertex3f(this.minX, this.minY, this.minZ);
			glVertex3f(this.minX, this.maxY, this.minZ);
			
			glEnd();
		}
	}
	
	public List<CollisionBox> buttons = new ArrayList<CollisionBox>();
	
	public BasePillar(int id, Material mat)
	{
		super(id, mat);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	public abstract void onActionPerformed(World world, int x, int y, int z, int id, int button, EntityPlayer player);
	
	@SideOnly(Side.CLIENT)
	public abstract boolean canPerformAction(World world, int x, int y, int z, int id, int button, EntityPlayer player);
	
	@SideOnly(Side.CLIENT)
	public int getClickedButtonId(int x, int y, int z, int button, EntityPlayer player)
	{
		float hitX = (float)player.posX, hitY = (float)player.posY+(float)player.eyeHeight, hitZ = (float)player.posZ;
		float dx = MathHelper.sin((float)Math.toRadians(-player.rotationYaw))*MathHelper.cos((float)Math.toRadians(-player.rotationPitch))/16F;
		float dy = MathHelper.sin((float)Math.toRadians(-player.rotationPitch))/16F;
		float dz = MathHelper.cos((float)Math.toRadians(-player.rotationYaw))*MathHelper.cos((float)Math.toRadians(-player.rotationPitch))/16F;
		
		boolean flag = false;
		int meta = player.worldObj.getBlockMetadata(x, y, z);
		
		while(((hitX-player.posX)*(hitX-player.posX) + (hitY-player.posY)*(hitY-player.posY) + (hitZ-player.posZ)*(hitZ-player.posZ) < 25F) && !flag)
		{
			hitX += dx;
			hitY += dy;
			hitZ += dz;
			for(CollisionBox box : this.buttons)
				if(box.inBounds(hitX-x, hitY-y, hitZ-z, meta) && this.canPerformAction(player.worldObj, x, y, z, box.id, button, player))
					return box.id;
		}
		
		return -1;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int meta = (int)Math.floor(entity.rotationYaw/90F + 0.5F) & 3;
		System.out.println("Placed pillar with orientation: "+meta);
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
