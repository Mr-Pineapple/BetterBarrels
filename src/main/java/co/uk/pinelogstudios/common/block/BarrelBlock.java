package co.uk.pinelogstudios.common.block;

import co.uk.pinelogstudios.common.tileentity.BarrelTileEntity;
import co.uk.pinelogstudios.common.util.VoxelShapeUtil;
import co.uk.pinelogstudios.core.registry.BlockRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: Mr. Pineapple
 */
public class BarrelBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

    public BarrelBlock() {
        super(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(!world.isClientSide()) {
            if(world.getBlockEntity(pos) instanceof BarrelTileEntity tileEntity) {
                player.awardStat(Stats.OPEN_BARREL);
                NetworkHooks.openScreen((ServerPlayer) player, tileEntity, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch(state.getValue(FACING)) {
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
        return new BarrelTileEntity(blockPos, blockState);
    }

    /*
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
     */

    @Override
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.MODEL;
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if(tileEntity instanceof BarrelTileEntity) {
            BarrelTileEntity barrelTileEntity = (BarrelTileEntity) tileEntity;
            if(world.isClientSide && player.isCreative() && !barrelTileEntity.isEmpty()) {
                ItemStack itemStack = new ItemStack(BlockRegistry.BETTER_BARREL.get());
                CompoundTag compoundTag = barrelTileEntity.saveToTag(new CompoundTag());
                if(!compoundTag.isEmpty()) {
                    itemStack.addTagElement("BlockEntityTag", compoundTag);
                } if(barrelTileEntity.hasCustomName()) {
                    itemStack.setHoverName(barrelTileEntity.getCustomName());
                }

                ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, itemStack);
                itemEntity.setDefaultPickUpDelay();
                world.addFreshEntity(itemEntity);
            } else {
                barrelTileEntity.unpackLootTable(player);
            }
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        BlockEntity tileEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if(tileEntity instanceof BarrelTileEntity) {
            BarrelTileEntity barrelTileEntity = (BarrelTileEntity) tileEntity;
            builder = builder.withDynamicDrop(CONTENTS, (itemStackConsumer) -> {
                for(int i = 0; i < barrelTileEntity.getContainerSize(); ++i) {
                    itemStackConsumer.accept(barrelTileEntity.getItem(i));
                }
            });
        } return super.getDrops(state, builder);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if(stack.hasCustomHoverName()) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof BarrelTileEntity) {
                ((BarrelTileEntity)tileEntity).setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    public void onRemove(BlockState oldState, Level world, BlockPos pos, BlockState newState, boolean flags) {
        if(!oldState.is(newState.getBlock())) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof BarrelTileEntity) {
                world.updateNeighbourForOutputSignal(pos, oldState.getBlock());
            }
            super.onRemove(oldState, world, pos, newState, flags);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, world, tooltip, options);
        CompoundTag compoundTag = stack.getTagElement("BlockEntityTag");
        if(compoundTag != null) {
            if(compoundTag.contains("LootTable", 8)) {
                tooltip.add(Component.literal("???????"));
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
                    tooltip.add(Component.translatable("container.shulkerBox.more", j - i).withStyle(ChatFormatting.ITALIC));
                }
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        ItemStack itemStack = super.getCloneItemStack(world, pos, state);
        BarrelTileEntity barrelTileEntity = (BarrelTileEntity) world.getBlockEntity(pos);
        CompoundTag compoundTag = barrelTileEntity.saveToTag(new CompoundTag());
        if(!compoundTag.isEmpty()) {
            itemStack.addTagElement("BlockEntityTag", compoundTag);
        }
        return itemStack;
    }
}
