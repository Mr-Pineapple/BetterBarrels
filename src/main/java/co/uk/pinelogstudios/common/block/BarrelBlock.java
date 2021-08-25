package co.uk.pinelogstudios.common.block;

import co.uk.pinelogstudios.common.util.VoxelShapeUtil;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

/**
 * Author: Mr. Pineapple
 */
public class BarrelBlock extends ContainerBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    public BarrelBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
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
        return null;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }
}
