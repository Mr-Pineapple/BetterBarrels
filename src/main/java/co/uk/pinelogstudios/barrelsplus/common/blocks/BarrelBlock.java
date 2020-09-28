package co.uk.pinelogstudios.barrelsplus.common.blocks;

import java.util.Iterator;
import java.util.List;

import co.uk.pinelogstudios.barrelsplus.common.blockentities.BarrelBlockEntity;
import co.uk.pinelogstudios.barrelsplus.core.BetterBarrels;
import co.uk.pinelogstudios.barrelsplus.core.VoxelShapeUtil;
import co.uk.pinelogstudios.barrelsplus.core.registry.BlockInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext.Builder;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 *	Author:	Mr. Pineapple
 */
public class BarrelBlock extends BlockWithEntity {
	public static final DirectionProperty FACING;
	public static final Identifier CONTENTS;
	
	public BarrelBlock() {
		super(AbstractBlock.Settings.of(Material.WOOD).strength(2.5F).sounds(BlockSoundGroup.WOOD).nonOpaque());
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new BarrelBlockEntity();
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if(world.isClient) {
			return ActionResult.SUCCESS;
		} else if(player.isSpectator()) {
			return ActionResult.CONSUME;
		} else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if(blockEntity instanceof BarrelBlockEntity) {
				BarrelBlockEntity barrelBlockEntity = (BarrelBlockEntity)blockEntity;
				player.openHandledScreen(barrelBlockEntity);
				player.incrementStat(Stats.OPEN_BARREL);
				return ActionResult.CONSUME;
			} else {
				return ActionResult.PASS;
			}
		}
	}
	
	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if(blockEntity instanceof BarrelBlockEntity) {
			BarrelBlockEntity barrelBlockEntity = (BarrelBlockEntity)blockEntity;
			if(!world.isClient && player.isCreative() && !barrelBlockEntity.isEmpty()) {
				ItemStack itemStack = new ItemStack(BlockInit.BARREL);
				CompoundTag compoundTag = barrelBlockEntity.serializeInventory(new CompoundTag());
				if (!compoundTag.isEmpty()) {
					itemStack.putSubTag("BlockEntityTag", compoundTag);
				} if (barrelBlockEntity.hasCustomName()) {
					itemStack.setCustomName(barrelBlockEntity.getCustomName());
				}
				ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemStack);
				itemEntity.setToDefaultPickupDelay();
				world.spawnEntity(itemEntity);
			} else {
				barrelBlockEntity.checkLootInteraction(player);
			}
		}
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, Builder builder) {
		BlockEntity blockEntity = builder.getNullable(LootContextParameters.BLOCK_ENTITY);
		if(blockEntity instanceof BarrelBlockEntity) {
			BarrelBlockEntity barrelBlockEntity = (BarrelBlockEntity)blockEntity;
			builder = builder.putDrop(CONTENTS, (lootContext, consumer) -> {
				for(int i = 0; i < barrelBlockEntity.size(); ++i) {
					consumer.accept(barrelBlockEntity.getStack(i));
				}
			});
		} return super.getDroppedStacks(state, builder);
	}
	
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if(itemStack.hasCustomName()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if(blockEntity instanceof BarrelBlockEntity) {
				((BarrelBlockEntity) blockEntity).setCustomName(itemStack.getName());
			}
		}
	}
	
	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if(!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if(blockEntity instanceof BarrelBlockEntity) {
				world.updateComparators(pos, state.getBlock());
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}
	
	@Override
	public void appendTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext options) {
		super.appendTooltip(stack, world, tooltip, options);
		CompoundTag compoundTag = stack.getSubTag("BlockEntityTag");
		if(compoundTag != null) {
			if(compoundTag.contains("LootTable", 8)) {
				tooltip.add(new LiteralText("???????"));
			} if(compoundTag.contains("Items", 9)) {
				DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(BetterBarrels.SLOT_AMOUNT, ItemStack.EMPTY);
				Inventories.fromTag(compoundTag, defaultedList);
				int i = 0;
				int j = 0;
				Iterator var9 = defaultedList.iterator();
				
				while(var9.hasNext()) {
					ItemStack itemStack = (ItemStack)var9.next();
					if(!itemStack.isEmpty()) {
						++j;
						if(i <= 4) {
							++i;
							MutableText mutableText = itemStack.getName().shallowCopy().formatted(Formatting.GREEN);
							mutableText.append(" x").append(String.valueOf(itemStack.getCount()));
							tooltip.add(mutableText);
						}
					}
				} if(j - i > 0) {
					tooltip.add((new TranslatableText("container.shulkerBox.more", new Object[]{j - i})).formatted(Formatting.ITALIC).formatted(Formatting.YELLOW));
				}
			}
		}
	}
	
	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		ItemStack itemStack = super.getPickStack(world, pos, state);
		BarrelBlockEntity BarrelBlockEntity = (BarrelBlockEntity)world.getBlockEntity(pos);
		CompoundTag compoundTag = BarrelBlockEntity.serializeInventory(new CompoundTag());
		if (!compoundTag.isEmpty()) {
			itemStack.putSubTag("BlockEntityTag", compoundTag);
		} return itemStack;
	}
	
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
	}
	
	@Override
	protected void appendProperties(net.minecraft.state.StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch(state.get(FACING)) {
		case NORTH:
			return VoxelShapeUtil.BARREL_NORTH;
		case EAST:
			return VoxelShapeUtil.BARREL_EAST;
		case SOUTH:
			return VoxelShapeUtil.BARREL_SOUTH;
		case WEST:
			return VoxelShapeUtil.BARREL_WEST;
		case UP:
			return VoxelShapeUtil.BARREL_UP;
		case DOWN:
			return VoxelShapeUtil.BARREL_DOWN;
		default:
			return VoxelShapeUtil.BARREL_UP;
		}
	}
	
	static {
		FACING = Properties.FACING;
		CONTENTS = new Identifier("contents");
	}
	
}