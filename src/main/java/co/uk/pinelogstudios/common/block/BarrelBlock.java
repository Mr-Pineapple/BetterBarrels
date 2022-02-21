package co.uk.pinelogstudios.common.block;

import co.uk.pinelogstudios.common.blockentity.BarrelBlockEntity;
import co.uk.pinelogstudios.common.util.VoxelShapeUtil;
import co.uk.pinelogstudios.core.registry.BlockRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: Mr. Pineapple
 */
public class BarrelBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

    public BarrelBlock() {
        super(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }
    // TODO remember to commit loot_table fix in a separate commit

    /**
     * Some methods, including {@link BlockBehaviour#use(BlockState, Level, BlockPos, Player, InteractionHand, BlockHitResult)},
     * are marked as {@link Deprecated} but they are still fine to {@link Override}.
     * It is recommended however not to call them directly, but to use similar methods from other classes.
     */
    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if(player.isSpectator()) {
            return InteractionResult.CONSUME;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BarrelBlockEntity) {
                BarrelBlockEntity barrelBlockEntity = (BarrelBlockEntity) blockEntity;
                player.openMenu(barrelBlockEntity);
                player.awardStat(Stats.OPEN_BARREL);
                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch(state.getValue(FACING)) {
            case UP:
                return VoxelShapeUtil.BARREL_UP;
            case DOWN:
                return VoxelShapeUtil.BARREL_DOWN;
            case NORTH:
                return VoxelShapeUtil.BARREL_NORTH;
            case EAST:
                return VoxelShapeUtil.BARREL_EAST;
            case SOUTH:
                return VoxelShapeUtil.BARREL_SOUTH;
            case WEST:
                return VoxelShapeUtil.BARREL_WEST;
            default:
                return VoxelShapeUtil.BARREL_UP;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getStateDefinition().any().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BarrelBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BarrelBlockEntity) {
            BarrelBlockEntity barrelBlockEntity = (BarrelBlockEntity) blockEntity;
            if (!level.isClientSide && player.isCreative() && !barrelBlockEntity.isEmpty()) {
                ItemStack itemStack = new ItemStack(BlockRegistry.BETTER_BARREL.get());
                blockEntity.saveToItem(itemStack);
                if (barrelBlockEntity.hasCustomName()) {
                    itemStack.setHoverName(barrelBlockEntity.getCustomName());
                }

                ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            } else {
                barrelBlockEntity.unpackLootTable(player);
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if(blockEntity instanceof BarrelBlockEntity) {
            BarrelBlockEntity barrelBlockEntity = (BarrelBlockEntity) blockEntity;
            builder = builder.withDynamicDrop(CONTENTS, (lootContext, itemStackConsumer) -> {
                for(int i = 0; i < barrelBlockEntity.getContainerSize(); ++i) {
                    itemStackConsumer.accept(barrelBlockEntity.getItem(i));
                }
            });
        } return super.getDrops(state, builder);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if(stack.hasCustomHoverName()) {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if(tileEntity instanceof BarrelBlockEntity) {
                ((BarrelBlockEntity)tileEntity).setCustomName(stack.getHoverName());
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean flags) {
        if(!oldState.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof BarrelBlockEntity) {
                level.updateNeighbourForOutputSignal(pos, oldState.getBlock());
            }
            super.onRemove(oldState, level, pos, newState, flags);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, world, tooltip, options);
        CompoundTag compoundTag = stack.getTagElement("BlockEntityTag");
        if(compoundTag != null) {
            if(compoundTag.contains("LootTable", 8)) {
                tooltip.add(new TextComponent("???????"));
            }
            if(compoundTag.contains("Items", 9)) {
                NonNullList<ItemStack> nonNullList = NonNullList.withSize(27, ItemStack.EMPTY);
                ContainerHelper.loadAllItems(compoundTag, nonNullList);
                int i = 0, j = 0;
                for(ItemStack itemStack : nonNullList) {
                    if(!itemStack.isEmpty()) {
                        ++j;
                        if(i <= 4) {
                            ++i;
                            MutableComponent iFormattableTextComponent = itemStack.getHoverName().copy();
                            iFormattableTextComponent.append(" x").append(String.valueOf(itemStack.getCount()));
                            tooltip.add(iFormattableTextComponent);
                        }
                    }
                }
                if(j - i > 0) {
                    tooltip.add((new TranslatableComponent("container.shulkerBox.more", j - i)).withStyle(ChatFormatting.ITALIC));
                }
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack itemStack = super.getCloneItemStack(state, target, world, pos, player);
        world.getBlockEntity(pos, BlockEntityType.BARREL).ifPresent((nbt) -> {
            nbt.saveToItem(itemStack);
        });
        return itemStack;
    }
}
