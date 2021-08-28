package co.uk.pinelogstudios.core.registry;

import co.uk.pinelogstudios.client.screens.containers.BarrelContainer;
import co.uk.pinelogstudios.common.tileentity.BarrelTileEntity;
import co.uk.pinelogstudios.core.BetterBarrels;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Author: Mr. Pineapple
 */
public class TileEntityRegistry {
    public static final DeferredRegister<TileEntityType<?>> REGISTER_TILE_ENTITY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BetterBarrels.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> REGISTER_CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, BetterBarrels.MOD_ID);

    public static final RegistryObject<TileEntityType<BarrelTileEntity>> BARREL = REGISTER_TILE_ENTITY.register("better_barrel", () -> TileEntityType.Builder.of(BarrelTileEntity::new, new Block[]{BlockRegistry.BETTER_BARREL.get()}).build(null));
    public static final RegistryObject<ContainerType<BarrelContainer>> BARREL_CONTAINER = register("better_barrel", BarrelContainer::new);

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String id, ContainerType.IFactory<T> factory) {
        return REGISTER_CONTAINER.register(id, () -> new ContainerType<>(factory));
    }

//    private static <T extends Container> ContainerType<T> register(String s, ContainerType.IFactory<T> factory) {
//        return Registry.register(Registry.MENU, s, new ContainerType<>(factory));
//    }
}
