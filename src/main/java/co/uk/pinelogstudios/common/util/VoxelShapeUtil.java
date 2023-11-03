package co.uk.pinelogstudios.common.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

/**
 * Author: Mr. Pineapple
 */
public class VoxelShapeUtil {


    public static final VoxelShape BARREL_UP = Stream.of(
            Block.box(1, 0, 1, 15, 16, 15)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();
    public static final VoxelShape BARREL_NORTH = Stream.of(
            Block.box(1, 0, 0, 15, 14, 16)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();
    public static VoxelShape BARREL_EAST = Stream.of(
            Block.box(0, 0, 1, 16, 14, 15)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();
    public static VoxelShape BARREL_SOUTH = Stream.of(
            Block.box(1, 0, 0, 15, 14, 16)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();
    public static VoxelShape BARREL_WEST = Stream.of(
            Block.box(0, 0, 1, 16, 14, 15)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();

}
