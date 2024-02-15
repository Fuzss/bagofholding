package fuzs.bagofholding.data;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractLanguageProvider;
import net.minecraft.data.PackOutput;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    protected void addTranslations() {
        this.addCreativeModeTab(BagOfHolding.MOD_NAME);
        this.add(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get(), "Leather Bag of Holding");
        this.add(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get(), "Iron Bag of Holding");
        this.add(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.get(), "Golden Bag of Holding");
        this.add(ModRegistry.PRESERVATION_ENCHANTMENT.get(), "Preservation");
        this.addAdditional(ModRegistry.PRESERVATION_ENCHANTMENT.get(), "desc", "Prevents a bag of holding from being lost on death. The enchantment level is reduced by one each time.");
    }
}
