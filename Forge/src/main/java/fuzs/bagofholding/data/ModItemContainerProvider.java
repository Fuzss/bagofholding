package fuzs.bagofholding.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.iteminteractionscore.api.container.v1.data.AbstractItemContainerProvider;
import fuzs.iteminteractionscore.api.container.v1.provider.SimpleItemProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;

public class ModItemContainerProvider extends AbstractItemContainerProvider {

    public ModItemContainerProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerBuiltInProviders() {
        this.add(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get(), new SimpleItemProvider(9, 1, DyeColor.BROWN).filterContainerItems());
        this.add(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get(), new SimpleItemProvider(9, 3, DyeColor.LIGHT_GRAY).filterContainerItems());
        this.add(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.get(), new SimpleItemProvider(9, 6, DyeColor.YELLOW).filterContainerItems());
    }
}
