package fuzs.bagofholding.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.bagofholding.world.item.BagOfHoldingProvider;
import fuzs.puzzlesapi.api.iteminteractions.v1.data.AbstractItemContainerProvider;
import net.minecraft.data.PackOutput;

public class ModItemContainerProvider extends AbstractItemContainerProvider {

    public ModItemContainerProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerBuiltInProviders() {
        this.add(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get(), new BagOfHoldingProvider(BagOfHoldingItem.Type.LEATHER));
        this.add(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get(), new BagOfHoldingProvider(BagOfHoldingItem.Type.IRON));
        this.add(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.get(), new BagOfHoldingProvider(BagOfHoldingItem.Type.GOLDEN));
    }
}
