package fuzs.bagofholding.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.capability.BagPerseveranceCapability;
import fuzs.bagofholding.capability.BagPerseveranceCapabilityImpl;
import fuzs.bagofholding.core.CommonAbstractions;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.bagofholding.world.item.crafting.BagUpgradeRecipe;
import fuzs.bagofholding.world.item.enchantment.PreservationEnchantment;
import fuzs.puzzleslib.api.capability.v2.CapabilityController;
import fuzs.puzzleslib.api.capability.v2.data.CapabilityKey;
import fuzs.puzzleslib.api.capability.v2.data.PlayerRespawnStrategy;
import fuzs.puzzleslib.api.init.v2.RegistryManager;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Locale;

public class ModRegistry {
    public static final EnchantmentCategory BAG_OF_HOLDING_ENCHANTMENT_CATEGORY = CommonAbstractions.INSTANCE.createEnchantmentCategory("%s_BAG_OF_HOLDING".formatted(BagOfHolding.MOD_ID.toUpperCase(Locale.ROOT)), item -> item instanceof BagOfHoldingItem);

    static final RegistryManager REGISTRY = RegistryManager.instant(BagOfHolding.MOD_ID);
    public static final RegistryReference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRY.placeholder(Registries.ITEM, "leather_bag_of_holding");
    public static final RegistryReference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRY.placeholder(Registries.ITEM, "iron_bag_of_holding");
    public static final RegistryReference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRY.placeholder(Registries.ITEM, "golden_bag_of_holding");
    public static final RegistryReference<MenuType<BagItemMenu>> LEATHER_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerMenuType("leather_bag_of_holding", () -> BagItemMenu.create(BagOfHoldingItem.Type.LEATHER));
    public static final RegistryReference<MenuType<BagItemMenu>> IRON_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerMenuType("iron_bag_of_holding", () -> BagItemMenu.create(BagOfHoldingItem.Type.IRON));
    public static final RegistryReference<MenuType<BagItemMenu>> GOLDEN_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerMenuType("golden_bag_of_holding", () -> BagItemMenu.create(BagOfHoldingItem.Type.GOLDEN));
    public static final RegistryReference<Enchantment> PRESERVATION_ENCHANTMENT = REGISTRY.registerEnchantment("preservation", () -> new PreservationEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.values()));
    public static final RegistryReference<RecipeSerializer<ShapedRecipe>> BAG_UPGRADE_RECIPE_SERIALIZER = REGISTRY.register(Registries.RECIPE_SERIALIZER, "crafting_special_bag_upgrade", () -> new BagUpgradeRecipe.Serializer());

    private static final CapabilityController CAPABILITIES = CapabilityController.from(BagOfHolding.MOD_ID);
    public static final CapabilityKey<BagPerseveranceCapability> BAG_PERSEVERANCE_CAPABILITY = CAPABILITIES.registerPlayerCapability("bag_perseverance", BagPerseveranceCapability.class, player -> new BagPerseveranceCapabilityImpl(), PlayerRespawnStrategy.NEVER);

    public static void touch() {

    }
}
