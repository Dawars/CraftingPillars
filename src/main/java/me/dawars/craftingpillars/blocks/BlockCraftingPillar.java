package me.dawars.craftingpillars.blocks;

import me.dawars.craftingpillars.tileentity.TileEntityCraftingPillar;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCraftingPillar extends BaseTileBlock {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingPillar(String name) {
        super(name);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    /**
     * Set the direction of the block based on the player orientation
     *
     * @param world
     * @param pos
     * @param facing
     * @param hitX
     * @param hitY
     * @param hitZ
     * @param meta
     * @param placer
     * @param stack
     * @return
     */
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, ItemStack stack) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, stack)
                .withProperty(FACING, placer.getHorizontalFacing());
    }

    /**
     * Get metadata from location
     * @param state
     * @return
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    /**
     * Get state from meta (deprecated, don't know what i should use)
     * @param meta
     * @return
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    /**
     * Does nothing for us?
     * @param state
     * @return
     */
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    /**
     *Fixes rendering lightmap and neighboring block sides
     * @param state
     * @return
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Does nothing for us?
     * @return
     */
    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityCraftingPillar();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

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
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // TODO drop inventory
        if (!worldIn.isRemote) {
            TileEntityCraftingPillar te = (TileEntityCraftingPillar) worldIn.getTileEntity(pos);
            if (te != null) {
                for (int i = 0; i < 9; ++i) {
                    ItemStack itemStack = te.removeStackFromSlot(i);
                    if (itemStack != null) {
                        worldIn.spawnEntityInWorld(new EntityItem(worldIn,
                                pos.getX() + 0.5,
                                pos.getY() + 1,
                                pos.getZ() + 0.5,
                                itemStack));
                    }
                }
            }
        }

        super.breakBlock(worldIn, pos, state);
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

                if (heldItem == null || heldItem.stackSize <= 0) {
                    te.playerExtractItem(slot, hitX, hitZ, playerIn.isSneaking());
                } else {
                    te.playerInsertItem(slot, playerIn.isCreative() ? heldItem.copy() : heldItem, playerIn.isSneaking());
                }
                return true;
            }
        }
        return false;
    }
}
