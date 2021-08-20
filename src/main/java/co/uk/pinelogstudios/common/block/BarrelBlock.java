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

    public BarrelBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD));
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
