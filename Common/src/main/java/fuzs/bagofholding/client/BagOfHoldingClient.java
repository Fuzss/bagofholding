package fuzs.bagofholding.client;

import fuzs.bagofholding.client.gui.screens.inventory.BagItemScreen;
import fuzs.bagofholding.client.gui.screens.inventory.tooltip.ClientContainerItemTooltip;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.inventory.tooltip.ContainerItemTooltip;
import fuzs.puzzleslib.client.core.ClientModConstructor;

public class BagOfHoldingClient implements ClientModConstructor {

    @Override
    public void onRegisterClientTooltipComponents(ClientTooltipComponentsContext context) {
        context.registerClientTooltipComponent(ContainerItemTooltip.class, ClientContainerItemTooltip::new);
    }

    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        context.registerMenuScreen(ModRegistry.LEATHER_BAG_OF_HOLDING_MENU_TYPE.get(), BagItemScreen::new);
        context.registerMenuScreen(ModRegistry.IRON_BAG_OF_HOLDING_MENU_TYPE.get(), BagItemScreen::new);
        context.registerMenuScreen(ModRegistry.GOLDEN_BAG_OF_HOLDING_MENU_TYPE.get(), BagItemScreen::new);
    }
}
