package co.uk.pinelogstudios.core.registry;

import co.uk.pinelogstudios.core.BetterBarrels;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;

/**
 * Author: Mr. Pineapple
 */
public class TagRegistry {
    public static final ITag.INamedTag<Block> FORBIDDEN_BARREL_CONTENTS = BlockTags.bind(BetterBarrels.MOD_ID + ":forbidden_barrel_contents");
}
