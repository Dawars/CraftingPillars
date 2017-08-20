package me.dawars.craftingpillars.tileentity;

import me.dawars.craftingpillars.inventory.InventorySmelter;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static me.dawars.craftingpillars.inventory.InventorySmelter.*;
import static net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime;

public class TileSmelter extends TilePillar implements ITickable {
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int furnaceBurnTime;
    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;

    public TileSmelter() {
        setInternalInventory(new InventorySmelter(this, 3, "inventory_smelter"));
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    @Override
    public void update() {
        // update cooking
        ItemStack stackRaw = getInternalInventory().getStackInSlot(SLOT_RAW);
        ItemStack stackFuel = getInternalInventory().getStackInSlot(SLOT_FUEL);
        ItemStack stackResult = getInternalInventory().getStackInSlot(SLOT_COOKED);
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning()) {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote) {
            if (this.isBurning() || stackFuel != null && stackRaw != null) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.furnaceBurnTime = getItemBurnTime(stackFuel);
                    this.currentItemBurnTime = this.furnaceBurnTime;

                    if (this.isBurning()) {
                        flag1 = true;

                        if (stackRaw != null) {
                            getInternalInventory().decrStackSize(SLOT_RAW, 1);

                            if (stackRaw.stackSize == 0) {
                                getInternalInventory().setInventorySlotContents(SLOT_RAW, stackRaw.getItem().getContainerItem(stackRaw));
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = getCookTime(stackRaw);
                        this.smeltItem();
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                // set state
//                BlockSmelter.setState(this.isBurning(), this.worldObj, this.pos);
            }
        }

        if (flag1) {
            this.markDirty();
            callBlockUpdate();
        }

        // update lighting

    }

    public int getCookTime(@Nullable ItemStack stack) {
        return 180; // little faster
    }


    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt() {
        ItemStack stackToCook = getInternalInventory().getStackInSlot(InventorySmelter.SLOT_RAW);
        ItemStack resultStack = getInternalInventory().getStackInSlot(InventorySmelter.SLOT_COOKED);
        if (stackToCook == null) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(stackToCook);
            if (itemstack == null) return false;
            if (resultStack == null) return true;
            if (!resultStack.isItemEqual(itemstack)) return false;
            int result = resultStack.stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= resultStack.getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }


    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack stackRaw = getInternalInventory().getStackInSlot(SLOT_RAW);
            ItemStack stackFuel = getInternalInventory().getStackInSlot(SLOT_FUEL);
            ItemStack stackResult = getInternalInventory().getStackInSlot(SLOT_COOKED);

            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(stackRaw);

            if (stackResult == null) {
                getInternalInventory().setInventorySlotContents(SLOT_COOKED, itemstack.copy());
            } else if (stackResult.getItem() == itemstack.getItem()) {
                int sum = stackResult.stackSize + itemstack.stackSize;
                getInternalInventory().setInventorySlotContents(SLOT_COOKED, new ItemStack(stackResult.getItem(), sum));
                // Forge BugFix: Results may have multiple items
            }

            if (stackRaw.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && stackRaw.getMetadata() == 1 &&
                    stackFuel != null && stackFuel.getItem() == Items.BUCKET) {
                getInternalInventory().setInventorySlotContents(SLOT_FUEL, new ItemStack(Items.WATER_BUCKET));
            }
            getInternalInventory().decrStackSize(SLOT_RAW, 1);

            if (stackRaw.stackSize <= 0) {
                getInternalInventory().setInventorySlotContents(SLOT_RAW, null);
            }
        }
    }
}
