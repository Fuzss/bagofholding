package fuzs.bagofholding;

import fuzs.bagofholding.config.ClientConfig;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.handler.PerseveranceHandler;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.network.S2CLockSlotMessage;
import fuzs.bagofholding.world.item.BagOfHoldingProvider;
import fuzs.bagofholding.world.item.BagType;
import fuzs.iteminteractions.api.v1.ItemContainerProviderSerializers;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.config.v3.json.GsonEnumHelper;
import fuzs.puzzleslib.api.core.v1.ContentRegistrationFlags;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerCopyEvents;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import fuzs.puzzleslib.api.network.v2.NetworkHandlerV2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BagOfHolding implements ModConstructor {
    public static final String MOD_ID = "bagofholding";
    public static final String MOD_NAME = "Bag Of Holding";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandlerV2 NETWORK = NetworkHandlerV2.build(MOD_ID, false);
    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).client(ClientConfig.class).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerMessages();
        registerHandlers();
        registerSerializers();
    }

    private static void registerMessages() {
        NETWORK.registerClientbound(S2CLockSlotMessage.class, S2CLockSlotMessage::new);
    }

    private static void registerHandlers() {
        PlayerCopyEvents.COPY.register(PerseveranceHandler::onPlayerClone);
    }

    private static void registerSerializers() {
        ItemContainerProviderSerializers.register(BagOfHoldingProvider.class, id("bag"), jsonElement -> {
            BagType type = GsonEnumHelper.getAsEnum(jsonElement.getAsJsonObject(), "bag_type", BagType.class);
            return new BagOfHoldingProvider(type);
        });
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID)
                .icon(() -> new ItemStack(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.value()))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value());
                    output.accept(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value());
                    output.accept(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.value());
                })
                .appendEnchantmentsAndPotions());
    }

    @Override
    public ContentRegistrationFlags[] getContentRegistrationFlags() {
        return new ContentRegistrationFlags[]{ContentRegistrationFlags.COPY_TAG_RECIPES};
    }
}
