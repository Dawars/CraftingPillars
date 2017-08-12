package me.dawars.craftingpillars.blocks;

import me.dawars.craftingpillars.CraftingPillars;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BaseBlock extends Block {

    public BaseBlock(String name) {
        super(Material.ROCK);
        setHardness(1.5f);
        setResistance(10f);
        setSoundType(SoundType.STONE);
        setCreativeTab(CraftingPillars.CREATIVETAB);
        setUnlocalizedName(name);
        setRegistryName(CraftingPillars.MODID, name);
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

}
