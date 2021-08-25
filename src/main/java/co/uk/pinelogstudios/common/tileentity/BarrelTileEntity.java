package co.uk.pinelogstudios.common.tileentity;

import co.uk.pinelogstudios.client.screens.containers.BarrelContainer;
import co.uk.pinelogstudios.common.block.BarrelBlock;
import co.uk.pinelogstudios.core.registry.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

/**
 * Author: Mr. Pineapple
 */
public class BarrelTileEntity extends LockableLootTileEntity implements ISidedInventory {
    private static final int[] SLOTS = IntStream.range(0, 27).toArray();
    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);

    public BarrelTileEntity() {
        super(TileEntityRegistry.BARREL.get());
    }

    @Override
    public int[] getSlotsForFace(Direction p_180463_1_) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return !((Block.byItem(itemStack.getItem())) instanceof ShulkerBoxBlock || (Block.byItem((itemStack.getItem())) instanceof BarrelBlock));
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction direction) {
        return true;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.itemStacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsStacks) {
        this.itemStacks = itemsStacks;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.barrel");
    }

    @Override
    protected Container createMenu(int windowId, PlayerInventory playerInventory) {
        return new BarrelContainer(windowId, playerInventory, this);
    }

    @Override
    public int getContainerSize() {
        return this.itemStacks.size();
    }
}
