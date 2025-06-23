package fuzs.bagofholding.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.client.gui.screens.inventory.BagItemScreen;
import fuzs.bagofholding.client.handler.SlotOverlayHandler;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.MenuScreensContext;
import fuzs.puzzleslib.api.client.event.v1.gui.ContainerScreenEvents;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;

public class BagOfHoldingClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        ConfigHolder.registerConfigurationScreen(BagOfHolding.MOD_ID, "iteminteractions");
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ContainerScreenEvents.FOREGROUND.register(SlotOverlayHandler::onDrawForeground);
    }

    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        context.registerMenuScreen(ModRegistry.LEATHER_BAG_OF_HOLDING_MENU_TYPE.value(), BagItemScreen::new);
        context.registerMenuScreen(ModRegistry.IRON_BAG_OF_HOLDING_MENU_TYPE.value(), BagItemScreen::new);
        context.registerMenuScreen(ModRegistry.GOLDEN_BAG_OF_HOLDING_MENU_TYPE.value(), BagItemScreen::new);
    }
}
