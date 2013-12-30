package me.dawars.CraftingPillars.api.sentry;

import me.dawars.CraftingPillars.entity.FakeSentryPlayer;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SentryBehaviorSnowball extends SentryDefaultProjectile
{
	/**
	 * Return the projectile entity spawned by this dispense behavior.
	 */
	@Override
	protected IProjectile getProjectileEntity(EntityMob target, EntityPlayer owner, IBlockSource blockSource, ItemStack item) {

		World world = blockSource.getWorld();
		int x = blockSource.getXInt();
		int y = blockSource.getYInt();
		int z = blockSource.getZInt();


		EntitySnowball entityammo = new EntitySnowball(world, new FakeSentryPlayer(world, "Sentry"));
		//		EntitySnowball entityammo = new EntitySnowball(world, x + 0.5F, y + 1.5F, z + 0.5F);

		entityammo.setPosition(x + 0.5F, y + 1.5F, z + 0.5F);
		double d0 = target.posX - x - 0.5F;
		double d1 = target.posY + target.getEyeHeight() - 1.100000023841858D - entityammo.posY;
		double d2 = target.posZ - z - 0.5F;
		float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
		entityammo.setThrowableHeading(d0, d1 + f1, d2, 1.6F, 1);

		return entityammo;
	}
}
