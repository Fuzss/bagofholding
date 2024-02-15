package fuzs.bagofholding.init;

import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.bagofholding.world.item.ForgeBagOfHoldingItem;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.world.item.Item;

import static fuzs.bagofholding.init.ModRegistry.REGISTRY;

public class ForgeModRegistry {
    public static final RegistryReference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("leather_bag_of_holding", () -> new ForgeBagOfHoldingItem(new Item.Properties().stacksTo(1), BagOfHoldingItem.Type.LEATHER));
    public static final RegistryReference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("iron_bag_of_holding", () -> new ForgeBagOfHoldingItem(new Item.Properties().stacksTo(1), BagOfHoldingItem.Type.IRON));
    public static final RegistryReference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("golden_bag_of_holding", () -> new ForgeBagOfHoldingItem(new Item.Properties().stacksTo(1), BagOfHoldingItem.Type.GOLDEN));

    public static void touch() {

    }
}
