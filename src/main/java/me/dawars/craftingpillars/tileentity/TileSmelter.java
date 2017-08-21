package me.dawars.craftingpillars.tileentity;

import me.dawars.craftingpillars.inventory.InventorySmelter;
import me.dawars.craftingpillars.network.PacketCraftingPillar;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static me.dawars.craftingpillars.inventory.InventorySmelter.*;

public class TileSmelter extends TilePillar implements ITickable {
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int furnaceBurnTime;
    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    private int currentItemBurnTime;
    public int cookTime;
    private int totalCookTime;
    // TODO check if it drops xp (on breaking?)

    public TileSmelter() {
        setInternalInventory(new InventorySmelter(this, 3, "inv_smelter"));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.furnaceBurnTime = nbt.getInteger("BurnTime");
        this.cookTime = nbt.getInteger("CookTime");
        this.totalCookTime = nbt.getInteger("CookTimeTotal");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(getStackInSlot(SLOT_FUEL));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("BurnTime", this.furnaceBurnTime);
        nbt.setInteger("CookTime", this.cookTime);
        nbt.setInteger("CookTimeTotal", this.totalCookTime);

        return nbt;
    }
/* NETWORK METHODS */

    /* SERVER -> CLIENT */
    @Override
    public PacketCraftingPillar getTilePacket() {

        PacketCraftingPillar payload = super.getTilePacket();

        return payload;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleTilePacket(PacketCraftingPillar payload) {

        super.handleTilePacket(payload);

        callBlockUpdate();
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        // FIXME STATIC rendering helper function, FIELD STUFF
        return inventory.getField(0) > 0;
    }

    public void update() {/*
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (isBurning()) {
            furnaceBurnTime--;
        }

        if (!worldObj.isRemote) {
            ItemStack stackRaw = getStackInSlot(SLOT_RAW);
            ItemStack stackFuel = getStackInSlot(SLOT_FUEL);


            if (isBurning() || stackFuel != null && stackRaw != null) {
                if (!isBurning() && canSmelt()) {
                    furnaceBurnTime = TileEntityFurnace.getItemBurnTime(stackFuel);
                    currentItemBurnTime = furnaceBurnTime;

                    if (isBurning()) {
                        flag1 = true;

                        if (stackFuel != null) {
                            decrStackSize(SLOT_FUEL, 1);
                            if (stackFuel.stackSize == 0) {
                                setInventorySlotContents(SLOT_FUEL, stackFuel.getItem().getContainerItem(stackFuel));
                            }
                        }
                    }
                }

                if (isBurning() && canSmelt()) {
                    cookTime++;

                    if (cookTime == totalCookTime) {
                        cookTime = 0;
                        totalCookTime = getCookTime(stackRaw);
                        CraftingPillars.LOGGER.info("Smelting item");
                        smeltItem();
                        flag1 = true;
                    }
                } else {
                    cookTime = 0;
                }
            } else if (!isBurning() && cookTime > 0) {
                cookTime = MathHelper.clamp_int(cookTime - 2, 0, totalCookTime);
            }

            if (flag != isBurning()) {
                flag1 = true;
                // FIXME burning block state
//                BlockFurnace.setState(isBurning(), worldObj, pos);
            }
        }

        if (flag1) {
            markDirty();
            callBlockUpdate();
        }
*/

        // TODO if inventory is dirty, cause of automation update
//        if(getInternalInventory().renderUpdate)


        super.update(); // light and comparator
    }

    public int getCookTime(@Nullable ItemStack stack) {
        return 200;
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    public boolean canSmelt() {

        ItemStack stackRaw = getStackInSlot(SLOT_RAW);
        ItemStack stackFuel = getStackInSlot(SLOT_FUEL);
        ItemStack stackCooked = getStackInSlot(SLOT_COOKED);
        if (stackRaw == null) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(stackRaw);
            if (itemstack == null) return false;
            if (stackCooked == null) return true;
            if (!stackCooked.isItemEqual(itemstack)) return false;
            int result = stackCooked.stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= stackCooked.getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem() {
        if (canSmelt()) {

            ItemStack stackRaw = getStackInSlot(SLOT_RAW);
            ItemStack stackFuel = getStackInSlot(SLOT_FUEL);
            ItemStack stackCooked = getStackInSlot(SLOT_COOKED);

            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(stackRaw);

            if (stackCooked == null) {
                setInventorySlotContents(SLOT_COOKED, itemstack.copy());
//            } else if (stackCooked.isItemEqual(itemstack)) { // itemDamage sensitive
            } else if (stackCooked.getItem() == itemstack.getItem()) {
                int amount = stackCooked.stackSize + itemstack.stackSize;
                setInventorySlotContents(SLOT_COOKED, new ItemStack(stackCooked.getItem(), amount)); // Forge BugFix: Results may have multiple items
            }
/*
            // remove sponge filling bucket with water
            if (stackRaw.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && stackRaw.getMetadata() == 1 &&
                    stackFuel != null && stackFuel.getItem() == Items.BUCKET) {
                setInventorySlotContents(SLOT_FUEL, new ItemStack(Items.WATER_BUCKET));
            }*/
            decrStackSize(SLOT_RAW, 1);

            if (getStackInSlot(SLOT_RAW).stackSize <= 0) {

                setInventorySlotContents(SLOT_RAW, null);
            }
        }
    }

    @Override
    public int getLightValue() {
        return isBurning() ? 14 : 0;
    }
}