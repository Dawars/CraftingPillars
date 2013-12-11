package me.dawars.CraftingPillars.api.sentry;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SentryBehaviorArrow extends SentryDefaultProjectile
{
	/**
     * Return the projectile entity spawned by this dispense behavior.
     */
	@Override
	protected IProjectile getProjectileEntity(EntityMob target, IBlockSource blockSource) {
		
		World world = blockSource.getWorld();
		int x = blockSource.getXInt();
		int y = blockSource.getYInt();
		int z = blockSource.getZInt();
		
		
		EntityArrow entityarrow = new EntityArrow(world, x, y+1, z);
        entityarrow.setDamage(entityarrow.getDamage() + 1);
        

        entityarrow.posY = y + 1.5F;
        double d0 = target.posX - x - 0.5F;
        double d1 = target.boundingBox.minY + (double)(target.height / 3.0F) - entityarrow.posY;
        double d2 = target.posZ - z - 0.5F;
        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D)
        {
            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            entityarrow.setLocationAndAngles(x + 0.5F + d4, entityarrow.posY, z + 0.5F + d5, f2, f3);
            entityarrow.yOffset = 0.0F;
            float f4 = (float)d3 * 0.2F;
            entityarrow.setThrowableHeading(d0, d1 + (double)f4, d2, 1.6F, (float)(14 - world.difficultySetting * 4));
        }
		return entityarrow;
	}
}
