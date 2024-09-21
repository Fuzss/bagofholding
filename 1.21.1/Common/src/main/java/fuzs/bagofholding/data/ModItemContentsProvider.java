package fuzs.bagofholding.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.item.BagType;
import fuzs.bagofholding.world.item.container.BagProvider;
import fuzs.iteminteractions.api.v1.data.AbstractItemContentsProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.world.item.Item;

public class ModItemContentsProvider extends AbstractItemContentsProvider {

    public ModItemContentsProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addItemProviders() {
        this.add(BagType.LEATHER, ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value());
        this.add(BagType.IRON, ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value());
        this.add(BagType.GOLDEN, ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.value());
    }

    public void add(BagType bagType, Item item) {
        this.add(new BagProvider(bagType, bagType.fallbackColor).filterContainerItems(true), item);
    }
}
