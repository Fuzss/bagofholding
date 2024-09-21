package fuzs.bagofholding.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.capability.BagPerseveranceCapability;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.item.container.BagProvider;
import fuzs.iteminteractions.api.v1.provider.ItemContentsProvider;
import fuzs.puzzleslib.api.capability.v3.CapabilityController;
import fuzs.puzzleslib.api.capability.v3.data.EntityCapabilityKey;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.BoundTagFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(BagOfHolding.MOD_ID);
    public static final Holder.Reference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRIES.registerLazily(Registries.ITEM,
            "leather_bag_of_holding"
    );
    public static final Holder.Reference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRIES.registerLazily(Registries.ITEM,
            "iron_bag_of_holding"
    );
    public static final Holder.Reference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRIES.registerLazily(Registries.ITEM,
            "golden_bag_of_holding"
    );
    public static final Holder.Reference<MenuType<BagItemMenu>> LEATHER_BAG_OF_HOLDING_MENU_TYPE = REGISTRIES.registerMenuType(
            "leather_bag_of_holding",
            () -> BagItemMenu.createLeatherBagMenu()
    );
    public static final Holder.Reference<MenuType<BagItemMenu>> IRON_BAG_OF_HOLDING_MENU_TYPE = REGISTRIES.registerMenuType(
            "iron_bag_of_holding",
            () -> BagItemMenu.createIronBagMenu()
    );
    public static final Holder.Reference<MenuType<BagItemMenu>> GOLDEN_BAG_OF_HOLDING_MENU_TYPE = REGISTRIES.registerMenuType(
            "golden_bag_of_holding",
            () -> BagItemMenu.createGoldenBagMenu()
    );
    public static final ResourceKey<Enchantment> PRESERVATION_ENCHANTMENT = REGISTRIES.registerEnchantment(
            "preservation");
    public static final Holder.Reference<ItemContentsProvider.Type> BAG_ITEM_CONTENTS_PROVIDER_TYPE = REGISTRIES.register(
            ItemContentsProvider.REGISTRY_KEY,
            "bag",
            () -> new ItemContentsProvider.Type(BagProvider.CODEC)
    );

    static final BoundTagFactory TAGS = BoundTagFactory.make(BagOfHolding.MOD_ID);
    public static final TagKey<Item> BAGS_ITEM_TAG = TAGS.registerItemTag("bags");
    public static final TagKey<Item> RECIPES_IGNORE_COMPONENTS_ITEM_TAG = TAGS.registerItemTag("recipes_ignore_components");

    static final CapabilityController CAPABILITIES = CapabilityController.from(BagOfHolding.MOD_ID);
    public static final EntityCapabilityKey<Player, BagPerseveranceCapability> BAG_PERSEVERANCE_CAPABILITY = CAPABILITIES.registerEntityCapability(
            "bag_perseverance",
            BagPerseveranceCapability.class,
            BagPerseveranceCapability::new,
            Player.class
    );

    public static void touch() {
        // NO-OP
    }
}
