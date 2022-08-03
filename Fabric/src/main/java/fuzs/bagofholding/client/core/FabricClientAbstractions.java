package fuzs.bagofholding.client.core;

import net.minecraft.client.KeyMapping;

public class FabricClientAbstractions implements ClientAbstractions {

    @Override
    public boolean isKeyActiveAndMatches(KeyMapping keyMapping, int keyCode, int modifierKeyCode) {
        return keyMapping.matches(keyCode, modifierKeyCode);
    }
}
