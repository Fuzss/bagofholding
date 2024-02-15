package fuzs.bagofholding.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.capability.BagPerseveranceCapability;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.bagofholding.world.item.BagType;
import fuzs.bagofholding.world.item.enchantment.PreservationEnchantment;
import fuzs.extensibleenums.api.v2.CommonAbstractions;
import fuzs.puzzleslib.api.capability.v3.CapabilityController;
import fuzs.puzzleslib.api.capability.v3.data.EntityCapabilityKey;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ModRegistry {
    public static final EnchantmentCategory BAG_OF_HOLDING_ENCHANTMENT_CATEGORY = CommonAbstractions.createEnchantmentCategory(BagOfHolding.id("bag"), item -> item instanceof BagOfHoldingItem);

    static final RegistryManager REGISTRY = RegistryManager.from(BagOfHolding.MOD_ID);
    public static final Holder.Reference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRY.registerLazily(Registries.ITEM, "leather_bag_of_holding");
    public static final Holder.Reference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRY.registerLazily(Registries.ITEM, "iron_bag_of_holding");
    public static final Holder.Reference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRY.registerLazily(Registries.ITEM, "golden_bag_of_holding");
    public static final Holder.Reference<MenuType<BagItemMenu>> LEATHER_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerMenuType("leather_bag_of_holding", () -> BagItemMenu.create(
            BagType.LEATHER));
    public static final Holder.Reference<MenuType<BagItemMenu>> IRON_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerMenuType("iron_bag_of_holding", () -> BagItemMenu.create(
            BagType.IRON));
    public static final Holder.Reference<MenuType<BagItemMenu>> GOLDEN_BAG_OF_HOLDING_MENU_TYPE = REGISTRY.registerMenuType("golden_bag_of_holding", () -> BagItemMenu.create(
            BagType.GOLDEN));
    public static final Holder.Reference<Enchantment> PRESERVATION_ENCHANTMENT = REGISTRY.registerEnchantment("preservation", () -> new PreservationEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.values()));

    static final CapabilityController CAPABILITIES = CapabilityController.from(BagOfHolding.MOD_ID);
    public static final EntityCapabilityKey<Player, BagPerseveranceCapability> BAG_PERSEVERANCE_CAPABILITY = CAPABILITIES.registerEntityCapability("bag_perseverance", BagPerseveranceCapability.class,
            BagPerseveranceCapability::new, Player.class);

    public static void touch() {

    }
}
