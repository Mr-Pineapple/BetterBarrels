package co.uk.pinelogstudios.core.registry;

import co.uk.pinelogstudios.client.screens.containers.BarrelContainer;
import co.uk.pinelogstudios.common.tileentity.BarrelTileEntity;
import co.uk.pinelogstudios.core.BetterBarrels;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Author: Mr. Pineapple
 */
public class TileEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER_TILE_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BetterBarrels.MOD_ID);
    public static final DeferredRegister<MenuType<?>> REGISTER_CONTAINER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BetterBarrels.MOD_ID);

    public static final RegistryObject<BlockEntityType<BarrelTileEntity>> BARREL = REGISTER_TILE_ENTITY.register("better_barrel", () -> BlockEntityType.Builder.of(BarrelTileEntity::new, new Block[]{BlockRegistry.BETTER_BARREL.get()}).build(null));
    public static final RegistryObject<MenuType<BarrelContainer>> BARREL_CONTAINER = register("better_barrel", BarrelContainer::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String id, MenuType.MenuSupplier<T> factory) {
        return REGISTER_CONTAINER.register(id, () -> new MenuType<>(factory));
    }
}
