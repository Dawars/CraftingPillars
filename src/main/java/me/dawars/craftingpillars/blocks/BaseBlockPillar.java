package me.dawars.craftingpillars.blocks;

import me.dawars.craftingpillars.CraftingPillars;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BaseBlockPillar extends Block {

    public BaseBlockPillar(String name) {
        super(Material.ROCK);
        setHardness(1.5f);
        setResistance(10f);
        setSoundType(SoundType.STONE);
        setCreativeTab(CraftingPillars.CREATIVETAB);
        setUnlocalizedName(name);
        setRegistryName(CraftingPillars.MODID, name);
    }
}
