package me.dawars.CraftingPillars.api.sentry;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IRegistry;
import net.minecraft.dispenser.RegistryDefaulted;
import net.minecraft.item.Item;

public class SentryBehaviors {
	/** Registry for all sentry behaviors. */
	public static Map sentryBehaviorRegistry = new HashMap();
	
	public static void registerDispenserBehaviours()
	{
		sentryBehaviorRegistry.put(Item.arrow.itemID, new SentryBehaviorArrow());
		sentryBehaviorRegistry.put(Item.snowball.itemID, new SentryBehaviorSnowball());
	}
}
