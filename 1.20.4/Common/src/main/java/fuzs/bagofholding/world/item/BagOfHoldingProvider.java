package fuzs.bagofholding.world.item;

import com.google.gson.JsonObject;
import fuzs.iteminteractions.api.v1.provider.SimpleItemProvider;
import net.minecraft.world.item.ItemStack;

public class BagOfHoldingProvider extends SimpleItemProvider {
    private final BagType type;

    public BagOfHoldingProvider(BagType type) {
        super(9, -1, type.getBackgroundColor());
        this.type = type;
        this.filterContainerItems();
    }

    @Override
    protected int getInventoryHeight() {
        return this.type.getInventoryRows();
    }

    @Override
    public boolean isItemAllowedInContainer(ItemStack containerStack, ItemStack stackToAdd) {
        return this.type.mayPlaceInBag(stackToAdd);
    }

    @Override
    public void toJson(JsonObject jsonObject) {
        jsonObject.addProperty("bag_type", this.type.name());
    }
}
