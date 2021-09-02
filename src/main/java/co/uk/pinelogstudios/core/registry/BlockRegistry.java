package co.uk.pinelogstudios.core.registry;

import co.uk.pinelogstudios.common.block.BarrelBlock;
import co.uk.pinelogstudios.core.BetterBarrels;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Author: Mr. Pineapple
 */
public class BlockRegistry {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterBarrels.MOD_ID);
    public static final DeferredRegister<Item> REGISTER_ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, BetterBarrels.MOD_ID);

    public static final RegistryObject<Block> BETTER_BARREL = register("better_barrel", BarrelBlock::new, ItemGroup.TAB_MISC);

    public static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends B> supplier, @Nullable ItemGroup group) {
        RegistryObject<B> block = REGISTER.register(name, supplier);
        REGISTER_ITEM.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(group).stacksTo(1)));
        return block;
    }
}
