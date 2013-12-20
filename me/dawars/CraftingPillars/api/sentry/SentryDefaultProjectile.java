package me.dawars.CraftingPillars.api.sentry;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class SentryDefaultProjectile implements IBehaviorSentryItem
{
	
	/**
     * Returns the specified ItemStack to be removed from the pillar.
     */
	@Override
    public final ItemStack dispense(IBlockSource blockSource,  EntityMob target, ItemStack item)
    {
        this.playSound(blockSource);
        this.spawnParticles(blockSource);
        this.spawnEntity(blockSource, target, item);
        return item;
    }
	
	/**
     * Spawns the entity.
	 * @param sourceblock - position info for the block
	 * @param target - the target the Pillar is shooting at
	 * @param item - the item placed into the pillar
     */
    public ItemStack spawnEntity(IBlockSource sourceblock, EntityMob target, ItemStack item)
    {
        IProjectile iprojectile = this.getProjectileEntity(target, sourceblock, item);
        if(iprojectile != null)
        	sourceblock.getWorld().spawnEntityInWorld((Entity)iprojectile);
        item.splitStack(1);
        return item;
    }

    /**
     * Play the appropriate sound for the shooting.
     */
    protected void playSound(IBlockSource blockSource)
    {
        blockSource.getWorld().playAuxSFX(1000, blockSource.getXInt(), blockSource.getYInt(), blockSource.getZInt(), 0);
    }

    /**
     * Order clients to display particles for shooting.
     */
    protected void spawnParticles(IBlockSource blockSource)
    {
        blockSource.getWorld().playAuxSFX(2000, blockSource.getXInt(), blockSource.getYInt(), blockSource.getZInt(), 6);
    }

    /**
     * Return the projectile entity spawned by the Sentry behavior.
     * @param item 
     */
    protected abstract IProjectile getProjectileEntity(EntityMob target, IBlockSource blockSource, ItemStack item);
	
    protected float getSpeed()
    {
        return 6.0F;
    }
    
    protected float getAccuracy()
    {
        return 1.1F;
    }

}
