package T145.pillars.common.blocks;

import T145.pillars.common.CraftingPillars;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.common.IPlantable;

public class BaseFlowerBlock extends BlockFlower implements IPlantable
{

	public BaseFlowerBlock(int id)
	{
		super(id);
		this.setCreativeTab(CraftingPillars.tabPillar);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister itemIcon)
	{
		this.blockIcon = itemIcon.registerIcon(CraftingPillars.ID + ":" + this.getUnlocalizedName().substring(5));
	} /*

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canThisPlantGrowOnThisBlock(Block block)
	{
		return block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland;
	}           */
}
