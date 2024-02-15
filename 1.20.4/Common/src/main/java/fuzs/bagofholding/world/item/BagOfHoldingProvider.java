package fuzs.bagofholding.world.item;

import com.google.gson.JsonObject;
import fuzs.bagofholding.world.inventory.FilteredBagSlot;
import fuzs.puzzlesapi.api.iteminteractions.v1.provider.SimpleItemProvider;
import net.minecraft.world.item.ItemStack;

public class BagOfHoldingProvider extends SimpleItemProvider {
    private final BagOfHoldingItem.Type type;

    public BagOfHoldingProvider(BagOfHoldingItem.Type type) {
        super(9, -1, type.backgroundColor);
        this.type = type;
        this.filterContainerItems();
    }

    @Override
    protected int getInventoryHeight() {
        return this.type.config().rows;
    }

    @Override
    public boolean isItemAllowedInContainer(ItemStack containerStack, ItemStack stackToAdd) {
        return FilteredBagSlot.mayPlaceInBag(this.type, stackToAdd);
    }

    @Override
    public void toJson(JsonObject jsonObject) {
        jsonObject.addProperty("bag_type", this.type.name());
    }
}
