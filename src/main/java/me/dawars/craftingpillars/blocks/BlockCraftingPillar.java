package me.dawars.craftingpillars.blocks;

import me.dawars.craftingpillars.CraftingPillars;
import me.dawars.craftingpillars.tiles.TileEntityCraftingPillar;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCraftingPillar extends BaseTileBlock {
    public BlockCraftingPillar(String name) {
        super(name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityCraftingPillar();
    }
// TODO: fix neighbouring side transparency

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    /*    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityCraftingPillar();
    }*/

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        if (!worldIn.isRemote) {
            TileEntityCraftingPillar te = (TileEntityCraftingPillar) worldIn.getTileEntity(pos);
            if (te != null) {
                te.playerCraftItem(playerIn);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }

        if (side == EnumFacing.UP) {
            TileEntityCraftingPillar te = (TileEntityCraftingPillar) worldIn.getTileEntity(pos);
            if (te != null) {
                int slot = hitX > 0.33f ? (hitX > 0.66f ? 2 : 1) : 0;
                slot += hitZ > 0.33f ? (hitZ > 0.66f ? 6 : 3) : 0;

                CraftingPillars.LOGGER.info(heldItem);
                if (heldItem == null || heldItem.stackSize <= 0) {
                    te.playerExtractItem(slot, hitX, hitZ, playerIn.isSneaking());
                } else {
                    te.playerInsertItem(slot, heldItem, playerIn.isSneaking());
                }
                return true;
            }
        }
        return false;
    }
}
