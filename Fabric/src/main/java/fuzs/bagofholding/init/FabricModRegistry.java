package fuzs.bagofholding.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.bagofholding.world.item.FabricBagOfHoldingItem;
import fuzs.puzzleslib.core.CommonFactories;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class FabricModRegistry {
    private static final RegistryManager REGISTRY = CommonFactories.INSTANCE.registration(BagOfHolding.MOD_ID);
    public static final RegistryReference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("leather_bag_of_holding", () -> new FabricBagOfHoldingItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1), BagOfHoldingItem.Type.LEATHER));
    public static final RegistryReference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("iron_bag_of_holding", () -> new FabricBagOfHoldingItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1), BagOfHoldingItem.Type.IRON));
    public static final RegistryReference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("golden_bag_of_holding", () -> new FabricBagOfHoldingItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1), BagOfHoldingItem.Type.GOLDEN));

    public static void touch() {

    }
}
