package fuzs.bagofholding.neoforge.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.neoforge.world.item.NeoForgeBagOfHoldingItem;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

public class NeoForgeModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(BagOfHolding.MOD_ID);
    public static final Holder.Reference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRIES.registerItem(
            "leather_bag_of_holding",
            NeoForgeBagOfHoldingItem::new,
            ModRegistry::bagOfHoldingProperties);
    public static final Holder.Reference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRIES.registerItem("iron_bag_of_holding",
            NeoForgeBagOfHoldingItem::new,
            ModRegistry::bagOfHoldingProperties);
    public static final Holder.Reference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRIES.registerItem(
            "golden_bag_of_holding",
            NeoForgeBagOfHoldingItem::new,
            ModRegistry::bagOfHoldingProperties);

    public static void bootstrap() {
        // NO-OP
    }
}
