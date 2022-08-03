package fuzs.bagofholding.client.core;

import net.minecraft.client.KeyMapping;

public interface ClientAbstractions {

    boolean isKeyActiveAndMatches(KeyMapping keyMapping, int keyCode, int modifierKeyCode);
}
