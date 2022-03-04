package fuzs.bagofholding.config;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.annotation.Config;
import fuzs.puzzleslib.config.serialization.EntryCollectionBuilder;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;

public class ServerConfig extends AbstractConfig {
    @Config(name = "bag_of_holding_blacklist", description = {"Items that should not be allowed to be put into a bag, mainly intended to prevent nesting.", "Shulker boxes are disabled by default.", EntryCollectionBuilder.CONFIG_DESCRIPTION})
    List<String> bagBlacklistRaw = EntryCollectionBuilder.getKeyList(ForgeRegistries.ITEMS);

    public Set<Item> bagBlacklist;

    public ServerConfig() {
        super("");
    }

    @Override
    protected void afterConfigReload() {
        this.bagBlacklist = EntryCollectionBuilder.of(ForgeRegistries.ITEMS).buildSet(this.bagBlacklistRaw);
    }
}
