package co.uk.pinelogstudios.client.screens.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Author: Mr. Pineapple
 */
public class BarrelSlot extends Slot {

    public BarrelSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        // The commented code no longer works on 1.17+, which means the tag has no longer any use. Replaced it with something else instead. - Autovw
        //return !(Block.byItem(itemStack.getItem()).is(TagRegistry.FORBIDDEN_BARREL_CONTENTS));
        return itemStack.getItem().canFitInsideContainerItems();
    }
}
