package fuzs.bagofholding.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.api.client.gui.screens.inventory.tooltip.ClientContainerItemTooltip;
import fuzs.bagofholding.api.client.helper.ItemDecorationHelper;
import fuzs.bagofholding.api.world.inventory.tooltip.ContainerItemTooltip;
import fuzs.bagofholding.api.world.item.ContainerItemHelper;
import fuzs.bagofholding.client.gui.screens.inventory.BagItemScreen;
import fuzs.bagofholding.client.handler.SlotOverlayHandler;
import fuzs.bagofholding.config.ClientConfig;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BagOfHoldingClient implements ClientModConstructor {

    @Override
    public void onRegisterClientTooltipComponents(ClientTooltipComponentsContext context) {
        context.registerClientTooltipComponent(ContainerItemTooltip.class, tooltip -> new ClientContainerItemTooltip(tooltip, BagOfHolding.CONFIG.get(ClientConfig.class)));
    }

    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        context.registerMenuScreen(ModRegistry.LEATHER_BAG_OF_HOLDING_MENU_TYPE.get(), BagItemScreen::new);
        context.registerMenuScreen(ModRegistry.IRON_BAG_OF_HOLDING_MENU_TYPE.get(), BagItemScreen::new);
        context.registerMenuScreen(ModRegistry.GOLDEN_BAG_OF_HOLDING_MENU_TYPE.get(), BagItemScreen::new);
    }

    @Override
    public void onRegisterItemDecorations(ItemDecorationContext context) {
        for (Item item : Registry.ITEM) {
            if (item instanceof BagOfHoldingItem bag) {
                context.register(item, ItemDecorationHelper.getDynamicItemDecorator((AbstractContainerScreen<?> screen, ItemStack containerStack, ItemStack carriedStack) -> {
                    return BagOfHoldingItem.mayPlaceInBag(carriedStack) && SlotOverlayHandler.canInteractWithItem(screen, containerStack)
                            && ContainerItemHelper.loadItemContainer(containerStack, null, bag.containerRows.getAsInt(), false).canAddItem(carriedStack);
                }, () -> BagOfHolding.CONFIG.get(ClientConfig.class).containerItemIndicator));
            }
        }
    }
}
