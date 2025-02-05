package fuzs.bagofholding.fabric.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.fabric.world.item.FabricBagOfHoldingItem;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FabricModRegistry {
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

    private static Holder.Reference<Item> registerBagOfHolding(String path, Supplier<Holder<MenuType<BagItemMenu>>> supplier) {
        return REGISTRIES.registerItem(path,
                (Item.Properties properties) -> new FabricBagOfHoldingItem(properties, supplier.get()),
                () -> new Item.Properties().stacksTo(1).enchantable(1));
    }
}
