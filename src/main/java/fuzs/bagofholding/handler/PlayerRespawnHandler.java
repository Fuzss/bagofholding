package fuzs.bagofholding.handler;

import fuzs.bagofholding.registry.ModRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerRespawnHandler {
    @SubscribeEvent
    public void onPlayerClone(final PlayerEvent.Clone evt) {
        if (!evt.isWasDeath()) return;
        if (evt.getOriginal() instanceof ServerPlayer player) {
            player.reviveCaps();
            player.getCapability(ModRegistry.BAG_PERSEVERANCE_CAPABILITY).ifPresent(capability -> {
                capability.restoreAfterRespawn(evt.getPlayer());
            });
            player.invalidateCaps();
        }
    }
}
