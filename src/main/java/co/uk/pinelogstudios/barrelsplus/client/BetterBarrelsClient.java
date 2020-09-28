package co.uk.pinelogstudios.barrelsplus.client;

import co.uk.pinelogstudios.barrelsplus.client.screens.Screens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 *	Author:	Mr. Pineapple
 */
@Environment(EnvType.CLIENT)
public class BetterBarrelsClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		Screens.registerScreens();
	}
	
}
