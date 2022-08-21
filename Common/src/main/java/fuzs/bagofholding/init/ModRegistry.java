package fuzs.bagofholding.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.capability.BagPerseveranceCapability;
import fuzs.bagofholding.capability.BagPerseveranceCapabilityImpl;
import fuzs.bagofholding.core.ModServices;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.bagofholding.world.item.crafting.BagUpgradeRecipe;
import fuzs.bagofholding.world.item.enchantment.PreservationEnchantment;
import fuzs.puzzleslib.capability.CapabilityController;
import fuzs.puzzleslib.capability.data.CapabilityKey;
import fuzs.puzzleslib.capability.data.PlayerRespawnStrategy;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Locale;

public class ModRegistry {
    public static final EnchantmentCategory BAG_OF_HOLDING_ENCHANTMENT_CATEGORY = ModServices.ABSTRACTIONS.createEnchantmentCategory("%s_BAG_OF_HOLDING".formatted(BagOfHolding.MOD_ID.toUpperCase(Locale.ROOT)), item -> item instanceof BagOfHoldingItem);

    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(BagOfHolding.MOD_ID);
    public static final RegistryReference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRY.placeholder(Registry.ITEM_REGISTRY, "leather_bag_of_holding");
    public static final RegistryReference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRY.placeholder(Registry.ITEM_REGISTRY, "iron_bag_of_holding");
    public static final RegistryReference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRY.placeholder(Registry.ITEM_REGISTRY, "golden_bag_of_holding");
    public static final RegistryReference<MenuType<BagItemMenu>> LEATHER_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerMenuTypeSupplier("leather_bag_of_holding", () -> BagItemMenu::leatherBag);
    public static final RegistryReference<MenuType<BagItemMenu>> IRON_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerMenuTypeSupplier("iron_bag_of_holding", () -> BagItemMenu::ironBag);
    public static final RegistryReference<MenuType<BagItemMenu>> GOLDEN_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerMenuTypeSupplier("golden_bag_of_holding", () -> BagItemMenu::goldenBag);
    public static final RegistryReference<Enchantment> PRESERVATION_ENCHANTMENT = REGISTRY.registerEnchantment("preservation", () -> new PreservationEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.values()));
    public static final RegistryReference<RecipeSerializer<ShapedRecipe>> BAG_UPGRADE_RECIPE_SERIALIZER = REGISTRY.register(Registry.RECIPE_SERIALIZER_REGISTRY, "crafting_special_bag_upgrade", () -> new BagUpgradeRecipe.Serializer());

    private static final CapabilityController CAPABILITIES = CoreServices.FACTORIES.capabilities(BagOfHolding.MOD_ID);
    public static final CapabilityKey<BagPerseveranceCapability> BAG_PERSEVERANCE_CAPABILITY = CAPABILITIES.registerPlayerCapability("bag_perseverance", BagPerseveranceCapability.class, player -> new BagPerseveranceCapabilityImpl(), PlayerRespawnStrategy.NEVER);

    public static void touch() {

    }
}
