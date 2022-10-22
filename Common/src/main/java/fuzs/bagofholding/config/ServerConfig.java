package fuzs.bagofholding.config;

import fuzs.bagofholding.api.config.ServerConfigCore;
import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;
import fuzs.puzzleslib.config.serialization.ConfigDataSet;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

import java.util.List;

public class ServerConfig implements ConfigCore, ServerConfigCore {
    @Config
    public BagOfHoldingConfig leatherBagConfig = new BagOfHoldingConfig();
    @Config
    public BagOfHoldingConfig ironBagConfig = new BagOfHoldingConfig();
    @Config
    public BagOfHoldingConfig goldenBagConfig = new BagOfHoldingConfig();
    @Config(description = "Chance that one level of preservation will be lost on a bag of holding when dying.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double preservationLevelLossChance = 1.0;
    @Config(description = "Allow using the mouse wheel to scroll between slots to choose the next item to extract when hovering over a container item.")
    public boolean allowSlotCycling = true;

    public ServerConfig() {
        this.leatherBagConfig.rows = 1;
        this.ironBagConfig.rows = 3;
        this.goldenBagConfig.rows = 6;
    }

    @Override
    public boolean allowSlotCycling() {
        return this.allowSlotCycling;
    }

    public static class BagOfHoldingConfig implements ConfigCore {
        @Config(description = "Amount of inventory rows this type of bag of holding has.")
        @Config.IntRange(min = 1, max = 9)
        public int rows = 1;
        @Config(name = "bag_of_holding_blacklist", description = {"Items that should not be allowed in a bag, mainly intended to prevent nesting with backpacks from other mods.", "Shulker boxes and other bags of holding are disabled by default.", ConfigDataSet.CONFIG_DESCRIPTION})
        List<String> bagBlacklistRaw = ConfigDataSet.toString(Registry.ITEM_REGISTRY);
        @Config(name = "bag_of_holding_whitelist", description = {"Only items in this list will be allowed in a bag. Must contain at least a single entry to be valid. Overrides blacklist when valid.", ConfigDataSet.CONFIG_DESCRIPTION})
        List<String> bagWhitelistRaw = ConfigDataSet.toString(Registry.ITEM_REGISTRY);

        public ConfigDataSet<Item> bagBlacklist;
        public ConfigDataSet<Item> bagWhitelist;

        @Override
        public void afterConfigReload() {
            this.bagBlacklist = ConfigDataSet.of(Registry.ITEM_REGISTRY, this.bagBlacklistRaw);
            this.bagWhitelist = ConfigDataSet.of(Registry.ITEM_REGISTRY, this.bagWhitelistRaw);
        }
    }
}
