package me.dawars.craftingpillars.blocks;

import me.dawars.craftingpillars.tileentity.TileBase;
import me.dawars.craftingpillars.tileentity.TileTank;
import me.dawars.craftingpillars.util.FluidHelper;
import mrtjp.core.util.Enum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockTank extends BaseTileBlock {
    public BlockTank(String name) {
        super(name);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, false)
                .withProperty(EAST, false)
                .withProperty(SOUTH, false)
                .withProperty(WEST, false));

        setHardness(15.0F);
        setResistance(25.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileTank();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    /**
     * Whether this tank connects in the northern direction
     */
    public static final PropertyBool NORTH = PropertyBool.create("north");
    /**
     * Whether this tank connects in the eastern direction
     */
    public static final PropertyBool EAST = PropertyBool.create("east");
    /**
     * Whether this tank connects in the southern direction
     */
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    /**
     * Whether this tank connects in the western direction
     */
    public static final PropertyBool WEST = PropertyBool.create("west");

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
// FIXME comparator in TE
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {

        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {

        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {

        return side == EnumFacing.UP || side == EnumFacing.DOWN;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

        TileBase tile = (TileBase) world.getTileEntity(pos); // FIXME add external update util

        if (tile != null) {
            IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
            if (FluidHelper.isFluidHandler(heldItem) && FluidHelper.interactWithHandler(heldItem, handler, player, hand)) {
                tile.updateBlock();// FIXME refactor this
                return true;
            }
        }
        return super.onBlockActivated(world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileTank) {
            TileTank tank = (TileTank) tile;
            return tank.getFluidLightLevel();
        }

        return super.getLightValue(state, world, pos);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state
                .withProperty(NORTH, this.canConnectTo(worldIn, pos.north(), EnumFacing.NORTH))
                .withProperty(EAST, this.canConnectTo(worldIn, pos.east(), EnumFacing.EAST))
                .withProperty(SOUTH, this.canConnectTo(worldIn, pos.south(), EnumFacing.SOUTH))
                .withProperty(WEST, this.canConnectTo(worldIn, pos.west(), EnumFacing.WEST));
    }

    private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing face) {
        TileEntity tile = worldIn instanceof ChunkCache ? ((ChunkCache) worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);

        return tile != null && (tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, face) || tile instanceof IFluidHandler);
    }


    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        switch (rot) {
            case CLOCKWISE_180:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        switch (mirrorIn) {
            case LEFT_RIGHT:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
            default:
                return super.withMirror(state, mirrorIn);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
    }
}
