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
	public class CollisionBox
	{
		public int id, slot;
		public float minX, minY, minZ, maxX, maxY, maxZ;
		
		public CollisionBox(int slot, float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
		{
			this.id = buttons.size();
			buttons.add(this);
			this.slot = slot;
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
	}
	
	public abstract void onActionPerformed(World world, int x, int y, int z, int id, int button, EntityPlayer player);
	
	@SideOnly(Side.CLIENT)
	public abstract boolean canActionPerformed(World world, int x, int y, int z, int id, int button, EntityPlayer player);
	
	public void onSlotClicked(World world, int x, int y, int z, int slot, int button, EntityPlayer player)
	{
		BaseTileEntityPillar tile = (BaseTileEntityPillar)world.getBlockTileEntity(x, y, z);
		if(button == 0)
		{
			if(player.isSneaking())
				tile.dropItemFromSlot(slot, tile.getInventoryStackLimit(), player);
			else
				tile.dropItemFromSlot(slot, 1, player);
		}
		else if(button == 2)
		{
			if(player.isSneaking())
			{
				int i;
				for(i = player.getCurrentEquippedItem().stackSize; !tile.insertStack(slot, new ItemStack(player.getCurrentEquippedItem().getItem(), i), 0); i--);
				if(!player.capabilities.isCreativeMode)
					player.getCurrentEquippedItem().stackSize -= i;
			}
			else
			{
				if(!player.capabilities.isCreativeMode)
					player.getCurrentEquippedItem().stackSize--;
				tile.insertStack(slot, new ItemStack(player.getCurrentEquippedItem().getItem(), 1), 0);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public boolean canSlotClicked(World world, int x, int y, int z, int slot, int button, EntityPlayer player)
	{
		BaseTileEntityPillar tile = (BaseTileEntityPillar)world.getBlockTileEntity(x, y, z);
		if(button == 0)
		{
			return tile.getStackInSlot(slot) != null;
		}
		else if(button == 2 && player.getCurrentEquippedItem() != null)
		{
			if(player.isSneaking())
				return tile.canInsertItem(slot, player.getCurrentEquippedItem(), 0);
			else
				return tile.canInsertItem(slot, new ItemStack(player.getCurrentEquippedItem().getItem(), 1), 0);
		}
		
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public int getClickedButtonId(World world, int x, int y, int z, int button, EntityPlayer player)
	{
		float hitX = (float)player.posX, hitY = (float)player.posY+(float)player.eyeHeight-2F/16F, hitZ = (float)player.posZ;
		float dx = MathHelper.sin((float)Math.toRadians(-player.rotationYaw))*MathHelper.cos((float)Math.toRadians(-player.rotationPitch))/16F;
		float dy = MathHelper.sin((float)Math.toRadians(-player.rotationPitch))/16F;
		float dz = MathHelper.cos((float)Math.toRadians(-player.rotationYaw))*MathHelper.cos((float)Math.toRadians(-player.rotationPitch))/16F;
		
		boolean flag = false;
		int meta = world.getBlockMetadata(x, y, z);
		
		while(((hitX-player.posX)*(hitX-player.posX) + (hitY-player.posY)*(hitY-player.posY) + (hitZ-player.posZ)*(hitZ-player.posZ) < 25F) && !flag)
		{
			hitX += dx;
			hitY += dy;
			hitZ += dz;
			for(CollisionBox box : this.buttons)
				if(box.inBounds(hitX-x, hitY-y, hitZ-z, meta))
					if((box.slot > -1 && this.canSlotClicked(world, x, y, z, box.slot, button, player)) || this.canActionPerformed(world, x, y, z, box.id, button, player))
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
