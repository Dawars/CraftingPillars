package me.dawars.CraftingPillars.world.gen;

import java.util.Random;

import me.dawars.CraftingPillars.CraftingPillars;
import me.dawars.CraftingPillars.blocks.BaseFlowerBlock;
import me.dawars.CraftingPillars.blocks.ChristmasTreeSapling;
import mods.elysium.Elysium;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class ChristmasTreeGen extends WorldGenerator
{
	private int leavesId, logId, logMeta;
	private boolean fromSapling;
	private int stage;

	public ChristmasTreeGen(boolean fromSapling)
	{
		super(fromSapling);
		this.fromSapling = fromSapling;
		this.leavesId = CraftingPillars.blockChristmasLeaves.blockID;
		this.logId = Block.wood.blockID;
		this.logMeta = 1;
	}
	
	public ChristmasTreeGen(boolean fromSapling, int stage)
	{
		this(fromSapling);
		this.stage = stage;
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		if(!this.fromSapling)
			y += 1;
		
		//We regenerate it every time so it always look good (player can't really replace it if broken by accident)
	 	if(this.stage >= 0)
	 	{
	 		addLog(world, x, y, z);
	 		addLeaves(world, x, y+1, z);
	 		addLeaves(world, x+1, y, z);
	 		addLeaves(world, x, y, z+1);
	 		addLeaves(world, x-1, y, z);
	 		addLeaves(world, x, y, z-1);
	 	}
	 	if(this.stage >= 1)
	 	{
	 		addLog(world, x, y+1, z);
	 		addLeaves(world, x, y+2, z);
	 		addLeaves(world, x+1, y+1, z);
	 		addLeaves(world, x, y+1, z+1);
	 		addLeaves(world, x-1, y+1, z);
	 		addLeaves(world, x, y+1, z-1);
	
	 		if(world.getBlockId(x+1, y, z) == this.leavesId)
	 			world.setBlockToAir(x+1, y, z);
	 		if(world.getBlockId(x, y, z+1) == this.leavesId)
	 			world.setBlockToAir(x, y, z+1);
	 		if(world.getBlockId(x-1, y, z) == this.leavesId)
	 			world.setBlockToAir(x-1, y, z);
	 		if(world.getBlockId(x, y, z-1) == this.leavesId)
	 			world.setBlockToAir(x, y, z-1);
	 	}
	 	if(this.stage >= 2)
	 	{
	 		//lvl1
	 		addLeaves(world, x+1, y+1, z+1);
	 		addLeaves(world, x-1, y+1, z-1);
	 		addLeaves(world, x+1, y+1, z-1);
	 		addLeaves(world, x-1, y+1, z+1);
	 		
	 		//lvl2
	 		addLeaves(world, x+1, y+2, z);
	 		addLeaves(world, x, y+2, z+1);
	 		addLeaves(world, x-1, y+2, z);
	 		addLeaves(world, x, y+2, z-1);
	 		addLog(world, x, y+2, z);
	
	 		//lvl3
	 		addLeaves(world, x, y+3, z);
	
	 	}
	 	if(this.stage >= 3)
	 	{
	 		//lvl1
	 		addLeaves(world, x+2, y+1, z);
	 		addLeaves(world, x-2, y+1, z);
	 		addLeaves(world, x, y+1, z-2);
	 		addLeaves(world, x, y+1, z+2);
	 		
	 		//lvl2
	 		addLeaves(world, x+1, y+2, z+1);
	 		addLeaves(world, x-1, y+2, z-1);
	 		addLeaves(world, x+1, y+2, z-1);
	 		addLeaves(world, x-1, y+2, z+1);
	 		
	 		//lvl3
	 		addLeaves(world, x+1, y+3, z);
	 		addLeaves(world, x, y+3, z+1);
	 		addLeaves(world, x-1, y+3, z);
	 		addLeaves(world, x, y+3, z-1);
	 		addLog(world, x, y+3, z);
	
	 		//lvl4
	 		addLeaves(world, x, y+4, z);
	 	}
	 	if(this.stage >= 4)
	 	{
	 		//lvl1
	 		addLeaves(world, x+1, y+1, z+2);
	 		addLeaves(world, x-1, y+1, z+2);
	 		addLeaves(world, x+2, y+1, z+1);
	 		addLeaves(world, x+2, y+1, z-1);
	 		addLeaves(world, x+1, y+1, z-2);
	 		addLeaves(world, x-1, y+1, z-2);
	 		addLeaves(world, x-2, y+1, z+1);
	 		addLeaves(world, x-2, y+1, z-1);
	 		
	 		//lvl2
	 		addLeaves(world, x+2, y+2, z);
	 		addLeaves(world, x, y+2, z+2);
	 		addLeaves(world, x-2, y+2, z);
	 		addLeaves(world, x, y+2, z-2);
	 		
	 		//lvl3
	 		addLeaves(world, x+1, y+3, z+1);
	 		addLeaves(world, x-1, y+3, z-1);
	 		addLeaves(world, x+1, y+3, z-1);
	 		addLeaves(world, x-1, y+3, z+1);
	
	 		//lvl4
	 		addLeaves(world, x+1, y+4, z);
	 		addLeaves(world, x, y+4, z+1);
	 		addLeaves(world, x-1, y+4, z);
	 		addLeaves(world, x, y+4, z-1);
	 		addLog(world, x, y+4, z);
	
	 		//lvl5
	 		addLeaves(world, x, y+5, z);
	
	 		for(int a = 0; a < 5; a++)
	 		{
	 			int i = x+random.nextInt(5)-2;
	 			int k = z+random.nextInt(5)-2;
	 			int j = y - 5;

	 			while(j < y + 5)
				{
	 				int id = world.getBlockId(i, j, k);
	 			    Block block = Block.blocksList[id];
	 			    if((block == null || block.canBeReplacedByLeaves(world, i, j, k) || block.isBlockReplaceable(world, i, j, k)))
	 			    {
 			    		break;
	 			    } else {
	 			    	j++;
	 			    }
				}
	 		   
	 			int id = world.getBlockId(i, j, k);
	 		    Block block = Block.blocksList[id];
	 			if(world.getBlockId(i, j-1, k) != this.leavesId)
	 			{	 		
	 				world.setBlock(i, j, k, CraftingPillars.blockChristmasPresent.blockID);
	 		    	world.setBlockMetadataWithNotify(i, j, k, random.nextInt(2), 2);
	 			}
	 		}
	 	}
 	
	 	return true;
	}

	private boolean addLeaves(World world, int x, int y, int z)
	{
	    int id = world.getBlockId(x, y, z);
	    Block block = Block.blocksList[id];
	    if(block == null || block.canBeReplacedByLeaves(world, x, y, z) || block.isBlockReplaceable(world, x, y, z))
	    {
	    	if(CraftingPillars.modElysium && world.provider.dimensionId == Elysium.DimensionID)
			{
				world.setBlock(x, y, z, Elysium.blockLeavesFostimber.blockID);
			} else {
				world.setBlock(x, y, z, this.leavesId);
			}
		    return true;
	    }
	    return false;
	}
	
	private boolean addLog(World world, int x, int y, int z)
	{
		if(CraftingPillars.modElysium && world.provider.dimensionId == Elysium.DimensionID)
		{
			world.setBlock(x, y, z, Elysium.blockLogFostimber.blockID);
		} else {
			world.setBlock(x, y, z, this.logId);
	    	world.setBlockMetadataWithNotify(x, y, z, this.logMeta, 2);
		}
		
	    return true;
	}
}