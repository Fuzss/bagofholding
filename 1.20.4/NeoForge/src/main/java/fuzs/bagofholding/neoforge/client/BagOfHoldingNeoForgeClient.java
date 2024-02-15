package fuzs.bagofholding.neoforge.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.client.BagOfHoldingClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = BagOfHolding.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BagOfHoldingNeoForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(BagOfHolding.MOD_ID, BagOfHoldingClient::new);
    }
}
