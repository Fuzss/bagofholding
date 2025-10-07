package fuzs.bagofholding.neoforge;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.data.ModItemContentsProvider;
import fuzs.bagofholding.data.ModRecipeProvider;
import fuzs.bagofholding.data.tags.ModEnchantmentTagProvider;
import fuzs.bagofholding.data.tags.ModItemTagProvider;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.neoforge.init.NeoForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(BagOfHolding.MOD_ID)
public class BagOfHoldingNeoForge {

    public BagOfHoldingNeoForge() {
        NeoForgeModRegistry.bootstrap();
        ModConstructor.construct(BagOfHolding.MOD_ID, BagOfHolding::new);
        DataProviderHelper.registerDataProviders(BagOfHolding.MOD_ID,
                ModRegistry.REGISTRY_SET_BUILDER,
                ModItemTagProvider::new,
                ModEnchantmentTagProvider::new,
                ModRecipeProvider::new,
                ModItemContentsProvider::new);
    }
}
