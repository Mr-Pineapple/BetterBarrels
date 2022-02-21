package co.uk.pinelogstudios.core;

import co.uk.pinelogstudios.client.screens.BarrelScreen;
import co.uk.pinelogstudios.core.registry.BlockRegistry;
import co.uk.pinelogstudios.core.registry.BlockEntityRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Author: Mr. Pineapple
 */
@Mod(BetterBarrels.MOD_ID)
public class BetterBarrels {
    public static final String MOD_ID = "pinesbarrels";
    public static final int SLOT_AMOUNT = 9;

    public BetterBarrels() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(this);
        BlockRegistry.REGISTER.register(bus);
        BlockRegistry.REGISTER_ITEM.register(bus);
        BlockEntityRegistry.REGISTER_BLOCK_ENTITY.register(bus);
        BlockEntityRegistry.REGISTER_CONTAINER.register(bus);
        bus.addListener(this::clientSetup);
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(BlockEntityRegistry.BARREL_CONTAINER.get(), BarrelScreen::new);
    }
}
