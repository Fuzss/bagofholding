package fuzs.bagofholding.neoforge.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.neoforge.world.item.NeoForgeBagOfHoldingItem;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class NeoForgeModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(BagOfHolding.MOD_ID);
    public static final Holder.Reference<Item> LEATHER_BAG_OF_HOLDING_ITEM = registerBagOfHolding(
            "leather_bag_of_holding",
            () -> ModRegistry.LEATHER_BAG_OF_HOLDING_MENU_TYPE);
    public static final Holder.Reference<Item> IRON_BAG_OF_HOLDING_ITEM = registerBagOfHolding("iron_bag_of_holding",
            () -> ModRegistry.IRON_BAG_OF_HOLDING_MENU_TYPE);
    public static final Holder.Reference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = registerBagOfHolding("golden_bag_of_holding",
            () -> ModRegistry.GOLDEN_BAG_OF_HOLDING_MENU_TYPE);

    public static void bootstrap() {
        // NO-OP
    }

    static Holder.Reference<Item> registerBagOfHolding(String path, Supplier<Holder<MenuType<BagItemMenu>>> supplier) {
        return REGISTRIES.registerItem(path,
                (Item.Properties properties) -> new NeoForgeBagOfHoldingItem(properties, supplier.get()),
                ModRegistry::bagOfHoldingProperties);
    }
}
