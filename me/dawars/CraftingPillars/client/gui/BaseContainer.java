package me.dawars.CraftingPillars.client.gui;

import net.minecraft.inventory.Container;

public abstract class BaseContainer extends Container {

    
    private int inventorySize;

    public BaseContainer(int inventorySize) {
            this.inventorySize = inventorySize;
    }

    public int getInventorySize(){
            return inventorySize;
    }
}