package fuzs.bagofholding.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.item.BagOfHoldingProvider;
import fuzs.bagofholding.world.item.BagType;
import fuzs.iteminteractions.api.v1.data.AbstractItemContainerProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModItemContainerProvider extends AbstractItemContainerProvider {

    public ModItemContainerProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addItemProviders() {
        this.add(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value(), new BagOfHoldingProvider(BagType.LEATHER));
        this.add(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value(), new BagOfHoldingProvider(BagType.IRON));
        this.add(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.value(), new BagOfHoldingProvider(BagType.GOLDEN));
    }
}
