package me.dawars.craftingpillars.blocks;

import me.dawars.craftingpillars.tiles.TileEntityCraftingPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCraftingPillar extends BaseBlockPillar {
    public BlockCraftingPillar(String name) {
        super(name);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityCraftingPillar();
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        System.out.println("clicked");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }

        if (facing == EnumFacing.UP) {
            TileEntityCraftingPillar te = (TileEntityCraftingPillar) worldIn.getTileEntity(pos);
            return te != null && te.onPlayerRightClickOnTop(playerIn, hitX, hitZ);
        }
        return false;
    }
}
