package co.uk.pinelogstudios.barrelsplus.core.registry;

import co.uk.pinelogstudios.barrelsplus.common.blocks.BarrelBlock;
import co.uk.pinelogstudios.barrelsplus.core.BetterBarrels;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 *	Author:	Mr. Pineapple
 */
public class BlockInit {
	
	public static final Block BARREL = registerBarrel(new BarrelBlock(), "better_barrel");
	
	private static Block registerBarrel(Block block, String name) {
		Registry.register(Registry.ITEM, new Identifier(BetterBarrels.MOD_ID, name), new BlockItem(block, new FabricItemSettings().maxCount(1).group(ItemGroup.DECORATIONS)));
		return Registry.register(Registry.BLOCK, new Identifier(BetterBarrels.MOD_ID, name), block);
	}
	
	public static void init() {}

}
