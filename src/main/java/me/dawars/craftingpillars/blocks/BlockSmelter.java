package me.dawars.craftingpillars.blocks;

import me.dawars.craftingpillars.tileentity.TileSmelter;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSmelter extends BaseTileBlock {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool BURNING = PropertyBool.create("burning");

    public BlockSmelter(String name) {
        super(name);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setDefaultState(blockState.getBaseState().withProperty(BURNING, false));
    }

    /**
     * Set the direction of the block based on the player orientation
     */
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, ItemStack stack) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, stack)
                .withProperty(FACING, placer.getHorizontalFacing())
                .withProperty(BURNING, false);
    }

    /**
     * Get metadata from location
     *
     * @param state
     * @return
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int isBurning = 0;
        if (state.getValue(BURNING)) {
            isBurning = 1 << 2;
        }
        return state.getValue(FACING).getHorizontalIndex() | isBurning;
    }

    /**
     * Get state from meta (deprecated, don't know what i should use)
     *
     * @param meta
     * @return
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean isBurning = (meta & 0b100) == 0b100;
        return getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta & 0b11))
                .withProperty(BURNING, isBurning);
    }

    /**
     * Does nothing for us?
     *
     * @param state
     * @return
     */
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    /**
     * Fixes rendering lightmap and neighboring block sides
     *
     * @param state
     * @return
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Does nothing for us?
     *
     * @return
     */
    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(BURNING)) {
            EnumFacing enumfacing = stateIn.getValue(FACING);
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double) pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            switch (enumfacing) {
                case WEST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case EAST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case NORTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BURNING);
    }


    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileSmelter();
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {

        return side == EnumFacing.UP || side == EnumFacing.DOWN;
    }

    /**
     * Set burning state
     */
    public static void setState(boolean burning, World worldIn, BlockPos pos) {

        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
//        keepInventory = true;

        worldIn.setBlockState(pos, CpBlocks.smeltingPillar.getDefaultState()
                .withProperty(FACING, iblockstate.getValue(FACING))
                .withProperty(BURNING, burning), 3);


//        keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
}
