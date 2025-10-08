package fuzs.bagofholding.neoforge.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.client.BagOfHoldingClient;
import fuzs.bagofholding.data.client.ModLanguageProvider;
import fuzs.bagofholding.data.client.ModModelProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = BagOfHolding.MOD_ID, dist = Dist.CLIENT)
public class BagOfHoldingNeoForgeClient {

    public BagOfHoldingNeoForgeClient() {
        ClientModConstructor.construct(BagOfHolding.MOD_ID, BagOfHoldingClient::new);
        DataProviderHelper.registerDataProviders(BagOfHolding.MOD_ID, ModLanguageProvider::new, ModModelProvider::new);
    }
}
