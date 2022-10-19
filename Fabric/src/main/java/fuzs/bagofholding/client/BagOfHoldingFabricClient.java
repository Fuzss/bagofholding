package fuzs.bagofholding.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.api.client.event.ContainerScreenEvents;
import fuzs.bagofholding.client.handler.SlotOverlayHandler;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.fabricmc.api.ClientModInitializer;

public class BagOfHoldingFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCoreServices.FACTORIES.clientModConstructor(BagOfHolding.MOD_ID).accept(new BagOfHoldingClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        ContainerScreenEvents.FOREGROUND.register(SlotOverlayHandler::onDrawForeground);
    }
}
