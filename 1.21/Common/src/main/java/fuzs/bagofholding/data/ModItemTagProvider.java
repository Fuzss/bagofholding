package fuzs.bagofholding.data;

import fuzs.bagofholding.world.item.BagType;
import fuzs.puzzleslib.api.data.v2.AbstractTagProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderLookup;

public class ModItemTagProvider extends AbstractTagProvider.Items {

    public ModItemTagProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        for (BagType bagType : BagType.values()) {
            this.tag(bagType.getAllowTag());
            this.tag(bagType.getDisallowTag());
        }
    }
}
