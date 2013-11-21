package me.dawars.CraftingPillars.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class BaseGui extends GuiContainer{

    protected TileEntity tile;

	public BaseGui(BaseContainer container, IInventory inventory) {
        super(container);

        if (inventory instanceof TileEntity)
                tile = (TileEntity) inventory;
	}
	public BaseGui(BaseContainer container)
	{
        super(container);
	}
	
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		
	}

}
