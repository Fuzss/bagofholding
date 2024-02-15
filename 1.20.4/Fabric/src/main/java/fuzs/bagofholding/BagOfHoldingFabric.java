package fuzs.bagofholding;

import fuzs.bagofholding.init.FabricModRegistry;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;

public class BagOfHoldingFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FabricModRegistry.touch();
        ModConstructor.construct(BagOfHolding.MOD_ID, BagOfHolding::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        // TODO move to common puzzles implementation in 1.19.4
        ServerPlayerEvents.COPY_FROM.register((ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) -> {
            if (alive) return;
            ModRegistry.BAG_PERSEVERANCE_CAPABILITY.maybeGet(oldPlayer).ifPresent(capability -> {
                capability.restoreAfterRespawn(newPlayer);
            });
        });
    }
}
