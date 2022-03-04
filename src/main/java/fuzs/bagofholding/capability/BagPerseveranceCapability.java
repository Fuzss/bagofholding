package fuzs.bagofholding.capability;

import fuzs.puzzleslib.capability.data.CapabilityComponent;
import net.minecraft.world.entity.player.Player;

public interface BagPerseveranceCapability extends CapabilityComponent {
    void saveOnDeath(Player player);

    void restoreAfterRespawn(Player player);
}
