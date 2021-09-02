package co.uk.pinelogstudios.common.util;

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
            Block.box(5, 15, 5, 6, 16, 7),
            Block.box(6, 15, 6, 7, 16, 7),
            Block.box(6, 15, 5, 7, 16, 6),
            Block.box(14, 1, 2, 15, 16, 14),
            Block.box(2, 0, 2, 14, 1, 14),
            Block.box(2, 1, 1, 14, 16, 2),
            Block.box(2, 1, 14, 14, 16, 15),
            Block.box(1, 1, 2, 2, 16, 14),
            Block.box(3, 14.75, 3, 13, 15.75, 13),
            Block.box(3, 14.75, 13, 13, 15.75, 14),
            Block.box(3, 14.75, 2, 13, 15.75, 3),
            Block.box(13, 14.75, 3, 14, 15.75, 13),
            Block.box(2, 14.75, 3, 3, 15.75, 13),
            Block.box(2, 15, 2, 3, 16, 3),
            Block.box(2, 15, 13, 3, 16, 14),
            Block.box(13, 15, 13, 14, 16, 14),
            Block.box(13, 15, 2, 14, 16, 3),
            Block.box(1.75, 12, 0.75, 14.25, 14, 2),
            Block.box(1.75, 4, 0.75, 14.25, 6, 2),
            Block.box(1.75, 12, 14, 14.25, 14, 15.25),
            Block.box(1.75, 4, 14, 14.25, 6, 15.25),
            Block.box(0.75, 12, 1.75, 1.75, 14, 14.25),
            Block.box(14.25, 4, 1.75, 15.25, 6, 14.25),
            Block.box(14.25, 12, 1.75, 15.25, 14, 14.25),
            Block.box(0.75, 4, 1.75, 1.75, 6, 14.25)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();

    public static final VoxelShape BARREL_DOWN = Stream.of(
            Block.box(5, 0, 5, 6, 1, 7),
            Block.box(6, 0, 6, 7, 1, 7),
            Block.box(6, 0, 5, 7, 1, 6),
            Block.box(14, 0, 2, 15, 15, 14),
            Block.box(2, 15, 2, 14, 16, 14),
            Block.box(2, 0, 1, 14, 15, 2),
            Block.box(2, 0, 14, 14, 15, 15),
            Block.box(1, 0, 2, 2, 15, 14),
            Block.box(3, 0.25, 3, 13, 1.25, 13),
            Block.box(3, 0.25, 13, 13, 1.25, 14),
            Block.box(3, 0.25, 2, 13, 1.25, 3),
            Block.box(13, 0.25, 3, 14, 1.25, 13),
            Block.box(2, 0.25, 3, 3, 1.25, 13),
            Block.box(2, 0, 2, 3, 1, 3),
            Block.box(2, 0, 13, 3, 1, 14),
            Block.box(13, 0, 13, 14, 1, 14),
            Block.box(13, 0, 2, 14, 1, 3),
            Block.box(1.75, 2, 0.75, 14.25, 4, 2),
            Block.box(1.75, 10, 0.75, 14.25, 12, 2),
            Block.box(1.75, 2, 14, 14.25, 4, 15.25),
            Block.box(1.75, 10, 14, 14.25, 12, 15.25),
            Block.box(0.75, 2, 1.75, 1.75, 4, 14.25),
            Block.box(14.25, 10, 1.75, 15.25, 12, 14.25),
            Block.box(14.25, 2, 1.75, 15.25, 4, 14.25),
            Block.box(0.75, 10, 1.75, 1.75, 12, 14.25)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();

    public static final VoxelShape BARREL_NORTH = Stream.of(
            Block.box(9, 4.25, 0, 11, 5.25, 1),
            Block.box(9, 5.25, 0, 10, 6.25, 1),
            Block.box(10, 5.25, 0, 11, 6.25, 1),
            Block.box(2, 13.25, 0, 14, 14.25, 15),
            Block.box(2, 1.25, 15, 14, 13.25, 16),
            Block.box(14, 1.25, 0, 15, 13.25, 15),
            Block.box(1, 1.25, 0, 2, 13.25, 15),
            Block.box(2, 0.25, 0, 14, 1.25, 15),
            Block.box(3, 2.25, 0.25, 13, 12.25, 1.25),
            Block.box(2, 2.25, 0.25, 3, 12.25, 1.25),
            Block.box(13, 2.25, 0.25, 14, 12.25, 1.25),
            Block.box(3, 12.25, 0.25, 13, 13.25, 1.25),
            Block.box(3, 1.25, 0.25, 13, 2.25, 1.25),
            Block.box(13, 1.25, 0, 14, 2.25, 1),
            Block.box(2, 1.25, 0, 3, 2.25, 1),
            Block.box(2, 12.25, 0, 3, 13.25, 1),
            Block.box(13, 12.25, 0, 14, 13.25, 1),
            Block.box(14, 1, 2, 15.25, 13.5, 4),
            Block.box(14, 1, 10, 15.25, 13.5, 12),
            Block.box(0.75, 1, 2, 2, 13.5, 4),
            Block.box(0.75, 1, 10, 2, 13.5, 12),
            Block.box(1.75, 0, 2, 14.25, 1, 4),
            Block.box(1.75, 13.5, 10, 14.25, 14.5, 12),
            Block.box(1.75, 13.5, 2, 14.25, 14.5, 4),
            Block.box(1.75, 0, 10, 14.25, 1, 12)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();

    public static final VoxelShape BARREL_EAST = Stream.of(
            Block.box(15, 4.25, 9, 16, 5.25, 11),
            Block.box(15, 5.25, 9, 16, 6.25, 10),
            Block.box(15, 5.25, 10, 16, 6.25, 11),
            Block.box(1, 13.25, 2, 16, 14.25, 14),
            Block.box(0, 1.25, 2, 1, 13.25, 14),
            Block.box(1, 1.25, 14, 16, 13.25, 15),
            Block.box(1, 1.25, 1, 16, 13.25, 2),
            Block.box(1, 0.25, 2, 16, 1.25, 14),
            Block.box(14.75, 2.25, 3, 15.75, 12.25, 13),
            Block.box(14.75, 2.25, 2, 15.75, 12.25, 3),
            Block.box(14.75, 2.25, 13, 15.75, 12.25, 14),
            Block.box(14.75, 12.25, 3, 15.75, 13.25, 13),
            Block.box(14.75, 1.25, 3, 15.75, 2.25, 13),
            Block.box(15, 1.25, 13, 16, 2.25, 14),
            Block.box(15, 1.25, 2, 16, 2.25, 3),
            Block.box(15, 12.25, 2, 16, 13.25, 3),
            Block.box(15, 12.25, 13, 16, 13.25, 14),
            Block.box(12, 1, 14, 14, 13.5, 15.25),
            Block.box(4, 1, 14, 6, 13.5, 15.25),
            Block.box(12, 1, 0.75, 14, 13.5, 2),
            Block.box(4, 1, 0.75, 6, 13.5, 2),
            Block.box(12, 0, 1.75, 14, 1, 14.25),
            Block.box(4, 13.5, 1.75, 6, 14.5, 14.25),
            Block.box(12, 13.5, 1.75, 14, 14.5, 14.25),
            Block.box(4, 0, 1.75, 6, 1, 14.25)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();


    public static final VoxelShape BARREL_SOUTH = Stream.of(
            Block.box(5, 4.25, 15, 7, 5.25, 16),
            Block.box(6, 5.25, 15, 7, 6.25, 16),
            Block.box(5, 5.25, 15, 6, 6.25, 16),
            Block.box(2, 13.25, 1, 14, 14.25, 16),
            Block.box(2, 1.25, 0, 14, 13.25, 1),
            Block.box(1, 1.25, 1, 2, 13.25, 16),
            Block.box(14, 1.25, 1, 15, 13.25, 16),
            Block.box(2, 0.25, 1, 14, 1.25, 16),
            Block.box(3, 2.25, 14.75, 13, 12.25, 15.75),
            Block.box(13, 2.25, 14.75, 14, 12.25, 15.75),
            Block.box(2, 2.25, 14.75, 3, 12.25, 15.75),
            Block.box(3, 12.25, 14.75, 13, 13.25, 15.75),
            Block.box(3, 1.25, 14.75, 13, 2.25, 15.75),
            Block.box(2, 1.25, 15, 3, 2.25, 16),
            Block.box(13, 1.25, 15, 14, 2.25, 16),
            Block.box(13, 12.25, 15, 14, 13.25, 16),
            Block.box(2, 12.25, 15, 3, 13.25, 16),
            Block.box(0.75, 1, 12, 2, 13.5, 14),
            Block.box(0.75, 1, 4, 2, 13.5, 6),
            Block.box(14, 1, 12, 15.25, 13.5, 14),
            Block.box(14, 1, 4, 15.25, 13.5, 6),
            Block.box(1.75, 0, 12, 14.25, 1, 14),
            Block.box(1.75, 13.5, 4, 14.25, 14.5, 6),
            Block.box(1.75, 13.5, 12, 14.25, 14.5, 14),
            Block.box(1.75, 0, 4, 14.25, 1, 6)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();

    public static final VoxelShape BARREL_WEST = Stream.of(
            Block.box(0, 4.25, 5, 1, 5.25, 7),
            Block.box(0, 5.25, 6, 1, 6.25, 7),
            Block.box(0, 5.25, 5, 1, 6.25, 6),
            Block.box(0, 13.25, 2, 15, 14.25, 14),
            Block.box(15, 1.25, 2, 16, 13.25, 14),
            Block.box(0, 1.25, 1, 15, 13.25, 2),
            Block.box(0, 1.25, 14, 15, 13.25, 15),
            Block.box(0, 0.25, 2, 15, 1.25, 14),
            Block.box(0.25, 2.25, 3, 1.25, 12.25, 13),
            Block.box(0.25, 2.25, 13, 1.25, 12.25, 14),
            Block.box(0.25, 2.25, 2, 1.25, 12.25, 3),
            Block.box(0.25, 12.25, 3, 1.25, 13.25, 13),
            Block.box(0.25, 1.25, 3, 1.25, 2.25, 13),
            Block.box(0, 1.25, 2, 1, 2.25, 3),
            Block.box(0, 1.25, 13, 1, 2.25, 14),
            Block.box(0, 12.25, 13, 1, 13.25, 14),
            Block.box(0, 12.25, 2, 1, 13.25, 3),
            Block.box(2, 1, 0.75, 4, 13.5, 2),
            Block.box(10, 1, 0.75, 12, 13.5, 2),
            Block.box(2, 1, 14, 4, 13.5, 15.25),
            Block.box(10, 1, 14, 12, 13.5, 15.25),
            Block.box(2, 0, 1.75, 4, 1, 14.25),
            Block.box(10, 13.5, 1.75, 12, 14.5, 14.25),
            Block.box(2, 13.5, 1.75, 4, 14.5, 14.25),
            Block.box(10, 0, 1.75, 12, 1, 14.25)
    ).reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();
}
