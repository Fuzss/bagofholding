package fuzs.bagofholding.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.attachment.SoulboundItems;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.item.container.BagProvider;
import fuzs.iteminteractions.api.v1.provider.ItemContentsProvider;
import fuzs.puzzleslib.api.attachment.v4.DataAttachmentRegistry;
import fuzs.puzzleslib.api.attachment.v4.DataAttachmentType;
import fuzs.puzzleslib.api.data.v2.AbstractDatapackRegistriesProvider;
import fuzs.puzzleslib.api.init.v3.registry.ContentRegistrationHelper;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModRegistry {
    public static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder().add(Registries.ENCHANTMENT,
            ModRegistry::bootstrapEnchantments);

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
    public static final Holder.Reference<ItemContentsProvider.Type<?>> BAG_ITEM_CONTENTS_PROVIDER_TYPE = REGISTRIES.register(
            ItemContentsProvider.REGISTRY_KEY,
            "bag",
            () -> new ItemContentsProvider.Type<>(BagProvider.CODEC));

    static final TagFactory TAGS = TagFactory.make(BagOfHolding.MOD_ID);
    public static final TagKey<Item> BAGS_ITEM_TAG = TAGS.registerItemTag("bags");
    public static final TagKey<Item> RECIPES_IGNORE_COMPONENTS_ITEM_TAG = TAGS.registerItemTag(
            "recipes_ignore_components");

    public static final DataAttachmentType<Entity, SoulboundItems> SOULBOUND_ITEMS_ATTACHMENT_TYPE = DataAttachmentRegistry.<SoulboundItems>entityBuilder()
            .persistent(SoulboundItems.CODEC)
            .build(BagOfHolding.id("soulbound_items"));

    public static void bootstrap() {
        ContentRegistrationHelper.registerTransmuteRecipeSerializers(REGISTRIES);
    }

    public static void bootstrapEnchantments(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemLookup = context.lookup(Registries.ITEM);
        AbstractDatapackRegistriesProvider.registerEnchantment(context,
                PRESERVATION_ENCHANTMENT,
                Enchantment.enchantment(Enchantment.definition(itemLookup.getOrThrow(BAGS_ITEM_TAG),
                        5,
                        3,
                        Enchantment.dynamicCost(5, 8),
                        Enchantment.dynamicCost(55, 8),
                        2,
                        EquipmentSlotGroup.ANY)));
    }

    public static Item.Properties bagOfHoldingProperties() {
        return new Item.Properties().stacksTo(1)
                .enchantable(1)
                .component(DataComponents.TOOLTIP_DISPLAY,
                        TooltipDisplay.DEFAULT.withHidden(DataComponents.CONTAINER, true));
    }
}
