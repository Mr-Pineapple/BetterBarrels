package co.uk.pinelogstudios.core.registry;

import co.uk.pinelogstudios.common.block.BarrelBlock;
import co.uk.pinelogstudios.common.item.BarrelBlockItem;
import co.uk.pinelogstudios.core.BetterBarrels;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Author: Mr. Pineapple
 */
public class BlockRegistry {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterBarrels.MOD_ID);
    public static final DeferredRegister<Item> REGISTER_ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, BetterBarrels.MOD_ID);

    public static final RegistryObject<Block> BETTER_BARREL = register("better_barrel", BarrelBlock::new, CreativeModeTab.TAB_MISC);

    public static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends B> supplier, @Nullable CreativeModeTab group) {
        RegistryObject<B> block = REGISTER.register(name, supplier);
        REGISTER_ITEM.register(name, () -> new BarrelBlockItem(block.get(), new Item.Properties().tab(group).stacksTo(1)));
        return block;
    }
}
