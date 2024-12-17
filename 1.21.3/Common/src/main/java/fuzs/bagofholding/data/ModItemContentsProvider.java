package fuzs.bagofholding.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.item.BagType;
import fuzs.bagofholding.world.item.container.BagProvider;
import fuzs.iteminteractions.api.v1.data.AbstractItemContentsProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ModItemContentsProvider extends AbstractItemContentsProvider {

    public ModItemContentsProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addItemProviders(HolderLookup.Provider registries) {
        HolderLookup.RegistryLookup<Item> items = registries.lookupOrThrow(Registries.ITEM);
        this.add(items, BagType.LEATHER, ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value());
        this.add(items, BagType.IRON, ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value());
        this.add(items, BagType.GOLDEN, ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.value());
    }

    public void add(HolderLookup.RegistryLookup<Item> items, BagType bagType, Item item) {
        this.add(items, new BagProvider(bagType, bagType.fallbackColor).filterContainerItems(true), item);
    }
}
