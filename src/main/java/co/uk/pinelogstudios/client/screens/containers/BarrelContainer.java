package co.uk.pinelogstudios.client.screens.containers;

import co.uk.pinelogstudios.core.registry.TileEntityRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Author: Mr. Pineapple
 */
public class BarrelContainer extends Container {
    private final IInventory container;

    public BarrelContainer(int windowId, PlayerInventory playerInventory) {
        this(windowId, playerInventory, new Inventory(27));
    }

    public BarrelContainer(int windowId, PlayerInventory playerInventory, IInventory inventory) {
        super(TileEntityRegistry.BARREL_CONTAINER.get(), windowId);
        checkContainerSize(inventory, 27);
        this.container = inventory;
        inventory.startOpen(playerInventory.player);
        int n, o;

        for(o = 0; o < 3; ++o) {
            for(n = 0; n < 3; ++n) {
                this.addSlot(new Slot(inventory, n + o * 3, 62 + n * 18, 17 + o * 18));
            }
        }

        for(o = 0; o < 3; ++o) {
            for(n = 0; n < 9; ++n) {
                this.addSlot(new Slot(playerInventory, n + o * 9 + 9, 8 + n * 18, 84 + o * 18));
            }
        }
        for(o = 0; o < 9; ++o) {
            this.addSlot(new Slot(playerInventory, o, 8 + o * 18, 142));
        }

    }

    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemstack = itemStack1.copy();
            if (index < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(itemStack1, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void removed(PlayerEntity player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public boolean stillValid(PlayerEntity player) {
        return this.container.stillValid(player);
    }
}
