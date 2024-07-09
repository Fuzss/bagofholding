package fuzs.bagofholding.neoforge;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.data.ModItemContainerProvider;
import fuzs.bagofholding.data.ModItemTagProvider;
import fuzs.bagofholding.data.ModRecipeProvider;
import fuzs.bagofholding.data.client.ModLanguageProvider;
import fuzs.bagofholding.data.client.ModModelProvider;
import fuzs.bagofholding.neoforge.init.NeoForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(BagOfHolding.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BagOfHoldingNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        NeoForgeModRegistry.touch();
        ModConstructor.construct(BagOfHolding.MOD_ID, BagOfHolding::new);
        DataProviderHelper.registerDataProviders(BagOfHolding.MOD_ID,
                ModItemContainerProvider::new,
                ModLanguageProvider::new,
                ModModelProvider::new,
                ModRecipeProvider::new,
                ModItemTagProvider::new
        );
    }
}
