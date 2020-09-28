package co.uk.pinelogstudios.barrelsplus.common.blockentities;

import java.util.stream.IntStream;

import co.uk.pinelogstudios.barrelsplus.client.screens.BarrelScreenHandler;
import co.uk.pinelogstudios.barrelsplus.common.blocks.BarrelBlock;
import co.uk.pinelogstudios.barrelsplus.core.BetterBarrels;
import co.uk.pinelogstudios.barrelsplus.core.registry.BlockEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

/**
 *	Author:	Mr. Pineapple
 */
public class BarrelBlockEntity extends LootableContainerBlockEntity implements SidedInventory {
	private static final int[] SLOTS = IntStream.range(0, BetterBarrels.SLOT_AMOUNT).toArray();
	private DefaultedList<ItemStack> inventory;
	private int viewerCount;
	
	public BarrelBlockEntity() {
		super(BlockEntityInit.BARREL);
		this.inventory = DefaultedList.ofSize(BetterBarrels.SLOT_AMOUNT, ItemStack.EMPTY);
	}
	
	/* Gets the size of the inventory */
	@Override
	public int size() {
		return this.inventory.size();
	}
	
	@Override
	public boolean onSyncedBlockEvent(int type, int data) {
		if(type == 1) {
			this.viewerCount = data;
			if(data == 0) {
				this.updateNeighborStates();
			} if(data == 1) {
				this.toUpdatePacket();
			} return true;
		} else {
			return super.onSyncedBlockEvent(type, data);
		}
	}
	
	private void updateNeighborStates() {
		this.getCachedState().updateNeighbors(this.getWorld(), this.getPos(), 3);
	}
	
	@Override
	public void onOpen(PlayerEntity player) {
		if(!player.isSpectator()) {
			if(this.viewerCount < 0) {
				this.viewerCount = 0;
			}
			
			++this.viewerCount;
			this.world.addSyncedBlockEvent(this.pos, this.getCachedState().getBlock(), 1, this.viewerCount);
			if(this.viewerCount == 1) {
				this.world.playSound((PlayerEntity)null, this.pos, SoundEvents.BLOCK_BARREL_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}
	
	@Override
	public void onClose(PlayerEntity player) {
		if(!player.isSpectator()) {
			--this.viewerCount;
			this.world.addSyncedBlockEvent(this.pos, this.getCachedState().getBlock(), 1, this.viewerCount);
			if (this.viewerCount <= 0) {
				this.world.playSound((PlayerEntity)null, this.pos, SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}
	
	@Override
	protected Text getContainerName() {
		return new TranslatableText("container.barrel");
	}
	
	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		this.deserializeInventory(tag);
	}
	
	@Override
	public CompoundTag toTag(CompoundTag tag) {
		super.toTag(tag);
		return this.serializeInventory(tag);
	}
	
	public void deserializeInventory(CompoundTag tag) {
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		if (!this.deserializeLootTable(tag) && tag.contains("Items", 9)) {
			Inventories.fromTag(tag, this.inventory);
		}
	}
	
	public CompoundTag serializeInventory(CompoundTag tag) {
		if (!this.serializeLootTable(tag)) {
			Inventories.toTag(tag, this.inventory, false);
		}
		return tag;
	}
	
	@Override
	protected DefaultedList<ItemStack> getInvStackList() {
		return this.inventory;
	}
	
	@Override
	protected void setInvStackList(DefaultedList<ItemStack> list) {
		this.inventory = list;
	}
	
	@Override
	public int[] getAvailableSlots(Direction side) {
		return SLOTS;
	}
	
	@Override
	public boolean canInsert(int slot, ItemStack stack, Direction dir) {
		return !(Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock || Block.getBlockFromItem(stack.getItem()) instanceof BarrelBlock);
	}
	
	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return true;
	}
	
	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new BarrelScreenHandler(syncId, playerInventory, this);
	}
}
