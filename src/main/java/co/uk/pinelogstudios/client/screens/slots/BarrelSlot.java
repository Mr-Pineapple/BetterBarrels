package co.uk.pinelogstudios.client.screens.slots;

import co.uk.pinelogstudios.core.registry.TagRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
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
        //return !(Block.byItem(itemStack.getItem()).is(TagRegistry.FORBIDDEN_BARREL_CONTENTS));
        return itemStack.getItem().canFitInsideContainerItems();
    }
}
