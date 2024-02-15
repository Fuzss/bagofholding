package fuzs.bagofholding.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(PackOutput packOutput, String modId, ExistingFileHelper fileHelper) {
        super(packOutput, modId, fileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.basicItem(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get());
        this.basicItem(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get());
        this.basicItem(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.get());
    }
}
