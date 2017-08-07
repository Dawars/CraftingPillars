package me.dawars.craftingpillars.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BaseTileBlock extends BaseBlockPillar implements ITileEntityProvider {
    public BaseTileBlock(String name) {
        super(name);
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int i);
}
