package fuzs.bagofholding.registry;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.capability.BagPerseveranceCapability;
import fuzs.bagofholding.capability.BagPerseveranceCapabilityImpl;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.bagofholding.world.item.crafting.BagUpgradeRecipe;
import fuzs.bagofholding.world.item.enchantment.PreservationEnchantment;
import fuzs.puzzleslib.capability.CapabilityController;
import fuzs.puzzleslib.capability.data.PlayerRespawnStrategy;
import fuzs.puzzleslib.registry.RegistryManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;

public class ModRegistry {
    public static final EnchantmentCategory BAG_OF_HOLDING_ENCHANTMENT_CATEGORY = EnchantmentCategory.create(BagOfHolding.MOD_ID.toUpperCase(Locale.ROOT).concat("_BAG_OF_HOLDING"), item -> item instanceof BagOfHoldingItem);

    private static final RegistryManager REGISTRY = RegistryManager.of(BagOfHolding.MOD_ID);
    public static final RegistryObject<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("leather_bag_of_holding", () -> new BagOfHoldingItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1), () -> BagOfHolding.CONFIG.server().leatherBagRows, DyeColor.BROWN, BagItemMenu::leatherBag));
    public static final RegistryObject<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("iron_bag_of_holding", () -> new BagOfHoldingItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1), () -> BagOfHolding.CONFIG.server().ironBagRows, DyeColor.WHITE, BagItemMenu::ironBag));
    public static final RegistryObject<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("golden_bag_of_holding", () -> new BagOfHoldingItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1), () -> BagOfHolding.CONFIG.server().goldenBagRows, DyeColor.YELLOW, BagItemMenu::goldenBag));
    public static final RegistryObject<MenuType<BagItemMenu>> LEATHER_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerRawMenuType("leather_bag_of_holding", () -> BagItemMenu::leatherBag);
    public static final RegistryObject<MenuType<BagItemMenu>> IRON_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerRawMenuType("iron_bag_of_holding", () -> BagItemMenu::ironBag);
    public static final RegistryObject<MenuType<BagItemMenu>> GOLDEN_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerRawMenuType("golden_bag_of_holding", () -> BagItemMenu::goldenBag);
    public static final RegistryObject<Enchantment> PRESERVATION_ENCHANTMENT = REGISTRY.registerEnchantment("preservation", () -> new PreservationEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.values()));
    public static final RegistryObject<RecipeSerializer<ShapedRecipe>> BAG_UPGRADE_RECIPE_SERIALIZER = REGISTRY.register((Class<RecipeSerializer<?>>) (Class<?>) RecipeSerializer.class, "crafting_special_bag_upgrade", () -> new BagUpgradeRecipe.Serializer());

    private static final CapabilityController CAPABILITIES = CapabilityController.of(BagOfHolding.MOD_ID);
    public static final Capability<BagPerseveranceCapability> BAG_PERSEVERANCE_CAPABILITY = CAPABILITIES.registerPlayerCapability("bag_perseverance", BagPerseveranceCapability.class, player -> new BagPerseveranceCapabilityImpl(), PlayerRespawnStrategy.NEVER, new CapabilityToken<BagPerseveranceCapability>() {});

    public static void touch() {

    }
}
