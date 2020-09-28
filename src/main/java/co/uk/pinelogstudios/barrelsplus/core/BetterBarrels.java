package co.uk.pinelogstudios.barrelsplus.core;

import co.uk.pinelogstudios.barrelsplus.client.screens.Screens;
import co.uk.pinelogstudios.barrelsplus.core.registry.BlockEntityInit;
import co.uk.pinelogstudios.barrelsplus.core.registry.BlockInit;
import net.fabricmc.api.ModInitializer;

/**
 *	Author:	Mr. Pineapple
 */
public class BetterBarrels implements ModInitializer {
	
	public static final String MOD_ID = "pinesbarrels";
	
	@Override
	public void onInitialize() {
		BlockInit.init();
		BlockEntityInit.init();
		Screens.init();
	}
}
