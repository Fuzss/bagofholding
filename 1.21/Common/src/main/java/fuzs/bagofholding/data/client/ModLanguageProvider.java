package fuzs.bagofholding.data.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.addCreativeModeTab(BagOfHolding.MOD_ID, BagOfHolding.MOD_NAME);
        builder.add(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value(), "Leather Bag of Holding");
        builder.add(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value(), "Iron Bag of Holding");
        builder.add(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.value(), "Golden Bag of Holding");
        builder.add(ModRegistry.PRESERVATION_ENCHANTMENT.value(), "Preservation");
        builder.add(ModRegistry.PRESERVATION_ENCHANTMENT.value(),
                "desc",
                "Prevents a bag of holding from being lost on death. The enchantment level is reduced by one each time."
        );
    }
}
