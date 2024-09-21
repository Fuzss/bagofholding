package fuzs.bagofholding.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class ModItemTagProvider extends AbstractTagProvider<Item> {

    public ModItemTagProvider(DataProviderContext context) {
        super(Registries.ITEM, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.add(ItemTags.VANISHING_ENCHANTABLE).addTag(ModRegistry.BAGS_ITEM_TAG);
        this.add(ModRegistry.BAGS_ITEM_TAG)
                .add(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value(),
                        ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value(),
                        ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.value()
                );
        this.add(ModRegistry.RECIPES_IGNORE_COMPONENTS_ITEM_TAG).addTag(ModRegistry.BAGS_ITEM_TAG);
    }
}
