package co.uk.pinelogstudios.barrelsplus.client.screens;

import co.uk.pinelogstudios.barrelsplus.client.screens.slots.BarrelSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

/**
 *	Author:	Mr. Pineapple
 */
public class BarrelScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	
	public BarrelScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(11));
	}

	public BarrelScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(Screens.BARREL_SCREEN, syncId);
		checkSize(inventory, 11);
		this.inventory = inventory;
		inventory.onOpen(playerInventory.player);
		
		int o;
		int n;
		
		for(o = 0; o < 3; ++o) {
			this.addSlot(new BarrelSlot(this.inventory, o, 62 + o * 18, 18));
		}//BARREL TOP
		
		for(o = 0; o < 5; ++o) {
			this.addSlot(new BarrelSlot(this.inventory, o + 3, 44 + o * 18, 36));
		}//BARREL MIDDLE
		
		for(o = 0; o < 3; ++o) {
			this.addSlot(new BarrelSlot(this.inventory, o + 8, 62 + o * 18, 54));
		}//BARREL BOTTOM
		
		for(o = 0; o < 3; ++o) {
			for(n = 0; n < 9; ++n) {
				this.addSlot(new Slot(playerInventory, n + o * 9 + 9, 8 + n * 18, 84 + o * 18));
			}
		}//PLAYER INVENTORY
		
		for(o = 0; o < 9; ++o) {
	         this.addSlot(new Slot(playerInventory, o, 8 + o * 18, 142));
	      }//HOTBAR
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}
	
	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = (Slot)this.slots.get(index);
		if (slot != null && slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index < this.inventory.size()) {
				if (!this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}
			if (itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		return itemStack;
	}
	
	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		this.inventory.onClose(player);
	}

}
