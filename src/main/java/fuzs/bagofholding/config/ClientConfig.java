package fuzs.bagofholding.config;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.annotation.Config;
import fuzs.puzzleslib.config.serialization.EntryCollectionBuilder;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;

public class ClientConfig extends AbstractConfig {
    @Config(description = "Color bag inventories on tooltips according to the bag's color.")
    public boolean colorfulTooltips = true;
    @Config(description = "Seeing bag inventory contents requires shift to be held.")
    public boolean contentsRequireShift = true;

    public ClientConfig() {
        super("");
    }
}
