package fuzs.bagofholding.init;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.capability.BagPerseveranceCapability;
import fuzs.bagofholding.capability.BagPerseveranceCapabilityImpl;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.item.FabricBagOfHoldingItem;
import fuzs.puzzleslib.capability.FabricCapabilityController;
import fuzs.puzzleslib.capability.data.CapabilityKey;
import fuzs.puzzleslib.capability.data.PlayerRespawnStrategy;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public class FabricModRegistry {
    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(BagOfHolding.MOD_ID);
    public static final RegistryReference<Item> LEATHER_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("leather_bag_of_holding", () -> new FabricBagOfHoldingItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1), () -> BagOfHolding.CONFIG.get(ServerConfig.class).leatherBagRows, DyeColor.BROWN, BagItemMenu::leatherBag));
    public static final RegistryReference<Item> IRON_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("iron_bag_of_holding", () -> new FabricBagOfHoldingItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1), () -> BagOfHolding.CONFIG.get(ServerConfig.class).ironBagRows, DyeColor.WHITE, BagItemMenu::ironBag));
    public static final RegistryReference<Item> GOLDEN_BAG_OF_HOLDING_ITEM = REGISTRY.registerItem("golden_bag_of_holding", () -> new FabricBagOfHoldingItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1), () -> BagOfHolding.CONFIG.get(ServerConfig.class).goldenBagRows, DyeColor.YELLOW, BagItemMenu::goldenBag));

    private static final FabricCapabilityController CAPABILITIES = FabricCapabilityController.of(BagOfHolding.MOD_ID);
    public static final CapabilityKey<BagPerseveranceCapability> BAG_PERSEVERANCE_CAPABILITY = CAPABILITIES.registerPlayerCapability("bag_perseverance", BagPerseveranceCapability.class, player -> new BagPerseveranceCapabilityImpl(), PlayerRespawnStrategy.NEVER);

    public static void touch() {

    }
}
