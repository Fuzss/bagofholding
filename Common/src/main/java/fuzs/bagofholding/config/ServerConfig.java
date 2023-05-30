package fuzs.bagofholding.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import fuzs.puzzleslib.api.config.v3.serialization.ConfigDataSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import java.util.List;

public class ServerConfig implements ConfigCore {
    @Config
    public BagOfHoldingConfig leatherBagConfig = new BagOfHoldingConfig();
    @Config
    public BagOfHoldingConfig ironBagConfig = new BagOfHoldingConfig();
    @Config
    public BagOfHoldingConfig goldenBagConfig = new BagOfHoldingConfig();
    @Config(description = "Chance that one level of preservation will be lost on a bag of holding when dying.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double preservationLevelLossChance = 1.0;

    public ServerConfig() {
        this.leatherBagConfig.rows = 1;
        this.ironBagConfig.rows = 3;
        this.goldenBagConfig.rows = 6;
    }
    
    public static class BagOfHoldingConfig implements ConfigCore {
        @Config(description = "Amount of inventory rows this type of bag of holding has.")
        @Config.IntRange(min = 1, max = 9)
        public int rows = 1;
        @Config(name = "bag_of_holding_blacklist", description = {"Items that should not be allowed in a bag, mainly intended to prevent nesting with backpacks from other mods.", "Shulker boxes and other bags of holding are disabled by default.", ConfigDataSet.CONFIG_DESCRIPTION})
        List<String> bagBlacklistRaw = ConfigDataSet.toString(Registries.ITEM);
        @Config(name = "bag_of_holding_whitelist", description = {"Only items in this list will be allowed in a bag. Must contain at least a single entry to be valid. Overrides blacklist when valid.", ConfigDataSet.CONFIG_DESCRIPTION})
        List<String> bagWhitelistRaw = ConfigDataSet.toString(Registries.ITEM);

        public ConfigDataSet<Item> bagBlacklist;
        public ConfigDataSet<Item> bagWhitelist;

        @Override
        public void afterConfigReload() {
            this.bagBlacklist = ConfigDataSet.from(Registries.ITEM, this.bagBlacklistRaw);
            this.bagWhitelist = ConfigDataSet.from(Registries.ITEM, this.bagWhitelistRaw);
        }
    }
}
