package me.dawars.craftingpillars.tileentity;

import me.dawars.craftingpillars.inventory.InventoryShowcase;

public class TileShowcase extends TilePillar {

    public TileShowcase() {
        setInternalInventory(new InventoryShowcase(this, 1, "inv_showcase"));
    }
}
