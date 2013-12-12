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
     * Dispenses the specified ItemStack from a dispenser.
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
     * Dispense the specified stack, play the dispense sound and spawn particles.
	 * @param target 
     */
    public ItemStack spawnEntity(IBlockSource sourceblock, EntityMob target, ItemStack item)
    {
        IProjectile iprojectile = this.getProjectileEntity(target, sourceblock);
        if(iprojectile != null)
        	sourceblock.getWorld().spawnEntityInWorld((Entity)iprojectile);
        item.splitStack(1);
        return item;
    }

    /**
     * Play the dispense sound from the specified block.
     */
    protected void playSound(IBlockSource blockSource)
    {
        blockSource.getWorld().playAuxSFX(1000, blockSource.getXInt(), blockSource.getYInt(), blockSource.getZInt(), 0);
    }

    /**
     * Order clients to display dispense particles from the specified block and facing.
     */
    protected void spawnParticles(IBlockSource blockSource)
    {
        blockSource.getWorld().playAuxSFX(2000, blockSource.getXInt(), blockSource.getYInt(), blockSource.getZInt(), 6);
    }

    /**
     * Return the projectile entity spawned by this dispense behavior.
     */
    protected abstract IProjectile getProjectileEntity(EntityMob target, IBlockSource blockSource);
	
    protected float getSpeed()
    {
        return 6.0F;
    }
    
    protected float getAccuracy()
    {
        return 1.1F;
    }

}
