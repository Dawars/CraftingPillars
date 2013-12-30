package me.dawars.CraftingPillars.api.sentry;

import java.util.Iterator;
import java.util.List;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SentryBehaviorPotion extends SentryDefaultProjectile
{
	/**
     * Return the projectile entity spawned by this dispense behavior.
     */
	@Override
	protected IProjectile getProjectileEntity(EntityMob target, EntityPlayer owner, IBlockSource blockSource, ItemStack item) {
		
		if(!ItemPotion.isSplash(item.getItemDamage()))
		{
			return null;
		}
		World world = blockSource.getWorld();
		int x = blockSource.getXInt();
		int y = blockSource.getYInt();
		int z = blockSource.getZInt();
		
		
		boolean hasPotionEffect = false;
		
		List list = Item.potion.getEffects(item);

        if (list != null && !list.isEmpty())
        {
        	Iterator iterator1 = list.iterator();

            while (iterator1.hasNext())
            {
                PotionEffect potioneffect = (PotionEffect)iterator1.next();
                int i = potioneffect.getPotionID();

                if (target.getActivePotionEffect(Potion.potionTypes[i]) == null)
                {
                	hasPotionEffect = false;
                	break;
                }
                else
                {
                	hasPotionEffect = true;
                }
            }
        	
        }
        
        if(!hasPotionEffect)
        {
        
			EntityPotion entitypotion = new EntityPotion(world, x + 0.5F, y + 1.5F, z + 0.5F, item.copy());
	        
	
	        entitypotion.posY = y + 1.5F;
	        double d0 = target.posX - x - 0.5F;
	        double d1 = target.boundingBox.minY + (double)(target.height / 3.0F) - entitypotion.posY;
	        double d2 = target.posZ - z - 0.5F;
	        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
	
	        if (d3 >= 1.0E-7D)
	        {
	            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
	            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
	            double d4 = d0 / d3;
	            double d5 = d2 / d3;
	            entitypotion.setLocationAndAngles(x + 0.5F + d4, entitypotion.posY, z + 0.5F + d5, f2, f3);
	            entitypotion.yOffset = 0.0F;
	            float f4 = (float)d3 * 0.2F;
	            entitypotion.setThrowableHeading(d0, d1 + (double)f4, d2, getSpeed(), getAccuracy());
	        }
			return entitypotion;
        }
        return null;
	}

	/**
     * Spawns the entity.
	 * @param sourceblock - position info for the block
	 * @param target - the target the Pillar is shooting at
	 * @param item - the item placed into the pillar
     */
	@Override
    public ItemStack spawnEntity(IBlockSource sourceblock, EntityMob target, EntityPlayer owner, ItemStack item)
    {
        IProjectile iprojectile = this.getProjectileEntity(target, owner, sourceblock, item);
        if(iprojectile != null)
        {
        	sourceblock.getWorld().spawnEntityInWorld((Entity)iprojectile);
        	item.splitStack(1);
        }
        return item;
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
