package me.dawars.CraftingPillars.api.sentry;

import me.dawars.CraftingPillars.entity.FakeSentryPlayer;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SentryBehaviorArrow extends SentryDefaultProjectile
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
		
		
//		EntityArrow entityammo = new EntityArrow(world, x + 0.5F, y + 1.5F, z + 0.5F);
		EntityArrow entityammo = new EntityArrow(world, new FakeSentryPlayer(world, "Sentry"), target, getSpeed(), getAccuracy());
		entityammo.setDamage(entityammo.getDamage() + 1);
        
		entityammo.setPosition(x + 0.5F, y + 1.5F, z + 0.5F);

		entityammo.posY = y + 1.5F;
        double d0 = target.posX - x - 0.5F;
        double d1 = target.boundingBox.minY + (double)(target.height / 3.0F) - entityammo.posY;
        double d2 = target.posZ - z - 0.5F;
        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D)
        {
            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            entityammo.setLocationAndAngles(x + 0.5F + d4, entityammo.posY, z + 0.5F + d5, f2, f3);
            entityammo.yOffset = 0.0F;
            float f4 = (float)d3 * 0.2F;
            entityammo.setThrowableHeading(d0, d1 + (double)f4, d2, getSpeed(), getAccuracy());
        }
		return entityammo;
	}
	
	/**
     * Return the speed/strength of the projectile entity - Doesn't used by default.
     */
	@Override
	protected float getSpeed()
    {
        return 1.6F;
    }
	
	/**
     * Return the accuracy of the projectile entity - Doesn't used by default.
     */
	@Override
	protected float getAccuracy()
    {
        return 3F;
    }
}
