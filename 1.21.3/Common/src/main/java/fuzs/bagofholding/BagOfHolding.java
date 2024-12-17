package fuzs.bagofholding;

import fuzs.bagofholding.attachment.SoulboundItems;
import fuzs.bagofholding.config.ClientConfig;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.network.S2CLockSlotMessage;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ContentRegistrationFlags;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingDropsCallback;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerCopyEvents;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import fuzs.puzzleslib.api.network.v3.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BagOfHolding implements ModConstructor {
    public static final String MOD_ID = "bagofholding";
    public static final String MOD_NAME = "Bag Of Holding";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandler NETWORK = NetworkHandler.builder(MOD_ID)
            .registerLegacyClientbound(S2CLockSlotMessage.class, S2CLockSlotMessage::new);
    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID)
            .client(ClientConfig.class)
            .server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        PlayerCopyEvents.COPY.register(SoulboundItems::onCopy);
        LivingDropsCallback.EVENT.register(SoulboundItems::onLivingDrops);
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
        return new ContentRegistrationFlags[]{ContentRegistrationFlags.CRAFTING_TRANSMUTE};
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
