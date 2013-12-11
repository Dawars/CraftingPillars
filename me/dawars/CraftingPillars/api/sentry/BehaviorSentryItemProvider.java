package me.dawars.CraftingPillars.api.sentry;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;

final class BehaviorSentryItemProvider implements IBehaviorSentryItem
{
    /**
     * Shoots the specified ItemStack from a sentry.
     */
	@Override
	public ItemStack dispense(IBlockSource iblocksource, EntityMob target, ItemStack itemstack) {
		return null;
	}
}
