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
        builder.add(ModRegistry.CREATIVE_MODE_TAB.value(), BagOfHolding.MOD_NAME);
        builder.add(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value(), "Leather Bag of Holding");
        builder.add(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value(), "Iron Bag of Holding");
        builder.add(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.value(), "Golden Bag of Holding");
        builder.addEnchantment(ModRegistry.PRESERVATION_ENCHANTMENT, "Preservation");
        builder.addEnchantment(ModRegistry.PRESERVATION_ENCHANTMENT,
                "desc",
                "Prevents a bag of holding from being lost on death. The enchantment level is reduced by one each time.");
        builder.add(ModRegistry.BAGS_ITEM_TAG, "Bags");
        builder.add(ModRegistry.RECIPES_IGNORE_COMPONENTS_ITEM_TAG, "Recipes Ignore Components");
    }
}
