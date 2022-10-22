package fuzs.bagofholding.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.api.client.event.ContainerScreenEvents;
import fuzs.bagofholding.api.client.handler.MouseScrollHandler;
import fuzs.bagofholding.client.handler.SlotOverlayHandler;
import fuzs.bagofholding.config.ClientConfig;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.puzzleslib.client.core.ClientFactories;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class BagOfHoldingFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientFactories.INSTANCE.clientModConstructor(BagOfHolding.MOD_ID).accept(new BagOfHoldingClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        ContainerScreenEvents.FOREGROUND.register(SlotOverlayHandler::onDrawForeground);
        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof AbstractContainerScreen<?>) {
                ScreenMouseEvents.allowMouseScroll(screen).register((screen1, mouseX, mouseY, horizontalAmount, verticalAmount) -> {
                    return MouseScrollHandler.onMouseScroll(screen1, mouseX, mouseY, horizontalAmount, verticalAmount, BagOfHolding.CONFIG.get(ClientConfig.class), BagOfHolding.CONFIG.get(ServerConfig.class)).isEmpty();
                });
            }
        });
//        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> MouseScrollHandler.onClientTick$End(minecraft, BagOfHolding.CONFIG.get(ClientConfig.class)));
    }
}
