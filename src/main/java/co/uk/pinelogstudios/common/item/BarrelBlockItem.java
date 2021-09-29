package co.uk.pinelogstudios.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

/**
 * Author: Autovw
 */
public class BarrelBlockItem extends BlockItem {
    public BarrelBlockItem(Block block, Properties builder) {
        super(block, builder);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
