package fuzs.bagofholding.neoforge.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.client.BagOfHoldingClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = BagOfHolding.MOD_ID, dist = Dist.CLIENT)
public class BagOfHoldingNeoForgeClient {

    public BagOfHoldingNeoForgeClient() {
        ClientModConstructor.construct(BagOfHolding.MOD_ID, BagOfHoldingClient::new);
    }
}
