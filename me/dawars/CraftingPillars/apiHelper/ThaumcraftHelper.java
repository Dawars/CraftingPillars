package me.dawars.CraftingPillars.apiHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;

public class ThaumcraftHelper {

	static Method consumAllVisCrafting;
	public static boolean consumAllVisCrafting(ItemStack is, EntityPlayer player, AspectList aspects, boolean doit) {
		boolean ot = false;
	    try {
	        if(consumAllVisCrafting == null) {
	            Class fake = Class.forName("thaumcraft.common.items.wands.ItemWandCasting");
	            consumAllVisCrafting = fake.getMethod("consumAllVisCrafting", ItemStack.class, EntityPlayer.class, AspectList.class, boolean.class);
	        }
	        ot = (Boolean) consumAllVisCrafting.invoke(is, player, aspects, doit);
	    } catch(Exception ex) { 
	    	FMLLog.warning("[CraftingPillars] Could not invoke thaumcraft.common.items.wands.ItemWandCasting method consumAllVisCrafting");
	    }
		return ot;
	}

	public static ItemStack findMatchingArcaneRecipe(IInventory inv, EntityPlayer player) {
		
		int i = 0;
	    ItemStack itemstack = null;
	    ItemStack itemstack1 = null;

		for (int j = 0; j < 9; j++) {
			ItemStack itemstack2 = inv.getStackInSlot(j);

			if (itemstack2 != null) {
				if (i == 0) {
					itemstack = itemstack2;
				}
				if (i == 1) {
					itemstack1 = itemstack2;
				}
				i++;
			}
		}
		
		IArcaneRecipe recipe = null;
		for (Iterator i$ = ThaumcraftApi.getCraftingRecipes().iterator(); i$.hasNext();) {
			Object currentRecipe = i$.next();
			if (((currentRecipe instanceof IArcaneRecipe)) && matches(inv, player.worldObj, player, ((IArcaneRecipe) currentRecipe))) {
				recipe = (IArcaneRecipe) currentRecipe;
				break;
			}
		}
		if(recipe != null)
		{
			FMLLog.warning(recipe.getRecipeOutput().getDisplayName());
		}
		return recipe == null ? null : recipe.getCraftingResult(inv);
	}

	public static AspectList findMatchingArcaneRecipeAspects(IInventory inv, EntityPlayer player) {
		
		int i = 0;
	    ItemStack itemstack = null;
	    ItemStack itemstack1 = null;

		for (int j = 0; j < 9; j++) {
			ItemStack itemstack2 = inv.getStackInSlot(j);

			if (itemstack2 != null) {
				if (i == 0) {
					itemstack = itemstack2;
				}
				if (i == 1) {
					itemstack1 = itemstack2;
				}
				i++;
			}
		}
		
		IArcaneRecipe recipe = null;
		for (Iterator iterator = ThaumcraftApi.getCraftingRecipes().iterator(); iterator.hasNext();) {
			Object currentRecipe = iterator.next();
			if (((currentRecipe instanceof IArcaneRecipe)) && matches(inv, player.worldObj, player, (((IArcaneRecipe) currentRecipe)))) {
				recipe = (IArcaneRecipe) currentRecipe;

				break;
			}
		}

		return recipe == null ? new AspectList() : recipe.getAspects();
	}

	public static boolean matches(IInventory inv, World world, EntityPlayer player, IArcaneRecipe recipe) {
		//		if (recipe.getResearch().length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.username, recipe.getResearch())) {
		//			return false;
		//		}

		if (recipe instanceof ShapedArcaneRecipe) {
			ShapedArcaneRecipe arcaneRecipe = (ShapedArcaneRecipe) recipe;
			for (int x = 0; x <= 3 - arcaneRecipe.width; x++) {
				for (int y = 0; y <= 3 - arcaneRecipe.height; ++y) {
					if (checkMatchShaped(inv, x, y, arcaneRecipe)) {
						return true;
					}
				}
			}
		} else if (recipe instanceof ShapelessArcaneRecipe) {
			ShapelessArcaneRecipe arcaneRecipe = (ShapelessArcaneRecipe) recipe;
			//			if (arcaneRecipe.getResearch().length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.username, arcaneRecipe.getResearch())) {
			//				return false;
			//			}

			ArrayList required = new ArrayList(arcaneRecipe.getInput());

			for (int x = 0; x < 9; x++) {
				ItemStack slot = inv.getStackInSlot(x);

				if (slot != null) {
					boolean inRecipe = false;
					Iterator req = required.iterator();

					while (req.hasNext()) {
						boolean match = false;

						Object next = req.next();

						if (next instanceof ItemStack) {
							match = checkItemEquals((ItemStack) next, slot);
						} else if (next instanceof ArrayList) {
							for (ItemStack item : (ArrayList<ItemStack>) next) {
								match = match || checkItemEquals(item, slot);
							}
						}

						if (match) {
							inRecipe = true;
							required.remove(next);
							break;
						}
					}

					if (!inRecipe) {
						return false;
					}
				}
			}

			return required.isEmpty();
		}

		return false;
	}

	private static boolean checkMatchShaped(IInventory inv, int startX, int startY, ShapedArcaneRecipe recipe) {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				int subX = x - startX;
				int subY = y - startY;
				Object target = null;

				if (subX >= 0 && subY >= 0 && subX < recipe.width
						&& subY < recipe.height) {
					target = recipe.getInput()[subX + subY * recipe.width];
				}

				ItemStack slot = getStackInRowAndColumn(inv, x, y);

				if (target instanceof ItemStack) {
					if (!checkItemEquals((ItemStack) target, slot)) {
						return false;
					}
				} else if (target instanceof ArrayList) {
					boolean matched = false;

					for (ItemStack item : (ArrayList<ItemStack>) target) {
						matched = matched || checkItemEquals(item, slot);
					}

					if (!matched) {
						return false;
					}
				} else if (target == null && slot != null) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean checkItemEquals(ItemStack target, ItemStack input) {
		if (input == null && target != null || input != null && target == null) {
			return false;
		}
		return (target.itemID == input.itemID
				&& (!target.hasTagCompound() || ItemStack
						.areItemStackTagsEqual(target, input)) && (target
								.getItemDamage() == OreDictionary.WILDCARD_VALUE || target
								.getItemDamage() == input.getItemDamage()));
	}

	public static ItemStack getStackInRowAndColumn(IInventory inv, int par1, int par2) {
		if ((par1 >= 0) && (par1 < 3)) {
			int slot = par1 + par2 * 3;
			return par1 >= inv.getSizeInventory() ? null : inv.getStackInSlot(slot);
		}

		return null;
	}
}
