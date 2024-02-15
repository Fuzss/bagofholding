package fuzs.bagofholding.handler;

import fuzs.bagofholding.init.ModRegistry;
import net.minecraft.server.level.ServerPlayer;

public class PerseveranceHandler {

    public static void onPlayerClone(ServerPlayer originalPlayer, ServerPlayer newPlayer, boolean alive) {
        if (!alive) ModRegistry.BAG_PERSEVERANCE_CAPABILITY.get(originalPlayer).restoreAfterRespawn(newPlayer);
    }
}
