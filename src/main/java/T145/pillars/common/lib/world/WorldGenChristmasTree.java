package T145.pillars.common.lib.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import T145.pillars.common.CraftingPillars;

public class WorldGenChristmasTree extends WorldGenerator {
	private Block leaves, log;
	int logMeta;
	private boolean manualGrowth;

	public WorldGenChristmasTree(boolean fromSapling) {
		super(fromSapling);
		manualGrowth = fromSapling;
		leaves = CraftingPillars.blockChristmasLeaves;
		log = Blocks.log;
		logMeta = 1;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		if (!manualGrowth)
			y += 1;

		addLog(world, x, y, z);
		addLeaves(world, x, y + 1, z);
		addLeaves(world, x + 1, y, z);
		addLeaves(world, x, y, z + 1);
		addLeaves(world, x - 1, y, z);
		addLeaves(world, x, y, z - 1);
		addLog(world, x, y + 1, z);
		addLeaves(world, x, y + 2, z);
		addLeaves(world, x + 1, y + 1, z);
		addLeaves(world, x, y + 1, z + 1);
		addLeaves(world, x - 1, y + 1, z);
		addLeaves(world, x, y + 1, z - 1);

		if (world.getBlock(x + 1, y, z) == leaves)
			world.setBlockToAir(x + 1, y, z);
		if (world.getBlock(x, y, z + 1) == leaves)
			world.setBlockToAir(x, y, z + 1);
		if (world.getBlock(x - 1, y, z) == leaves)
			world.setBlockToAir(x - 1, y, z);
		if (world.getBlock(x, y, z - 1) == leaves)
			world.setBlockToAir(x, y, z - 1);

		addLeaves(world, x + 1, y + 1, z + 1);
		addLeaves(world, x - 1, y + 1, z - 1);
		addLeaves(world, x + 1, y + 1, z - 1);
		addLeaves(world, x - 1, y + 1, z + 1);

		addLeaves(world, x + 1, y + 2, z);
		addLeaves(world, x, y + 2, z + 1);
		addLeaves(world, x - 1, y + 2, z);
		addLeaves(world, x, y + 2, z - 1);
		addLog(world, x, y + 2, z);

		addLeaves(world, x, y + 3, z);

		addLeaves(world, x + 2, y + 1, z);
		addLeaves(world, x - 2, y + 1, z);
		addLeaves(world, x, y + 1, z - 2);
		addLeaves(world, x, y + 1, z + 2);

		addLeaves(world, x + 1, y + 2, z + 1);
		addLeaves(world, x - 1, y + 2, z - 1);
		addLeaves(world, x + 1, y + 2, z - 1);
		addLeaves(world, x - 1, y + 2, z + 1);

		addLeaves(world, x + 1, y + 3, z);
		addLeaves(world, x, y + 3, z + 1);
		addLeaves(world, x - 1, y + 3, z);
		addLeaves(world, x, y + 3, z - 1);
		addLog(world, x, y + 3, z);

		addLeaves(world, x, y + 4, z);
		addLeaves(world, x + 1, y + 1, z + 2);
		addLeaves(world, x - 1, y + 1, z + 2);
		addLeaves(world, x + 2, y + 1, z + 1);
		addLeaves(world, x + 2, y + 1, z - 1);
		addLeaves(world, x + 1, y + 1, z - 2);
		addLeaves(world, x - 1, y + 1, z - 2);
		addLeaves(world, x - 2, y + 1, z + 1);
		addLeaves(world, x - 2, y + 1, z - 1);

		addLeaves(world, x + 2, y + 2, z);
		addLeaves(world, x, y + 2, z + 2);
		addLeaves(world, x - 2, y + 2, z);
		addLeaves(world, x, y + 2, z - 2);

		addLeaves(world, x + 1, y + 3, z + 1);
		addLeaves(world, x - 1, y + 3, z - 1);
		addLeaves(world, x + 1, y + 3, z - 1);
		addLeaves(world, x - 1, y + 3, z + 1);

		addLeaves(world, x + 1, y + 4, z);
		addLeaves(world, x, y + 4, z + 1);
		addLeaves(world, x - 1, y + 4, z);
		addLeaves(world, x, y + 4, z - 1);
		addLog(world, x, y + 4, z);

		addLeaves(world, x, y + 5, z);

		for (int a = 0; a < 5; a++) {
			int i = x + random.nextInt(5) - 2, k = z + random.nextInt(5) - 2, j = y - 5;

			while (j < y + 5) {
				Block block = world.getBlock(i, j, k);
				if ((block == null || block.canBeReplacedByLeaves(world, i, j, k) || block.isReplaceable(world, i, j, k)))
					break;
				else
					j++;
			}

			if (world.getBlock(i, j - 1, k) != leaves) {
				world.setBlock(i, j, k, CraftingPillars.blockChristmasPresent);
				world.setBlockMetadataWithNotify(i, j, k, random.nextInt(2), 2);
			}
		}

		return true;
	}

	private boolean addLeaves(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		if (block == null || block.canBeReplacedByLeaves(world, x, y, z) || block.isReplaceable(world, x, y, z)) {
			world.setBlock(x, y, z, leaves);
			return true;
		}
		return false;
	}

	private boolean addLog(World world, int x, int y, int z) {
		world.setBlock(x, y, z, log);
		world.setBlockMetadataWithNotify(x, y, z, logMeta, 2);

		return true;
	}
}