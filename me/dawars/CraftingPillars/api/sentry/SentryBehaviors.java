package me.dawars.CraftingPillars.api.sentry;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.FMLLog;

public class SentryBehaviors {
	
	/** Registry for all sentry behaviors. */
	private static Map sentryBehaviorRegistry = new HashMap();

	/**
	 * Use this method to register a new behavior for an item/block.
	 * @param itemID - the itemID of the projectile or weapon
	 * @param behavior - The behavior for the projectile or weapon - must extend SentryDefaultProjectile
	 */	
	public static void add(int itemID, Object behavior)
	{
		if(behavior instanceof IBehaviorSentryItem)
		{
			sentryBehaviorRegistry.put(itemID, behavior);
		} else {
			FMLLog.warning("[CraftingPillar]: Couldn't register " + behavior.toString() + "! It has to implement IBehaviorSentryItem!");
		}
	}

	/**
	 * Gets the behavior for the itemID
	 * @param itemID - id of the weapon/projectile
	 * @return - behavior for the weapon/projectile
	 */
	public static IBehaviorSentryItem get(int itemID) {
		return (IBehaviorSentryItem) sentryBehaviorRegistry.get(itemID);
	}
}
