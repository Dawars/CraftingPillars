package me.dawars.craftingpillars.blocks;

import me.dawars.craftingpillars.CraftingPillars;
import me.dawars.craftingpillars.tileentity.TileShowcase;
import me.dawars.craftingpillars.util.helpers.ServerHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockShowcase extends BaseTileBlock {
    public BlockShowcase(String name) {
        super(name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileShowcase();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {


        TileShowcase te = (TileShowcase) worldIn.getTileEntity(pos);
        if (te != null) {
            CraftingPillars.LOGGER.info(te.getStackInSlot(0));

            if (ServerHelper.isServerWorld(worldIn)) {
                if (side == EnumFacing.UP) {
                    ItemStack stackInSlot = te.getStackInSlot(0);
                    // put in
                    if (heldItem != null) {
                        ItemStack stackToInsert = new ItemStack(heldItem.getItem(), 1);
                        if (te.canInsertItem(0, stackToInsert, side)) { // FIXME only for automation?
                            if (stackInSlot == null) {
                                te.setInventorySlotContents(0, stackToInsert);
                            } else {
                                te.decrStackSize(0, -1);
                            }

                            if (!playerIn.isCreative())
                                heldItem.stackSize--;
                            return true;
                        }
                    }


                    // take out
                    if (playerIn.isSneaking()) {
                        CraftingPillars.LOGGER.info("Sneak right click");

                        ItemStack stackToExtract = stackInSlot.copy();
                        stackToExtract.stackSize = 1;

                        if (te.canExtractItem(0, stackToExtract, side)) {

                            te.decrStackSize(0, 1);
                            return true;
                        }
                    }
                }
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }
}
