package fuzs.bagofholding;

import fuzs.bagofholding.config.CommonConfig;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.network.S2CLockSlotMessage;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.bagofholding.world.item.BagOfHoldingProvider;
import fuzs.puzzlesapi.api.iteminteractions.v1.ItemContainerProviderSerializers;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.config.v3.json.GsonEnumHelper;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import fuzs.puzzleslib.api.network.v2.MessageDirection;
import fuzs.puzzleslib.api.network.v2.NetworkHandlerV2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BagOfHolding implements ModConstructor {
    public static final String MOD_ID = "bagofholding";
    public static final String MOD_NAME = "Bag Of Holding";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandlerV2 NETWORK = NetworkHandlerV2.build(MOD_ID);
    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).common(CommonConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerMessages();
        ItemContainerProviderSerializers.register(BagOfHoldingProvider.class, id("bag"), jsonElement -> {
            BagOfHoldingItem.Type type = GsonEnumHelper.getAsEnum(jsonElement.getAsJsonObject(), "bag_type", BagOfHoldingItem.Type.class);
            return new BagOfHoldingProvider(type);
        });
    }

    private static void registerMessages() {
        NETWORK.register(S2CLockSlotMessage.class, S2CLockSlotMessage::new, MessageDirection.TO_CLIENT);
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID).icon(() -> new ItemStack(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.get())).displayItems((itemDisplayParameters, output) -> {
            output.accept(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get());
            output.accept(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get());
            output.accept(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.get());
        }).appendEnchantmentsAndPotions());
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
