package fuzs.bagofholding.data.client;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.value(), ModelTemplates.FLAT_ITEM);
    }
}
