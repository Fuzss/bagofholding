package fuzs.bagofholding.fabric.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.fabric.world.item.FabricBagOfHoldingItem;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

public class FabricModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.from(BagOfHolding.MOD_ID);
    public static final Holder.Reference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem(
            "leather_bag_of_holding",
            () -> new FabricBagOfHoldingItem(new Item.Properties().stacksTo(1),
                    ModRegistry.LEATHER_BAG_OF_HOLDING_MENU_TYPE
            )
    );
    public static final Holder.Reference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("iron_bag_of_holding",
            () -> new FabricBagOfHoldingItem(new Item.Properties().stacksTo(1),
                    ModRegistry.IRON_BAG_OF_HOLDING_MENU_TYPE
            )
    );
    public static final Holder.Reference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem(
            "golden_bag_of_holding",
            () -> new FabricBagOfHoldingItem(new Item.Properties().stacksTo(1),
                    ModRegistry.GOLDEN_BAG_OF_HOLDING_MENU_TYPE
            )
    );

    public static void touch() {
        // NO-OP
    }
}
