package fuzs.bagofholding;

import fuzs.bagofholding.config.ClientConfig;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.network.message.S2CLockSlotMessage;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.ModConstructor;
import fuzs.puzzleslib.network.MessageDirection;
import fuzs.puzzleslib.network.NetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BagOfHolding implements ModConstructor {
    public static final String MOD_ID = "bagofholding";
    public static final String MOD_NAME = "Bag Of Holding";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandler NETWORK = CoreServices.FACTORIES.network(MOD_ID);
    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder CONFIG = CoreServices.FACTORIES
            .clientConfig(ClientConfig.class, () -> new ClientConfig())
            .serverConfig(ServerConfig.class, () -> new ServerConfig());

    @Override
    public void onConstructMod() {
        CONFIG.bakeConfigs(MOD_ID);
        ModRegistry.touch();
        registerMessages();
    }

    private static void registerMessages() {
        NETWORK.register(S2CLockSlotMessage.class, S2CLockSlotMessage::new, MessageDirection.TO_CLIENT);
    }
}
