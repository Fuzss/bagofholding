package fuzs.bagofholding.fabric.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.client.BagOfHoldingClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class BagOfHoldingFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(BagOfHolding.MOD_ID, BagOfHoldingClient::new);
    }
}
