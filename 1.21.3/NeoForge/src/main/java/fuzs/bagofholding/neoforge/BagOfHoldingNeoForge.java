package fuzs.bagofholding.neoforge;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.data.ModItemContentsProvider;
import fuzs.bagofholding.data.ModItemTagProvider;
import fuzs.bagofholding.data.ModRecipeProvider;
import fuzs.bagofholding.data.client.ModLanguageProvider;
import fuzs.bagofholding.data.client.ModModelProvider;
import fuzs.bagofholding.neoforge.data.ModEnchantmentProvider;
import fuzs.bagofholding.neoforge.init.NeoForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(BagOfHolding.MOD_ID)
public class BagOfHoldingNeoForge {

    public BagOfHoldingNeoForge() {
        NeoForgeModRegistry.touch();
        ModConstructor.construct(BagOfHolding.MOD_ID, BagOfHolding::new);
        DataProviderHelper.registerDataProviders(BagOfHolding.MOD_ID,
                ModItemContentsProvider::new,
                ModLanguageProvider::new,
                ModModelProvider::new,
                ModRecipeProvider::new,
                ModItemTagProvider::new,
                ModEnchantmentProvider::new
        );
    }
}
