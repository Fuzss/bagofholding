package fuzs.bagofholding.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

public class ClientConfig implements ConfigCore {
    @Config(description = "Color inventory menus according to the bag's color.")
    public boolean colorfulMenuBackgrounds = true;
}
