package me.dawars.CraftingPillars.api.sentry;

import java.util.Random;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SentryBehaviorFireball extends SentryDefaultProjectile
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
		
		double d0 = target.posX - x - 0.5F;
		double d1 = target.posY + (double)target.getEyeHeight() - 1.7D - y;
		double d2 = target.posZ - z - 0.5F;

		
		world.spawnEntityInWorld(new EntitySmallFireball(world, x + 0.5F, y + 1.5F, z + 0.5F, d0, d1, d2));
		
        return null;
	}
            
//    /**
//     * Play the dispense sound from the specified block.
//     */
//    @Override
//    protected void playSound(IBlockSource blockSource)
//    {
//        blockSource.getWorld().playAuxSFXAtEntity((EntityPlayer)null, 1008, blockSource.getXInt(), blockSource.getYInt(), blockSource.getZInt(), 0);
//    }
}
