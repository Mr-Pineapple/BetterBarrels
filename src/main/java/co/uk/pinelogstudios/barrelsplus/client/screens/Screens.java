package co.uk.pinelogstudios.barrelsplus.client.screens;

import co.uk.pinelogstudios.barrelsplus.core.BetterBarrels;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.SimpleClientHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

/**
 *	Author:	Mr. Pineapple
 */
public class Screens {
	
	//public static final ScreenHandlerType<PotteryWheelScreenHandler> POTTERY_WHEEL_SCREEN = registerScreen(PotteryWheelScreenHandler::new, "pottery_wheel");
	public static final ScreenHandlerType<BarrelScreenHandler> BARREL_SCREEN = register(BarrelScreenHandler::new, "better_barrel");
	
	public static void registerScreens() {
		ScreenRegistry.register(BARREL_SCREEN, BarrelScreen::new);
	}
	
	private static <T extends ScreenHandler> ScreenHandlerType<T> register(SimpleClientHandlerFactory<T> screenHandlerType, String name) {
        return ScreenHandlerRegistry.registerSimple(new Identifier(BetterBarrels.MOD_ID, name), screenHandlerType);
    }
	
	public static void init() {}
	
}
