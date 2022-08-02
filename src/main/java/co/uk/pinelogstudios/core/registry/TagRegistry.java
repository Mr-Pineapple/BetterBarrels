package co.uk.pinelogstudios.core.registry;

import co.uk.pinelogstudios.core.BetterBarrels;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Author: Mr. Pineapple
 */
public class TagRegistry {
    private static TagKey<Item> createTag(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(BetterBarrels.MOD_ID, name));
    }

    public static final TagKey<Item> FORBIDDEN_BARREL_CONTENT = createTag("forbidden_barrel_contents");
}
