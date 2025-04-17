package fuzs.bagofholding.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.attachment.SoulboundItems;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.item.container.BagProvider;
import fuzs.iteminteractions.api.v1.provider.ItemContentsProvider;
import fuzs.puzzleslib.api.attachment.v4.DataAttachmentRegistry;
import fuzs.puzzleslib.api.attachment.v4.DataAttachmentType;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.registry.TransmuteRecipeHelper;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(BagOfHolding.MOD_ID);
    public static final Holder.Reference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRIES.registerLazily(Registries.ITEM,
            "leather_bag_of_holding");
    public static final Holder.Reference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRIES.registerLazily(Registries.ITEM,
            "iron_bag_of_holding");
    public static final Holder.Reference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRIES.registerLazily(Registries.ITEM,
            "golden_bag_of_holding");
    public static final Holder.Reference<MenuType<BagItemMenu>> LEATHER_BAG_OF_HOLDING_MENU_TYPE = REGISTRIES.registerMenuType(
            "leather_bag_of_holding",
            BagItemMenu::createLeatherBagMenu);
    public static final Holder.Reference<MenuType<BagItemMenu>> IRON_BAG_OF_HOLDING_MENU_TYPE = REGISTRIES.registerMenuType(
            "iron_bag_of_holding",
            BagItemMenu::createIronBagMenu);
    public static final Holder.Reference<MenuType<BagItemMenu>> GOLDEN_BAG_OF_HOLDING_MENU_TYPE = REGISTRIES.registerMenuType(
            "golden_bag_of_holding",
            BagItemMenu::createGoldenBagMenu);
    public static final Holder.Reference<CreativeModeTab> CREATIVE_MODE_TAB = REGISTRIES.registerCreativeModeTab(
            GOLDEN_BAG_OF_HOLDING_ITEM);
    public static final ResourceKey<Enchantment> PRESERVATION_ENCHANTMENT = REGISTRIES.registerEnchantment(
            "preservation");
    public static final Holder.Reference<ItemContentsProvider.Type> BAG_ITEM_CONTENTS_PROVIDER_TYPE = REGISTRIES.register(
            ItemContentsProvider.REGISTRY_KEY,
            "bag",
            () -> new ItemContentsProvider.Type(BagProvider.CODEC));

    static final TagFactory TAGS = TagFactory.make(BagOfHolding.MOD_ID);
    public static final TagKey<Item> BAGS_ITEM_TAG = TAGS.registerItemTag("bags");
    public static final TagKey<Item> RECIPES_IGNORE_COMPONENTS_ITEM_TAG = TAGS.registerItemTag(
            "recipes_ignore_components");

    public static final DataAttachmentType<Entity, SoulboundItems> SOULBOUND_ITEMS_ATTACHMENT_TYPE = DataAttachmentRegistry.<SoulboundItems>entityBuilder()
            .persistent(SoulboundItems.CODEC)
            .build(BagOfHolding.id("soulbound_items"));

    public static void bootstrap() {
        TransmuteRecipeHelper.registerTransmuteRecipeSerializers(REGISTRIES);
    }
}
