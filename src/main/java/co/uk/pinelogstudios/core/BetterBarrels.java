package co.uk.pinelogstudios.core;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Author: Mr. Pineapple
 */
@Mod(BetterBarrels.MOD_ID)
public class BetterBarrels {
    public static final String MOD_ID = "pinesbarrels";
    public static final int SLOT_AMOUNT = 9;

    public BetterBarrels() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }
}
