package co.uk.pinelogstudios.core.registry;

import co.uk.pinelogstudios.core.BetterBarrels;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;

/**
 * Author: Mr. Pineapple
 */
public class TagRegistry {
    public static final Tag.Named<Block> FORBIDDEN_BARREL_CONTENTS = BlockTags.bind(BetterBarrels.MOD_ID + ":forbidden_barrel_contents");
}
