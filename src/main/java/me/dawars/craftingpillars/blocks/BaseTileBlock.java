package me.dawars.craftingpillars.blocks;

import me.dawars.craftingpillars.tileentity.TileBase;
import me.dawars.craftingpillars.tileentity.TilePillar;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BaseTileBlock extends BaseBlock implements ITileEntityProvider {
    public BaseTileBlock(String name) {
        super(name);
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int i);

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.getHeldItemMainhand() == null && worldIn.getTileEntity(pos) instanceof TilePillar) {
            ((TilePillar) worldIn.getTileEntity(pos)).toggleShow();
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {

        TileEntity tile = world.getTileEntity(pos);
        return tile instanceof TileBase && tile.hasWorldObj() ? ((TileBase) tile).getComparatorInputOverride() : 0;
    }


    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileBase) {
            TileBase tank = (TileBase) tile;
            return tank.getLightValue();
        }

        return super.getLightValue(state, world, pos);
    }
}
