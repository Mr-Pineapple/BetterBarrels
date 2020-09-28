package co.uk.pinelogstudios.barrelsplus.core.registry;

import java.util.function.Supplier;

import co.uk.pinelogstudios.barrelsplus.common.blockentities.BarrelBlockEntity;
import co.uk.pinelogstudios.barrelsplus.core.BetterBarrels;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 *	Author:	Mr. Pineapple
 */
public class BlockEntityInit {
	
	public static BlockEntityType<BarrelBlockEntity> BARREL = register(BarrelBlockEntity::new, BlockInit.BARREL, "better_barrel");

	private static <T extends BlockEntity> BlockEntityType<T> register(Supplier<T> blockEntitySupplier, Block block, String name) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(BetterBarrels.MOD_ID, name), BlockEntityType.Builder.create(blockEntitySupplier, block).build(null));
    }
	
	public static void init() {}
}
