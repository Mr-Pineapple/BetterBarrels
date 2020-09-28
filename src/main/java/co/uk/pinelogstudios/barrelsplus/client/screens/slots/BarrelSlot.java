package co.uk.pinelogstudios.barrelsplus.client.screens.slots;

import co.uk.pinelogstudios.barrelsplus.common.blocks.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

/**
 *	Author:	Mr. Pineapple
 */
public class BarrelSlot extends Slot {
	public BarrelSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean canInsert(ItemStack stack) {
		return !(Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock || Block.getBlockFromItem(stack.getItem()) instanceof BarrelBlock);
	}

}
