package fuzs.bagofholding.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

public class ServerConfig implements ConfigCore {
    @Config(description = "Amount of inventory rows this type of bag of holding has.")
    @Config.IntRange(min = 1, max = 9)
    public int leatherBagRows = 1;
    @Config(description = "Amount of inventory rows this type of bag of holding has.")
    @Config.IntRange(min = 1, max = 9)
    public int ironBagRows = 3;
    @Config(description = "Amount of inventory rows this type of bag of holding has.")
    @Config.IntRange(min = 1, max = 9)
    public int goldenBagRows = 6;
    @Config(description = "Chance that one level of preservation will be lost on a bag of holding when dying.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double preservationLevelLossChance = 1.0;
    @Config(description = "Require the player to be crouching to open the item bag menu via right-click.")
    public boolean sneakToOpenBag = false;
}
