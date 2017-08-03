package me.dawars.craftingpillars.blocks;

import me.dawars.craftingpillars.tiles.TileEntityCraftingPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
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
}
