package fuzs.bagofholding.fabric.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.fabric.world.item.FabricBagOfHoldingItem;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

public class FabricModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(BagOfHolding.MOD_ID);
    public static final Holder.Reference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRIES.registerItem(
            "leather_bag_of_holding",
            FabricBagOfHoldingItem::new,
            ModRegistry::bagOfHoldingProperties);
    public static final Holder.Reference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRIES.registerItem("iron_bag_of_holding",
            FabricBagOfHoldingItem::new,
            ModRegistry::bagOfHoldingProperties);
    public static final Holder.Reference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRIES.registerItem(
            "golden_bag_of_holding",
            FabricBagOfHoldingItem::new,
            ModRegistry::bagOfHoldingProperties);

    public static void bootstrap() {
        // NO-OP
    }
}
