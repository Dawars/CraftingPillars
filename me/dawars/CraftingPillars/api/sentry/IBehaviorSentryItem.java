package me.dawars.CraftingPillars.api.sentry;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;

public interface IBehaviorSentryItem
{
    /**
     * Dispenses the specified ItemStack from a Sentry.
     */
    ItemStack dispense(IBlockSource iblocksource, EntityMob target, ItemStack itemstack);
}
