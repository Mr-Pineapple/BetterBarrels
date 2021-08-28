package co.uk.pinelogstudios.common.block;

import co.uk.pinelogstudios.common.tileentity.BarrelTileEntity;
import co.uk.pinelogstudios.common.util.VoxelShapeUtil;
import co.uk.pinelogstudios.core.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: Mr. Pineapple
 */
public class BarrelBlock extends ContainerBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

    public BarrelBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else if(player.isSpectator()) {
            return ActionResultType.CONSUME;
        } else {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof BarrelTileEntity) {
                BarrelTileEntity barrelTileEntity = (BarrelTileEntity) tileEntity;
                player.openMenu(barrelTileEntity);
                player.awardStat(Stats.OPEN_BARREL);
                return ActionResultType.CONSUME;
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getStateDefinition().any().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new BarrelTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if(tileEntity instanceof BarrelTileEntity) {
            BarrelTileEntity barrelTileEntity = (BarrelTileEntity) tileEntity;
            if(world.isClientSide && player.isCreative() && !barrelTileEntity.isEmpty()) {
                ItemStack itemStack = new ItemStack(BlockRegistry.BETTER_BARREL.get());
                CompoundNBT compoundTag = barrelTileEntity.saveToTag(new CompoundNBT());
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
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        TileEntity tileEntity = builder.getOptionalParameter(LootParameters.BLOCK_ENTITY);
        if(tileEntity instanceof BarrelTileEntity) {
            BarrelTileEntity barrelTileEntity = (BarrelTileEntity) tileEntity;
            builder = builder.withDynamicDrop(CONTENTS, (lootContext, itemStackConsumer) -> {
                for(int i = 0; i < barrelTileEntity.getContainerSize(); ++i) {
                    itemStackConsumer.accept(barrelTileEntity.getItem(i));
                }
            });
        } return super.getDrops(state, builder);
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if(stack.hasCustomHoverName()) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof BarrelTileEntity) {
                ((BarrelTileEntity)tileEntity).setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    public void onRemove(BlockState oldState, World world, BlockPos pos, BlockState newState, boolean flags) {
        if(!oldState.is(newState.getBlock())) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof BarrelTileEntity) {
                world.updateNeighbourForOutputSignal(pos, oldState.getBlock());
            }
            super.onRemove(oldState, world, pos, newState, flags);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag options) {
        super.appendHoverText(stack, world, tooltip, options);
        CompoundNBT compoundTag = stack.getTagElement("BlockEntityTag");
        if(compoundTag != null) {
            if(compoundTag.contains("LootTable", 8)) {
                tooltip.add(new StringTextComponent("???????"));
            }
            if(compoundTag.contains("Items", 9)) {
                NonNullList<ItemStack> nonNullList = NonNullList.withSize(27, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(compoundTag, nonNullList);
                int i = 0, j = 0;
                for(ItemStack itemStack : nonNullList) {
                    if(!itemStack.isEmpty()) {
                        ++j;
                        if(i <= 4) {
                            ++i;
                            IFormattableTextComponent iFormattableTextComponent = itemStack.getHoverName().copy();
                            iFormattableTextComponent.append(" x").append(String.valueOf(itemStack.getCount()));
                            tooltip.add(iFormattableTextComponent);
                        }
                    }
                }
                if(j - i > 0) {
                    tooltip.add((new TranslationTextComponent("container.shulkerBox.more", j - i)).withStyle(TextFormatting.ITALIC));
                }
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader world, BlockPos pos, BlockState state) {
        ItemStack itemStack = super.getCloneItemStack(world, pos, state);
        BarrelTileEntity barrelTileEntity = (BarrelTileEntity) world.getBlockEntity(pos);
        CompoundNBT compoundTag = barrelTileEntity.saveToTag(new CompoundNBT());
        if(!compoundTag.isEmpty()) {
            itemStack.addTagElement("BlockEntityTag", compoundTag);
        }
        return itemStack;
    }
}
