package me.dawars.CraftingPillars.api.sentry;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IBehaviorSentryItem
{
    /**
     * Dispenses the specified ItemStack from a Sentry.
     * @param entityPlayer 
     * @return - returns the ammo remaining in the Sentry
     */
    ItemStack dispense(IBlockSource iblocksource, EntityMob target, EntityPlayer entityPlayer, ItemStack itemstack);
}
