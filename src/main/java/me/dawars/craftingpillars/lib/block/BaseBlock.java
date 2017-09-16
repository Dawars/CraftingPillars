package me.dawars.craftingpillars.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BaseBlock extends Block {
    public BaseBlock(Material mat) {
        super(mat);
    }

    public Block setBlockName(String name) {
        return super.setBlockName(name);
    }

    public Block setUnlocalizedName(String name) {
        return super.setBlockName(name);
    }

    public Block setStepSound(SoundType soundType) {
        return super.setStepSound(soundType);
    }

    public Block setSoundType(SoundType soundType) {
        return super.setStepSound(soundType);
    }
}
