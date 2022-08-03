package fuzs.bagofholding.client.core;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class ForgeClientAbstractions implements ClientAbstractions {

    @Override
    public boolean isKeyActiveAndMatches(KeyMapping keyMapping, int keyCode, int modifierKeyCode) {
        return keyMapping.isActiveAndMatches(InputConstants.getKey(keyCode, modifierKeyCode));
    }
}
