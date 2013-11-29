package me.dawars.CraftingPillars.items;

import java.util.List;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.client.gui.GuiIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BookElysium extends BaseItem
{
	public BookElysium(int id)
	{
		super(id);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.openGui(CraftingPillars.getInstance(), GuiIds.COMING_SOON, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		return stack;
	}
	
	/*public static boolean validBookTagContents(NBTTagCompound nbt)
	{
		if(!ItemWritableBook.validBookTagPages(nbt))
		{
			return false;
		}
		else if(!nbt.hasKey("title"))
		{
			return false;
		}
		else
		{
			String s = nbt.getString("title");
			return s != null && s.length() <= 16 ? nbt.hasKey("author") : false;
		}
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound nbttagcompound = stack.getTagCompound();
			NBTTagString nbttagstring = (NBTTagString)nbttagcompound.getTag("title");

			if(nbttagstring != null)
			{
				return nbttagstring.toString();
			}
		}

		return super.getItemDisplayName(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound nbttagcompound = stack.getTagCompound();
			NBTTagString nbttagstring = (NBTTagString)nbttagcompound.getTag("author");

			if(nbttagstring != null)
			{
				list.add(EnumChatFormatting.GRAY + String.format(StatCollector.translateToLocalFormatted("book.byAuthor", new Object[] {nbttagstring.data}), new Object[0]));
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.displayGUIBook(stack);
		return stack;
	}
	
	@Override
	public boolean getShareTag()
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}*/
}
